package com.moaplace.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.UUID;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.log4j.Log4j;

/**
 * @author hyein
 * 파일 업로드(다중파일, 단일파일)
 * 파일 수정
 * 파일 삭제
 */
@Component
@Log4j
public class FileUtil {
	
	@Value("${oracle.download}")
	private String realPath; 
	
	/**
	 * @apiNote 단일파일을 업로드 폴더에 저장합니다.
	 * 폴더명은 해당 게시판명을 영어로 써야합니다. ex) 대관신청 - rental
	 * @param file 파일
	 * @param folder 저장될 폴더 명
	 * @return 원본 파일명, 저장된 파일명, 파일 크기
	 */
	public HashMap<String, Object> upload(
			MultipartFile file, String folder)
	{
		HashMap<String, Object> fileinfo= new HashMap<String, Object>();
		String path = realPath;
		log.info("저장경로: "+ path);
		String orgfilename = file.getOriginalFilename();
		String savefilename = UUID.randomUUID() + "_" + orgfilename;
		
		try {
			//폴더 생성
			File uploadPath = new File(path + File.separator + folder);
			if(!uploadPath.exists()) {
				uploadPath.mkdirs();
			}
			
			//파일 저장
			InputStream is = file.getInputStream();
			File f = new File(path+ File.separator + folder + File.separator + savefilename);
			FileOutputStream fos = new FileOutputStream(f);
			FileCopyUtils.copy(is, fos);
			is.close();
			fos.close();
			
			long filesize = file.getSize();
			
			//결과 전달
			fileinfo.put("result", "success");
			fileinfo.put("orgfilename", orgfilename);
			fileinfo.put("savefilename", savefilename);
			fileinfo.put("filesize", filesize);
		} catch (Exception e) {
			log.warn(e.getMessage());
			fileinfo.put("result", "fail");
		}
		return fileinfo;
	}
	
}
