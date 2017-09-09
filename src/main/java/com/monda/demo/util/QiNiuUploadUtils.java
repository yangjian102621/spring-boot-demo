package com.monda.demo.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.monda.demo.enums.ResultEnum;
import com.monda.demo.vo.ResultVo;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

/**
 * 七牛云上传工具
 * @author yangjian
 * @since 17-9-8.
 */
@Component
public class QiNiuUploadUtils {

	static final Logger LOGGER = LoggerFactory.getLogger(QiNiuUploadUtils.class);
	/**
	 * json 处理工具
	 */
	static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

	@Value("${qiniu.accessKey}")
	private String accessKey;

	@Value("${qiniu.secretKey}")
	private String secretKey;

	@Value("${qiniu.bucket}")
	private String bucket;

	@Value("${qiniu.domain}")
	private String domain;

	@Value("${qiniu.maxFileSize}")
	private Integer maxFileSize;

	@Value("${qiniu.allowImgExts}")
	private String allowImgExts;

	/**
	 * 文件上传主程序
	 * @param upFile
	 * @return
     */
	public ResultVo uploadFile(MultipartFile upFile) {

		Configuration cfg = new Configuration(Zone.zone2());
		UploadManager uploadManager = new UploadManager(cfg);
		ResultVo resultVo = ResultVo.success();

		//检查文件大小
		this.checkFileSize(upFile, resultVo);
		if (!resultVo.isSuccess()) {
			return resultVo;
		}
		this.checkFileExtension(upFile, resultVo);
		if (!resultVo.isSuccess()) {
			return resultVo;
		}

		Auth auth = Auth.create(accessKey, secretKey);
		String upToken = auth.uploadToken(bucket);
		try {
			Response response = uploadManager.put(upFile.getBytes(), null, upToken);
			//解析上传成功的结果
			DefaultPutRet putRet = OBJECT_MAPPER.readValue(response.bodyString(), DefaultPutRet.class);
			resultVo.setItem(domain+"/"+putRet.hash);
		} catch (Exception e) {
			resultVo.setCode(ResultEnum.FAIL.getCode());
			LOGGER.error("{}", e);
		}

		return resultVo;
	}

	/**
	 * 检查文件大小
	 * @param multipartFile
	 * @param resultVo
     */
	private void checkFileSize(MultipartFile multipartFile, ResultVo resultVo) {


		if (maxFileSize * 1024 * 1024 < multipartFile.getSize()) {
			resultVo.setCode(ResultEnum.FAIL.getCode());
			resultVo.setMessage("上传文件大小不能超过 "+maxFileSize+" MB");
		}
	}

	/**
	 * 检查文件后缀
	 * @param multipartFile
	 * @param resultVo
     */
	private void checkFileExtension(MultipartFile multipartFile, ResultVo resultVo) {

		String extension = this.getFileExt(multipartFile.getOriginalFilename());
		List<String> extList = Arrays.asList(allowImgExts.split("\\|"));
		if (!extList.contains(extension)) {
			resultVo.setCode(ResultEnum.FAIL.getCode());
			resultVo.setMessage("非法的文件后缀");
		}
	}

	/**
	 * 获取文件后缀名
	 * @param filename
	 * @return
     */
	private String getFileExt(String filename) {
		int pos = filename.lastIndexOf(".");
		if (pos == -1) {
			return null;
		} else {
			return  filename.substring(pos+1).toLowerCase() ;
		}
	}


}
