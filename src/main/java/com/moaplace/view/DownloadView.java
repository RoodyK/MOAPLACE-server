package com.moaplace.view;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.FileCopyUtils;
import org.springframework.web.servlet.view.AbstractView;

//커스텀뷰 - 사용자가 직접 만드는 뷰(다운로드창으로 응답하는 뷰 - 엑셀 파일로 응답하는 뷰, pdf로 응답하는 뷰)
public class DownloadView extends AbstractView {

	// 뷰에서 해야 할일 구현 - 다운로드 창으로 응답하기
	@Override
	protected void renderMergedOutputModel(
			Map<String, Object> model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		response.setContentType("application/octet-stream");
		File f = (File)model.get("file");
		long fileSize = (long) model.get("fileSize");
		String fileName = (String) model.get("fileName");
		response.setContentLength((int)fileSize);
		fileName = URLEncoder.encode(fileName, "utf-8");
		fileName.replaceAll("\\+", "%20");
		response.setHeader("Content-Disposition", "attachment;filename="+fileName);
		OutputStream os = response.getOutputStream();
		FileInputStream fis = new FileInputStream(f);
		FileCopyUtils.copy(fis, os);
		fis.close();
		os.close();
	}
}