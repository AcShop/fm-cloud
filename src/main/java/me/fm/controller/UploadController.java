package me.fm.controller;

import java.io.File;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.unique.common.tools.CollectionUtil;
import org.unique.common.tools.DateUtil;
import org.unique.common.tools.FileUtil;
import org.unique.common.tools.StringUtils;
import org.unique.web.annotation.Path;
import org.unique.web.core.Const;
import org.unique.web.core.Controller;

import com.baidu.ueditor.ActionEnter;

/**
 * 文件上传
 * @author:rex
 * @date:2014年8月19日
 * @version:1.0
 */
@Path("/upload")
public class UploadController extends Controller {

	private Logger logger = Logger.getLogger(UploadController.class);

	private static final String FILE_SIZE = "file_size";

	private static final String FILE_URL = "url";

	private static final String SAVE_PATH = "save_path";

	private static final String SAVE_NAME = "save_name";

	private static final String FILE_NAME = "file_name";

	private String savePath = "";

	/**
	 * 上传临时文件
	 * @throws Exception 
	 */
	public void temp_file() throws Exception {
		savePath = this.getRequest().getServletContext().getRealPath("/")
				+ Const.CONST_MAP.get("unique.web.upload.path").toString() + File.separator + "temp" + File.separator;
		fileupload(savePath);
	}

	/**
	 * 上传用户目录文件
	 */
	public void sys_file() {
		savePath = this.getRequest().getServletContext().getRealPath("/") + savePath;
		try {
			fileupload(savePath);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 文件上传处理
	 * @throws Exception 
	 */
	private void fileupload(String savePath) throws Exception {
		if (ServletFileUpload.isMultipartContent(this.getRequest())) {
			
			//设置接收的编码格式  
	        request.setCharacterEncoding("UTF-8");  
	        
			File uploadDir = new File(savePath);

			if (!uploadDir.exists()) {
				uploadDir.mkdirs();
			}

			logger.debug("savepath:" + savePath);

			DiskFileItemFactory diskFactory = new DiskFileItemFactory();
			// threshold 极限、临界值，即硬盘缓存 1M  
			diskFactory.setSizeThreshold(4 * 1024);
			// repository 贮藏室，即临时文件目录  
			diskFactory.setRepository(new File(savePath));

			ServletFileUpload upload = new ServletFileUpload(diskFactory);

			Integer maxSize = Integer.valueOf(Const.CONST_MAP.get("unique.web.upload.maxSize").toString().trim());
			// 设置允许上传的最大文件大小（单位MB）
			upload.setSizeMax(maxSize * 1024);
			upload.setHeaderEncoding("utf-8");
			try {
				// 解析HTTP请求消息头  
				List<FileItem> fileList = upload.parseRequest(request);
				Iterator<FileItem> it = fileList.iterator();
				while (it.hasNext()) {
					FileItem item = it.next();
					// 处理上传文件内容
					if (!item.isFormField()) {
						processUploadFile(item, response.getWriter());
					}
				}

			} catch (FileUploadException ex) {
				return;
			}
		}
	}

	/**
	 * 处理上传文件
	 * @param item
	 * @param printWriter
	 * @throws Exception 
	 */
	private void processUploadFile(FileItem item, PrintWriter printWriter) throws Exception {
		String extName = "";
		String fileName = item.getName();
		long size = item.getSize();
		String type = item.getContentType();

		logger.debug("length:" + size + "\t type:" + type);

		if (StringUtils.isBlank(fileName)) {
			return;
		}

		// 扩展名格式：  
		extName = FileUtil.getExtension(fileName);
		
		String saveFilePath = null;
		// 生成文件名：  
		String dateStr = DateUtil.convertIntToDatePattern(DateUtil.getCurrentTime(), "yyyyMMddHHmmss");
		String random = StringUtils.randomNum(5);
		String saveName = dateStr + random + extName;
		// 保存全路径
		saveFilePath = savePath + dateStr + random + extName;
		saveFilePath = saveFilePath.replace("\\", "/");
		File file = new File(saveFilePath);
		item.write(file);
		
		Map<String, Object> fileInfo = CollectionUtil.newHashMap();
		fileInfo.put(SAVE_PATH, saveFilePath);
		fileInfo.put(SAVE_NAME, saveName);
		fileInfo.put(FILE_NAME, fileName);
		String url = this.getURL(saveFilePath);
		fileInfo.put(FILE_URL, url);
		fileInfo.put(FILE_SIZE, item.getSize());
		this.renderJson(fileInfo);
	}
	
	private String getURL(String saveFilePath) {
		String path = request.getContextPath();
		String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
		String url = basePath + saveFilePath.substring(saveFilePath.indexOf(path));
		return url;
	}

	public void ueditor() throws UnsupportedEncodingException, JSONException{
		request.setCharacterEncoding( "utf-8" );
		response.setHeader("Content-Type" , "text/html");
		String rootPath = request.getServletContext().getRealPath( "/" );
		this.renderText(new ActionEnter( request, rootPath ).exec());
	}
}
