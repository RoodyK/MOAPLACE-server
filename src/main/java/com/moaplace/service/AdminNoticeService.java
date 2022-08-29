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

import com.moaplace.dto.AdminListDTO;
import com.moaplace.mapper.AdminNoticeDetailMapper;
import com.moaplace.mapper.AdminNoticeMapper;
import com.moaplace.vo.AdminNoticeDetailVO;
import com.moaplace.vo.AdminNoticeVO;


@Service
public class AdminNoticeService {
    @Autowired private AdminNoticeMapper mapper;
    @Autowired private AdminNoticeDetailMapper detailmapper;
    @Value("${oracle.download}") 
    private String realPath;
    
    private Logger log = LoggerFactory.getLogger(this.getClass());
    
    //파일 및 공지 사항 등록
	@Transactional(rollbackFor = Exception.class)
	public int insert(List<MultipartFile> multipartFile, AdminNoticeVO vo) {
		log.info("인자로 받은 AdminNOticeVO : " + vo);
		log.info("==============================");
		mapper.insert(vo);
		log.info("selectKey 사용 후 VO : " + vo);
		try {
			if (multipartFile.size() > 0 && !multipartFile.get(0).getOriginalFilename().equals("")) {
				//파일이 저장될 경로 설정 및 기스가 없다면 설정하기 
				 String path = realPath; 
				 System.out.println("경로확인" + path);
				 File dir = new File(path);
				 if(!dir.isDirectory()){
					 dir.mkdirs();
				 }
				
				for (MultipartFile file : multipartFile) {
					String originalFilename = file.getOriginalFilename(); // 원본 파일명
					String savefilename = UUID.randomUUID() + "_" + originalFilename; // 실제 저장 될 파일명
					InputStream is = file.getInputStream(); // 파일 읽어오기
					File f = new File(path + "\\" + savefilename);
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
}
