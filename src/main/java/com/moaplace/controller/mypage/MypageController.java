package com.moaplace.controller.mypage;

import java.text.SimpleDateFormat;
import java.util.Date;
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
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.moaplace.dto.MyBookingCancleRequestDTO;
import com.moaplace.dto.MyBookingDTO;
import com.moaplace.dto.MyBookingDetailDTO;
import com.moaplace.dto.MyFavoriteDTO;
import com.moaplace.dto.MyInfoEditDTO;
import com.moaplace.dto.MyRentalDTO;
import com.moaplace.dto.MyRentalDetailDTO;
import com.moaplace.dto.MyReviewDTO;
import com.moaplace.service.BookingService;
import com.moaplace.service.FavoriteService;
import com.moaplace.service.ImportService;
import com.moaplace.service.MemberService;
import com.moaplace.service.PaymentService;
import com.moaplace.service.RentalService;
import com.moaplace.service.ReviewService;
import com.moaplace.util.PageUtil;
import com.moaplace.vo.RentalVO;

import lombok.extern.log4j.Log4j;

@CrossOrigin("*")
@RestController
@RequestMapping("/users/mypage")
@Log4j
public class MypageController {

	@Autowired
	private BookingService bookingService;
	@Autowired
	private RentalService rentalService;
	@Autowired
	private PaymentService paymentService;
	@Autowired
	private MemberService memberService;
	@Autowired
	private FavoriteService favoriteService;
	@Autowired
	private ReviewService reviewService;
	@Autowired
	private ImportService importService;
	
	/* 로그인한 회원의 최근 예매내역 1건 + 최근 대관내역 1건 조회 */
	@GetMapping(value = "/{member_num}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, Object> mypage(@PathVariable("member_num") int member_num) {
		
		try {
			log.info(member_num);
			
			HashMap<String, Object> map = new HashMap<String, Object>();
			
			// 1. 예매내역, 대관내역 유무 조회하고 존재하지 않으면 bkExist false, 존재하면 true put
			boolean bkExist = bookingService.bookingExist(member_num); // 예매내역 존재여부
			boolean rtExist = rentalService.rentalExist(member_num); // 대관내역 존재여부
			map.put("bkExist", bkExist);
			map.put("rtExist", rtExist);
			
			// 2. 예매내역 존재하면 MyBookingDTO도 put
			if(bkExist) {
				MyBookingDTO bkDto = bookingService.recentBooking(member_num);
				map.put("bkDto", bkDto);
			}
			
			// 3. 대관내역 존재하면 MyRentalDTO도 put
			if(rtExist) {
				MyRentalDTO rtDto = rentalService.recentRental(member_num);
				map.put("rtDto", rtDto);
			}
			
			return map;
			
		} catch (Exception e) {
			log.info(e.getMessage());
			return null;
		}
	}
	
	/* 예매내역 5행 5페이지 조회 + 페이징 */
	@GetMapping(value = {"/ticket/list/{member_num}/{startdate}/{enddate}", 
			"/ticket/list/{member_num}/{startdate}/{enddate}/{pageNum}"},
			produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, Object> ticketList(
			@PathVariable("member_num") int member_num,
			@PathVariable("startdate") String startdate,
			@PathVariable("enddate") String enddate,
			@PathVariable("pageNum") Integer pageNum) 
	{
		try {
			
			SimpleDateFormat dtFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date cstartdate = dtFormat.parse(startdate); // 시작날짜 String -> Date
			Date cenddate = dtFormat.parse(enddate); // 끝날짜 String -> Date
			
			HashMap<String, Object> map = new HashMap<String, Object>();
			
			boolean bkExist = bookingService.bookingExist(member_num); // 예매내역 존재여부
			map.put("bkExist", bkExist);
			
			// 페이지번호 없으면 1로 초기화
			if(pageNum == null) pageNum = 1;
			
			map.put("member_num", member_num); // 회원번호
			map.put("startdate", cstartdate); // 시작날짜
			map.put("enddate", cenddate); // 끝날짜
			
			int totalRowCount = bookingService.listCount(map); // 전체 결과 개수

			PageUtil pageUtil = new PageUtil(pageNum, 5, 5, totalRowCount); // 한페이지 3개, 한페이지당 페이지개수 5개

			map.put("startRow", pageUtil.getStartRow()); // 시작행번호
			map.put("endRow", pageUtil.getEndRow()); // 끝행번호
			
			List<MyBookingDTO> list = bookingService.list(map); // 예매내역 리스트
			map.put("list", list);
			
			map.put("listCnt", totalRowCount); // 전체 결과 개수
			map.put("pageNum", pageNum); // 페이지번호
			map.put("startPage", pageUtil.getStartPageNum()); // 페이지시작번호
			map.put("endPage", pageUtil.getEndPageNum()); // 페이지끝번호
			map.put("pageCnt", pageUtil.getTotalPageCount()); // 전체 페이지수
			
			return map;
			
		} catch (Exception e) {
			log.info(e.getMessage());
			return null;
		}
	}
	
	/* 예매상세내역 조회 */
	@GetMapping(value = "/ticket/detail/{booking_num}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, Object> ticketDetail(@PathVariable("booking_num") int booking_num) {
		
		try {
			log.info(booking_num);
			
			HashMap<String, Object> map = new HashMap<String, Object>();
			
			// 예매상세내역정보
			MyBookingDetailDTO dto = bookingService.detail(booking_num);
			map.put("dto", dto);
			
			// 예매취소가능여부 (현재일이 공연일 3일이내면 false)
			boolean cancle = bookingService.getScheduleDate(booking_num);
			map.put("cancle", cancle);
			
			return map;
			
		} catch (Exception e) {
			log.info(e.getMessage());
			return null;
		}
	}
	
	/* 예매취소 페이지 데이터 조회 */
	@GetMapping(value = "/ticket/cancle/{booking_num}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, Object> ticketCancle(@PathVariable("booking_num") int booking_num) {
		
		try {
			log.info("받은 예매번호 : " + booking_num);
			
			HashMap<String, Object> map = new HashMap<String, Object>();
			
			// 받아온 예매번호로 회원번호 조회한다음 응답
			int member_num = bookingService.cancleInfoCheck(booking_num);
			log.info("예매내역의 회원번호 : " + member_num);
			map.put("member_num", member_num);
			
			// 예매취소가능여부 (현재일이 공연일 3일이내면 false)
			boolean cancle = bookingService.getScheduleDate(booking_num);
			map.put("cancle", cancle);
			
			MyBookingDetailDTO dto = bookingService.detail(booking_num);
			map.put("dto", dto);
			
			return map;
			
		} catch (Exception e) {
			log.info(e.getMessage());
			return null;
		}
	}
	
	/* 예매취소 실행 */
	@PostMapping(value = "/ticket/cancle", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public String ticketCancle(@RequestBody MyBookingCancleRequestDTO dto) {
		
		// 받아온 예매번호,아이디,입력패스워드로 비밀번호 체크한 뒤 맞으면 success보내고 틀리면 fail
		log.info("MyBookingCancleRequestDTO : " + dto);
		log.info("booking_num : " + dto.getBooking_num());
		
		// 비밀번호 일치 체크
		boolean pwdCheck = memberService.pwdCheck(dto);
		
		// 비밀번호 일치할 경우 예매취소로 업데이트하고 업데이트 성공시 success
		if(pwdCheck) {
			// 아임포트 환불요청
			String token = importService.getToken();
			String imp_uid = dto.getImp_uid();
			String reason = dto.getReason();
			int amount = dto.getAmount();
			boolean importCancle = importService.cancle(token,imp_uid,reason,amount);
			
			if(importCancle) {
				// payment 테이블 결제상태 변경
				int n = paymentService.ticketCancle(dto.getBooking_num());
				// all_seat 테이블 삭제
				int n2 = bookingService.cancleSeat(dto.getBooking_num());
				
				if( n > 0 && n2 > 0 ) return "success";
				
			}
			return "failA";
			
		} else {
			// 비밀번호 불일치시 fail
			return "failB";
		}
	}
	
	/* 대관내역 5행 5페이지 조회 + 페이징 */
	@GetMapping(value = {"/rental/list/{member_num}/{startdate}/{enddate}", 
			"/rental/list/{member_num}/{startdate}/{enddate}/{pageNum}"},
			produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, Object> rentalList(
			@PathVariable("member_num") int member_num,
			@PathVariable("startdate") String startdate,
			@PathVariable("enddate") String enddate,
			@PathVariable("pageNum") Integer pageNum) 
	{
		try {
			
			SimpleDateFormat dtFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date cstartdate = dtFormat.parse(startdate); // 시작날짜 String -> Date
			Date cenddate = dtFormat.parse(enddate); // 끝날짜 String -> Date
			
			HashMap<String, Object> map = new HashMap<String, Object>();
			
			boolean rtExist = rentalService.rentalExist(member_num); // 대관내역 존재여부
			map.put("rtExist", rtExist);
			
			// 페이지번호 없으면 1로 초기화
			if(pageNum == null) pageNum = 1;
			
			map.put("member_num", member_num); // 회원번호
			map.put("startdate", cstartdate); // 시작날짜
			map.put("enddate", cenddate); // 끝날짜
			
			int totalRowCount = rentalService.listCount(map); // 전체 결과 개수
		
			PageUtil pageUtil = new PageUtil(pageNum, 5, 5, totalRowCount); // 한페이지 5개, 한페이지당 페이지개수 5개
			
			map.put("startRow", pageUtil.getStartRow()); // 시작행번호
			map.put("endRow", pageUtil.getEndRow()); // 끝행번호
			
			List<MyRentalDTO> list = rentalService.myList(map); // 예매내역 리스트
			map.put("list", list);
			
			map.put("listCnt", totalRowCount); // 전체 결과 개수
			map.put("pageNum", pageNum); // 페이지번호
			map.put("startPage", pageUtil.getStartPageNum()); // 페이지시작번호
			map.put("endPage", pageUtil.getEndPageNum()); // 페이지끝번호
			map.put("pageCnt", pageUtil.getTotalPageCount()); // 전체 페이지수
			
			return map;
			
		} catch (Exception e) {
			log.info(e.getMessage());
			return null;
		}
	}
	
	/* 대관상세내역 조회 */
	@GetMapping(value = "/rental/detail/{rental_num}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, Object> rentalDetail(@PathVariable("rental_num") int rental_num) {
		
		try {
			log.info(rental_num);
			
			HashMap<String, Object> map = new HashMap<String, Object>();
			
			// 예매상세내역정보
			MyRentalDetailDTO dto = rentalService.myDetail(rental_num);
			map.put("dto", dto);
			
			// 답변여부 검사 필요한지 하면서 생각좀해봄
			
			return map;
			
		} catch (Exception e) {
			log.info(e.getMessage());
			return null;
		}
	}
	
	/* 관심공연 조회 */
	@GetMapping(value = "/performance/{member_num}/{pageNum}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, Object> performance(@PathVariable int member_num, @PathVariable Integer pageNum) {
		
		try {
			log.info("member_num : " + member_num);
			log.info("pageNum : " + pageNum);
			
			HashMap<String, Object> map = new HashMap<String, Object>();
			
			// 페이지번호 없으면 1로 초기화
			if (pageNum == null)
				pageNum = 1;

			map.put("member_num", member_num); // 회원번호

			int totalRowCount = favoriteService.listCount(member_num); // 전체 결과 개수

			PageUtil pageUtil = new PageUtil(pageNum, 5, 5, totalRowCount); // 한페이지 5개, 한페이지당 페이지개수 5개

			map.put("startRow", pageUtil.getStartRow()); // 시작행번호
			map.put("endRow", pageUtil.getEndRow()); // 끝행번호

			List<MyFavoriteDTO> list = favoriteService.myList(map); // 관심공연 리스트
			map.put("list", list);

			map.put("listCnt", totalRowCount); // 전체 결과 개수
			map.put("pageNum", pageNum); // 페이지번호
			map.put("startPage", pageUtil.getStartPageNum()); // 페이지시작번호
			map.put("endPage", pageUtil.getEndPageNum()); // 페이지끝번호
			map.put("pageCnt", pageUtil.getTotalPageCount()); // 전체 페이지수

			return map;

		} catch (Exception e) {
			log.info(e.getMessage());
			return null;
		}
	}
	
	/* 관심공연 삭제 실행 */
	@PostMapping(value = "/performance/delete", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public String performanceDelete(@RequestBody HashMap<String, Object> map) {
		log.info(map);
		int n = favoriteService.delete(map);
		if(n > 0) return "success";
		return "fail";
	}
	
	/* 관람후기 조회 */
	@GetMapping(value = "/review/list/{member_num}/{pageNum}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, Object> reviewList(@PathVariable int member_num, @PathVariable Integer pageNum) {
		
		try {
			log.info("member_num : " + member_num);
			log.info("pageNum : " + pageNum);
			
			HashMap<String, Object> map = new HashMap<String, Object>();
			
			// 페이지번호 없으면 1로 초기화
			if (pageNum == null)
				pageNum = 1;

			map.put("member_num", member_num); // 회원번호

			int totalRowCount = reviewService.myListCount(member_num); // 전체 결과 개수

			PageUtil pageUtil = new PageUtil(pageNum, 5, 5, totalRowCount); // 한페이지 5개, 한페이지당 페이지개수 5개

			map.put("startRow", pageUtil.getStartRow()); // 시작행번호
			map.put("endRow", pageUtil.getEndRow()); // 끝행번호

			List<MyReviewDTO> list = reviewService.myList(map); // 관심공연 리스트
			map.put("list", list);

			map.put("listCnt", totalRowCount); // 전체 결과 개수
			map.put("pageNum", pageNum); // 페이지번호
			map.put("startPage", pageUtil.getStartPageNum()); // 페이지시작번호
			map.put("endPage", pageUtil.getEndPageNum()); // 페이지끝번호
			map.put("pageCnt", pageUtil.getTotalPageCount()); // 전체 페이지수

			return map;

		} catch (Exception e) {
			log.info(e.getMessage());
			return null;
		}
	}
	
	/* 회원 정보수정 */
	@PostMapping(value = "/info/edit", produces = MediaType.APPLICATION_JSON_VALUE)
	public String infoEdit(@RequestBody MyInfoEditDTO dto) {
		
		log.info(dto);
		int n = memberService.myInfoEdit(dto);
		
		if(n > 0) return "success";
		return "fail";
	}
	
	/* 대관신청정보 수정 */
	@PostMapping(value = "/rental/update", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
	public String rentalDetailUpdate(@RequestParam String data, @RequestPart(required = false) MultipartFile file) {
		
		log.info("data:"+data);
		log.info("file:"+file);
		
		ObjectMapper mapper = new ObjectMapper();
		RentalVO vo = new RentalVO();
			
		try {
			log.info("mapper.readValue : " + mapper.readValue(data, RentalVO.class));
			vo = mapper.readValue(data, RentalVO.class);
			log.info("RnetalVO : " + vo);
			
			// rental_content가 null이면 오류 발생 -> null일 경우 공백 스트링을 set 처리
			if(vo.getRental_content() == null) {
//				log.info("rentalcontent가 null임");
				vo.setRental_content("");
//				log.info("rentalcontent 수정후 : " + vo.getRental_content());
			}else {
				log.info("rentalcontent : " + vo.getRental_content());
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
			
		// 파일관리는 service에서 처리
		int n = rentalService.myUpdate(file, vo);
			
		if(n == 1) return "success";
		return "fail";
	}
	
}
