package com.moaplace.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.moaplace.dto.BookingCancleDTO;

import lombok.extern.log4j.Log4j;

@Log4j
@Service
public class ImportService {
	
	@Value("${import.imp_key}")
	private String impKey;
	
	@Value("${import.imp_secret}")
	private String impSecret;
	
	private String tokenURL = "https://api.iamport.kr/users/getToken";
	private String cancleURL = "https://api.iamport.kr/payments/cancel";
	
	// 아임포트 접속토큰 생성
	public String getToken(){
		try {
			
			URL url = new URL(tokenURL);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "application/json");
			connection.setRequestProperty("Accept", "application/json");
			connection.setDoOutput(true);
			
			JsonObject json = new JsonObject();
			json.addProperty("imp_key", impKey);
			json.addProperty("imp_secret", impSecret);
			
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()));
			bw.write(json.toString());
			bw.flush();
			bw.close();
			
			BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			Gson gson = new Gson();
			String response = gson.fromJson(br.readLine(), Map.class).get("response").toString();
			log.info("아임포트 접속토큰: " + response);
			
			String token = gson.fromJson(response, Map.class).get("access_token").toString();
			
			br.close();
			connection.disconnect();

			return token;
				
		} catch (Exception e) {
			log.info(e.getMessage());
			return null;
		}
	}
	
	// 아임포트 서버 환불요청
	public boolean cancle(String token, String imp_uid, String reason, int amount) {
		try {
		
			URL url = new URL(cancleURL);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "application/json");
			connection.setRequestProperty("Authorization", token);
			connection.setRequestProperty("Accept", "application/json");
			connection.setDoOutput(true);

			JsonObject json = new JsonObject();
			json.addProperty("imp_uid", imp_uid);
			json.addProperty("reason", reason);
			json.addProperty("amount", amount);
			json.addProperty("checksum", amount);
			
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()));
			bw.write(json.toString());
			bw.flush();
			bw.close();
			
			BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			Gson gson = new Gson();
			String response = gson.fromJson(br.readLine(), Map.class).get("response").toString();
			log.info("아임포트 환불요청 응답결과: " + response);
						
			br.close();
			connection.disconnect();
			
			return true;
		
		} catch (Exception e) {
			log.info(e.getMessage());
			return false;
		}
	}
}
