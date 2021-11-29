package com.risk.riskmanage.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import com.risk.riskmanage.common.basefactory.BaseController;


/**
 * @ClassName: UploadController <br/>
 * @Description: TODO ADD FUNCTION. <br/>
 */
@Controller

public class UploadController extends BaseController {
	static Logger log = LoggerFactory.getLogger(UploadController.class);
	
	@RequestMapping("/upload")
	public ModelAndView uploadFile(
			@RequestParam(value = "file", required = true) MultipartFile file,
			HttpServletRequest request, ModelMap model) {
		log.info(" upload file " + file);
		InputStream is = null;
		OutputStream os = null;

		try {

			is = file.getInputStream();

			File destFile = new File("C:\\img", "123456.jpg");
			// 获取文件类型ID
			os = new FileOutputStream(destFile);

			byte[] b = new byte[4096];
			int length = 0;
			while (true) {
				length = is.read(b);
				if (length < 0)
					break;
				os.write(b, 0, length);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (is != null) {
					is.close();
				}
				if (os != null) {
					os.close();
				}
			} catch (Exception e) {
				e.printStackTrace();

			} finally {
				System.gc();
			}
		}
		return new ModelAndView("uploadResult");
	}
	
	}
