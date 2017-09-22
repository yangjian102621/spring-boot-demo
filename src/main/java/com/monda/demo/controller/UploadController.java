package com.monda.demo.controller;

import com.monda.demo.enums.ResultEnum;
import com.monda.demo.util.QiNiuUploadUtils;
import com.monda.demo.vo.ResultVo;
import com.qiniu.common.QiniuException;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * 文件上传控制器
 * @author yangjian
 * @since 17-9-8.
 */
@Controller
@RequestMapping("/upload")
public class UploadController {

	@Autowired
	private QiNiuUploadUtils qiNiuUploadUtils;

	/**
	 * 文件上传
	 * @param file
	 * @return
	 */
	@PostMapping(value = "/putFile")
	@ResponseBody
	public ResultVo putFile(HttpServletRequest request, MultipartFile file) {

		String prefix = request.getParameter("fileType");
		if (null == prefix) {
			prefix = "image";
		}
		ResultVo resultVo = ResultVo.success();
		ResultVo uploadFile = qiNiuUploadUtils.uploadFile(prefix, file);
		if (uploadFile.isSuccess()) {
			resultVo.setItem(uploadFile.getItem());
			resultVo.setMessage("上传成功.");
		} else {
			resultVo.setCode(ResultEnum.FAIL.getCode());
			resultVo.setMessage(uploadFile.getMessage());
		}
		return resultVo;
	}

	/**
	 * 文件列表管理
	 * @param file
	 * @return
	 */
	@GetMapping(value = "/list")
	@ResponseBody
	public ResultVo list(HttpServletRequest request) throws QiniuException {

		String prefix = StringUtils.trim(request.getParameter("fileType"));
		String marker = StringUtils.trim(request.getParameter("marker"));
		if (marker.equals("no_records")) {
			return ResultVo.fail();
		}
		return qiNiuUploadUtils.getFileList(prefix, marker, 15);

	}

}
