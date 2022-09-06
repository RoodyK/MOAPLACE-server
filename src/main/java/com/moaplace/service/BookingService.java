package com.moaplace.service;

import java.sql.Array; 
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.moaplace.dto.AdminChartDTO;
import com.moaplace.dto.BookingShowDTO;
import com.moaplace.dto.GradePriceDTO;
import com.moaplace.dto.HallSeatDTO;
import com.moaplace.dto.MyBookingDTO;
import com.moaplace.dto.MyBookingDetailDTO;
import com.moaplace.dto.TicketGradeDTO;
import com.moaplace.dto.payment.BookingDTO;
import com.moaplace.dto.payment.PaymentDTO;
import com.moaplace.dto.payment.TicketDTO;
import com.moaplace.mapper.AllSeatMapper;
import com.moaplace.mapper.BookingMapper;
import com.moaplace.mapper.HallMapper;
import com.moaplace.mapper.PaymentMapper;
import com.moaplace.mapper.ShowMapper;
import com.moaplace.mapper.TicketMapper;
import com.moaplace.vo.AllSeatVO;
import com.moaplace.vo.BookingVO;
import com.moaplace.vo.PaymentVO;
import com.moaplace.vo.TicketVO;

import lombok.extern.log4j.Log4j;

@Service
@Log4j
public class BookingService {

	@Autowired 
	private BookingMapper mapper;
	
	@Autowired
	private AllSeatMapper allSeatMapper;
	
	@Autowired
	private HallMapper hallMapper;
	
	@Autowired
	private ShowMapper showMapper;
	
	@Autowired
	private PaymentMapper paymapper;
	
	@Autowired
	private TicketMapper ticketmapper;
	
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
	
	// 결제 차트용
	public List<AdminChartDTO> bookingChart(Map<String, Object> map) {
		return mapper.bookingChart(map);
	}
	
	// 예매취소 좌석 삭제
	public int cancleSeat(int booking_num) {
		return allSeatMapper.cancleSeat(booking_num);
	}

	//예매 페이지 공연장번호, 공연제목 조회
	public BookingShowDTO getBookingShow(int show_num)
	{
		return showMapper.getBookingShow(show_num);
	}
	
	//공연장명 조회
	public String getHallName(int hall_num)
	{
		return hallMapper.getHallname(hall_num);
	}
	
	//예매페이지 결제 완료 후 예매 처리  
	@Transactional(rollbackFor = Exception.class)
	public int insert(BookingDTO bookingdto, PaymentDTO paymentdto, List<TicketDTO> ticket, int show_num, List<String> allseat) {
		log.info("인자로 받은 BookingVO : " + bookingdto);
		log.info("============================서비스 확인값==========================");
		
		String temp = "";
		for(String d : bookingdto.getBooking_seat()) {
			log.info("for문 : " + d);
			temp += d + ", ";
		}
		temp = temp.substring(0, temp.length()-2);
		log.info("temp : " + temp);
		
		BookingVO vo = new BookingVO(0, bookingdto.getMember_num(), bookingdto.getSchedule_num(),bookingdto.getBooking_count(), 
				bookingdto.getBooking_price(), temp, bookingdto.getUse_point(), null);
		mapper.payInfoInsert(vo);
		log.info("selectKey 사용 후 VO : " + bookingdto);
		
		try {
			paymapper.paymentInsert(new PaymentVO(0 ,vo.getBooking_num(), paymentdto.getMerchant_uid(), paymentdto.getImp_uid(),
					paymentdto.getBooking_price(), "카드결제", "결제완료", null));
			log.info("=========================================================================");

			
			ObjectMapper objmapper = new ObjectMapper();

			for (int i = 0; i < ticket.size(); i++) {
				TicketDTO tt = objmapper.convertValue(ticket.get(i), TicketDTO.class);
				log.info("tt : " + tt);
				if (tt.getCount() != 0) {
					if (tt.getCountA() != 0) {
						ticketmapper.ticketInsert(new TicketVO(0, vo.getBooking_num(), "성인", tt.getCountA(),
								tt.getGrade(), tt.getCountA() * tt.getPriceY()));
					} else if (tt.getCountY() != 0) {
						ticketmapper.ticketInsert(new TicketVO(0, vo.getBooking_num(), "청소년", tt.getCountY(),
								tt.getGrade(), tt.getCountY() * tt.getPriceY()));
					}
				}

			}
			log.info("=========================================================================");


			String[] seat = allseat.stream().toArray(String[]::new);
			for (int i = 0; i < allseat.size(); i++) {
				allSeatMapper.seatInsert(new AllSeatVO(0, vo.getSchedule_num(), show_num, seat[i], vo.getBooking_num()));
			}
			return vo.getBooking_num();
		} catch (Exception e1) {
			e1.printStackTrace();
			return -1;
		}

	}

}
