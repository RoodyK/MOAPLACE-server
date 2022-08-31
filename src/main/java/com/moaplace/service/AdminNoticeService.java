package com.moaplace.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import com.moaplace.dto.AdminDetailDTO;
import com.moaplace.dto.AdminListDTO;
import com.moaplace.dto.AdminNoticeDetailDTO;
import com.moaplace.mapper.AdminNoticeDetailMapper;
import com.moaplace.mapper.AdminNoticeMapper;
import com.moaplace.util.FileUtil;
import com.moaplace.vo.AdminNoticeDetailVO;
import com.moaplace.vo.AdminNoticeVO;


@Service
public class AdminNoticeService {
    @Autowired private AdminNoticeMapper mapper;
    @Autowired private AdminNoticeDetailMapper detailmapper;
    @Autowired
	private FileUtil fileutil;
    @Value("${oracle.download}") 
    private String realPath;
    
    private Logger log = LoggerFactory.getLogger(this.getClass());
    
    //파일 및 공지 사항 등록
	@Transactional(rollbackFor = Exception.class)
	public int insert(List<MultipartFile> multipartFile, AdminNoticeVO vo) {
		log.info("인자로 받은 AdminNoticeVO : " + vo);
		log.info("============================서비스 확인값==========================");
		mapper.insert(vo);
		log.info("selectKey 사용 후 VO : " + vo);
		try {
			if (multipartFile.size() > 0 && !multipartFile.get(0).getOriginalFilename().equals("")) {
				//파일이 저장될 경로 설정 및 기스가 없다면 설정하기, 컨트롤러에선 realpath 사용 못함 		
				 String path = realPath; 
				 System.out.println("경로확인" + path);
				 File dir = new File(path + File.separator + "notice" + File.separator);
				 log.info("파일경로:"+dir);
				 if(!dir.isDirectory()){
					 dir.mkdirs();
				 }
				
				for (MultipartFile file : multipartFile) {
					
					String originalFilename = file.getOriginalFilename(); // 원본 파일명
					String savefilename = UUID.randomUUID() + "_" + originalFilename; // 실제 저장 될 파일명
					InputStream is = file.getInputStream(); // 파일 읽어오기
					File f = new File(path + File.separator + "notice" + File.separator + savefilename);
					FileOutputStream fos = new FileOutputStream(f); // 파일 저장하기
					FileCopyUtils.copy(is, fos);
					is.close();
					fos.close();
					long filesize = file.getSize();
					detailmapper.detailinsert(
							new AdminNoticeDetailVO(0, vo.getNotice_num(), originalFilename, savefilename, filesize));
				}
				return 0;
			}
		} catch (Exception e1) {
			e1.printStackTrace();
			return -1;
		}
//		log.info("mapper.insert값 확인 try 후: "+vo);
//		log.info("================================================================");
		return 1;
	}
	
	//수정용
	@Transactional(rollbackFor = Exception.class)
	public int update(List<MultipartFile> multipartFile, AdminNoticeVO vo) {
		
		mapper.update(vo);

		List<AdminNoticeDetailDTO> filelist = mapper.filelist(vo.getNotice_num());

		log.info("========================== 파일 삭제 체험  log=================================");
		log.info("delete 정보 :" + filelist);
		log.info("==========================================================================");
		
		try {
			if (filelist != null || filelist.size() != 0) {
				List<Integer> list = mapper.selectnum(vo.getNotice_num());
				int[] flist = list.stream().mapToInt(i -> i).toArray();
				for (int i = 0; i < flist.length; i++) {
					String fileName = mapper.selectfile(flist[i]).getNotice_savefile();
					fileutil.delete(fileName, "notice");
				}
			
				
		//		=============================================================================================
			
			if (multipartFile.size() > 0 && !multipartFile.get(0).getOriginalFilename().equals("")) {			
				String path = realPath;
				System.out.println("경로확인" + path);
				File dir = new File(path + File.separator + "notice" + File.separator);
				for (MultipartFile file : multipartFile) {					
					String originalFilename = file.getOriginalFilename(); // 원본 파일명
					String savefilename = UUID.randomUUID() + "_" + originalFilename; // 실제 저장 될 파일명
					log.info("========================== for문 파일  log=================================");
					log.info("originalFilename 정보 :" + originalFilename);
					log.info("========================================================================");
					InputStream is = file.getInputStream(); // 파일 읽어오기
					File f = new File(path + File.separator + "notice" + File.separator + savefilename);
					FileOutputStream fos = new FileOutputStream(f); // 파일 저장하기
					FileCopyUtils.copy(is, fos);
					is.close();
					fos.close();
					long filesize = file.getSize();
					detailmapper.detailinsert(
							new AdminNoticeDetailVO(0, vo.getNotice_num(), originalFilename, savefilename, filesize));
				}

				

				return 0;
			}
			}
		} catch (Exception e1) {
			e1.printStackTrace();
			return -1;
		}
		return 1;
	}
	
	//공지사항 리스트 
	public List<AdminListDTO> listAll(HashMap<String,Object> map){
		return mapper.listAll(map);
	}
	
	//전체 글 개수 
	public int getCount(HashMap<String,Object> map) {
		return mapper.getCount(map);
	}
	
	//글 상세보기
	public List<AdminDetailDTO> detail(int notice_num){
		return mapper.detail(notice_num);
	}
	
	//글  상세보기 : 파일 없을때
	public List<AdminListDTO> detaillist(int notice_num){
		return mapper.detaillist(notice_num);
	}
	
	//파일 리스트 불러오기
	public List<AdminNoticeDetailDTO> filelist(int notice_num){
		return mapper.filelist(notice_num);
	}
	
	//파일 다운로드 
	public AdminNoticeDetailDTO filedown(int notice_detail_num){
		return mapper.filedown(notice_detail_num);
	}
	
	//삭제하기 위한 파일
	public AdminNoticeDetailDTO selectfile(int notice_detail_num) {
		return mapper.selectfile(notice_detail_num);
	}
	
	//파일 삭제를 위한 넘버 받기,. resultType = int > int 배열로 받기
	public List<Integer> selectnum(int notice_num) {
		return mapper.selectnum(notice_num);
	}

	// 파일이 없는 경우 삭제
	public int delete(int notice_num) {
		return mapper.delete(notice_num);
	}

	// 파일이 있는 경우 삭제
	public int alldelete(int notice_num) {
		return mapper.alldelete(notice_num);
	}
	
	//수정용 파일 개별 삭제 
	public int deletefile(int notice_detail_num) {
		return mapper.deletefile(notice_detail_num);
	}
	
	//수정용 - 리스트 불러오기
	public AdminListDTO selectdetail(int notice_num) {
		return mapper.selectdetail(notice_num);
	}
	
	public int deleteone(int notice_num) {
		return mapper.deleteone(notice_num);
	}
	
	
}
