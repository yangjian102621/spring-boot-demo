package com.monda.demo.controller;

import com.monda.demo.enums.ResultEnum;
import com.monda.demo.util.QiNiuUploadUtils;
import com.monda.demo.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

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
	 * 七牛云服务器上传
	 * @param file
	 * @return
	 */
	@PostMapping(value = "/qiniu")
	@ResponseBody
	public ResultVo qiNiu(MultipartFile file) {

		ResultVo resultVo = ResultVo.success();
		ResultVo uploadFile = qiNiuUploadUtils.uploadFile(file);
		if (uploadFile.isSuccess()) {
			resultVo.setItem(uploadFile.getItem());
			resultVo.setMessage("上传成功.");
		} else {
			resultVo.setCode(ResultEnum.FAIL.getCode());
			resultVo.setMessage("上传失败.");
		}
		return resultVo;
	}

}
