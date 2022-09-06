package com.moaplace.controller.booking;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.moaplace.dto.BookingShowDTO;
import com.moaplace.dto.GradePriceDTO;
import com.moaplace.dto.HallSeatDTO;
import com.moaplace.dto.TicketGradeDTO;
import com.moaplace.dto.payment.AllSeatDTO;
import com.moaplace.dto.payment.BookingDTO;
import com.moaplace.dto.payment.PaymentDTO;
import com.moaplace.dto.payment.TicketDTO;
import com.moaplace.service.BookingService;
import com.moaplace.service.MemberService;
import com.moaplace.vo.AllSeatVO;

import lombok.extern.log4j.Log4j;

@CrossOrigin("*")
@RestController
@RequestMapping("/booking")
@Log4j
public class BookingController {
	
	@Autowired
	private BookingService service;
	
	@Autowired
	private MemberService mservice;
	
	@GetMapping
	(value = "/getBookingSeat/{schedule_num}",
	produces = {MediaType.APPLICATION_JSON_VALUE})
	public List<String> getBookingSeat(
			@PathVariable Integer schedule_num)
	{
		List<String> seats = service.getBookingSeat(schedule_num);
		
		return seats;
	}
	
	@GetMapping
	(value = "/getHallInfo/{show_num}",
	produces = {MediaType.APPLICATION_JSON_VALUE})
	public HashMap<String, Object> getHallInfo(
			@PathVariable Integer show_num)
	{
		BookingShowDTO dto = service.getBookingShow(show_num);
		
		HashMap<String, Object> hallInfo = new HashMap<String, Object>();
		
		//공연명
		hallInfo.put("show_name", dto.getShow_name());
		
		//공연장명
		String hall_name = service.getHallName(dto.getHall_num());
		hallInfo.put("hall_name", hall_name);
		
		//좌석 수 (cols, rows)
		HallSeatDTO seats = service.getHallSeats(dto.getHall_num());
		hallInfo.put("seats", seats);
		
		//등급별 행수
		List<TicketGradeDTO> gradeSeats = service.getTicketGrade(dto.getHall_num());
		hallInfo.put("gradeSeats", gradeSeats);
		
		//가격
		List<GradePriceDTO> gradePrice = service.getGradePrice(show_num);
		hallInfo.put("gradePrice", gradePrice);
		
		return hallInfo;
	}
	
	@PostMapping(value = "/payment",
			consumes = {MediaType.APPLICATION_JSON_VALUE})
	public HashMap<String,Object> payinsert(@RequestBody Map<String, Object> map		
			){
     	log.info("payment:" + map.get("payment"));	
		log.info("=========================================================================");
     	
         
		
		ObjectMapper objmapper = new ObjectMapper();
		PaymentDTO payment = null;
		BookingDTO booking = null;
//		ticket[] ticket = null;
		List<String> allseat = (List<String>) map.get("allseat");
		List<TicketDTO> ticket = (List<TicketDTO>) map.get("ticket");
		int show_num = (int) map.get("show_num");
		int member_num = (int) map.get("member_num");
		 log.info("확인:"+ ticket);
		try {
           payment = objmapper.convertValue(map.get("payment"), PaymentDTO.class);
           booking = objmapper.convertValue(map.get("booking"), BookingDTO.class);
//           ticket = objmapper.readValue(map.get("ticket"), TicketDTO[].class);
//           log.info(objmapper.convertValue(map.get("ticket"), TicketDTO.class));
 //          log.info(map.get("allseat"));
           
//           allseat = objmapper.convertValue(map.get("allseat"), AllSeatDTO[].class); 
           for(int i = 0; i < ticket.size(); i++) {
   			TicketDTO tt = objmapper.convertValue(ticket.get(i), TicketDTO.class);
   			log.info("tt : " + tt.getCount());
   			
   		}
		}catch(Exception e) {
			e.getMessage();
		}
		
		
		
		
		HashMap<String,Object> resultmap = new HashMap<String,Object>();

		log.info("=============================== 결제  log==================================");
		log.info("결과:" + map);
		log.info("payment:" + payment);
		log.info("booking:" + booking.getBooking_seat().getClass().getName());
//		log.info("ticket:" + ticket);
//     	log.info("AllSeat:" + allseat);
		log.info("=========================================================================");

		try {
			int result = service.insert(booking, payment, ticket, show_num, allseat);
			if(result > 0 ) {
				HashMap<String,Object> map1 = new HashMap<String,Object>();
				map1.put("use_point",booking.getUse_point());
				map1.put("member_num",member_num);
				mservice.pointupdate(map1);
			}
			resultmap.put("booking_num",result);
			resultmap.put("data", "success");	
		}catch(Exception e) {
			e.getMessage();
			resultmap.put("data", "fail");
		}
		return resultmap;
	
	}
	

}
