package com.moaplace.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moaplace.dto.GradePriceDTO;
import com.moaplace.dto.HallSeatDTO;
import com.moaplace.dto.MyBookingDTO;
import com.moaplace.dto.MyBookingDetailDTO;
import com.moaplace.dto.TicketGradeDTO;
import com.moaplace.mapper.AllSeatMapper;
import com.moaplace.mapper.BookingMapper;
import com.moaplace.mapper.HallMapper;

@Service
public class BookingService {

	@Autowired 
	private BookingMapper mapper;
	
	@Autowired
	private AllSeatMapper allSeatMapper;
	
	@Autowired
	private HallMapper hallMapper;
	
	// member_num으로 회원의 예매내역 존재여부 확인
	public boolean bookingExist(int member_num) {
		boolean exist = false;
		if(mapper.bookingExist(member_num) > 0) {
			exist = true;
		}
		return exist;
	}
	
	// member_num으로 회원의 예매내역 개수 확인 (필요없을듯)
	public int bookingCount(int member_num) {
		return mapper.bookingExist(member_num);
	}
	
	// member_num으로 회원의 가장 최근 예매내역 1건 조회
	public MyBookingDTO recentBooking(int member_num) {
		return mapper.recentBooking(member_num);
	}
	
	// member_num + startdate + enddate + startrow + endrow 받아서 기간설정 + 페이징 조회
	public List<MyBookingDTO> list(HashMap<String, Object> map) {
		return mapper.list(map);
	}
	
	// 기간설정  + 페이징 조회된 내역 개수
	public int listCount(HashMap<String, Object> map) {
		return mapper.listCount(map);
	}
	
	// booking_num으로 예매상세내역 조회
	public MyBookingDetailDTO detail(int booking_num) {
		return mapper.detail(booking_num);
	}
	
	// booking_num으로 공연일 조회해서 예매취소여부 조회
	public boolean getScheduleDate(int booking_num) {
		
		boolean possible = true;
		long calDateDays = 0; // 날짜 차이를 담을 변수
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date today = new Date(); // 오늘 날짜
		Date sday = null; // String타입인 공연일을 Date형변환해서 담을 변수
		
		try {
			
			// Date타입으로 변환된 공연일
			sday = format.parse(mapper.getScheduleDate(booking_num)); 
			
			// 공연일-현재날짜를 일수로 계산
			long calDate = (sday.getTime() - today.getTime()) / 1000;
			calDateDays = calDate / (24*60*60);
			calDateDays = Math.abs(calDateDays);
			
			// 공연일-현재날짜가 3이하일 경우 false 리턴
			if(calDateDays <= 3) {
				possible = false;
			}
			
			return possible;
			
		} catch (ParseException e) {
			e.printStackTrace();
			return possible;
		}
	}

	// booking_num으로 member_num 조회
	public int cancleInfoCheck(int booking_num) {
		return mapper.cancleInfoCheck(booking_num);
	}
  
	//좌석 선택 페이지  상연일정(스케줄)번호로 이미 예매된 좌석 불러오기
	public List<String> getBookingSeat(int schedule_num)
	{
		return allSeatMapper.getBookingSeat(schedule_num);
	}
	
	//좌석 선택 페이지  공연장 좌석수 불러오기
	public HallSeatDTO getHallSeats(int hall_num) 
	{
		return hallMapper.getHallSeats(hall_num);
	}
	
	//좌석 선택 페이지 좌석 등급별 행수 조회
	public List<TicketGradeDTO> getTicketGrade(int hall_num)
	{
		return hallMapper.getTicketGrade(hall_num);
	}
	
	//좌석 선택 페이지 좌석 등급별 가격 조회
	public List<GradePriceDTO> getGradePrice(int show_num)
	{
		return hallMapper.getGradePrice(show_num);
	}
	
}
