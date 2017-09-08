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

	public ResultVo uploadFile(MultipartFile upFile) {

		Configuration cfg = new Configuration(Zone.zone2());
		UploadManager uploadManager = new UploadManager(cfg);
		ResultVo resultVo = ResultVo.fail();
		Auth auth = Auth.create(accessKey, secretKey);
		String upToken = auth.uploadToken(bucket);
		try {
			Response response = uploadManager.put(upFile.getBytes(), null, upToken);
			//解析上传成功的结果
			DefaultPutRet putRet = OBJECT_MAPPER.readValue(response.bodyString(), DefaultPutRet.class);
			System.out.println(putRet.key);
			System.out.println(putRet.hash);
			resultVo.setCode(ResultEnum.SUCCESS.getCode());
			resultVo.setItem(domain+"/"+putRet.hash);
		} catch (Exception e) {
			LOGGER.error("{}", e);
		}

		return resultVo;
	}


}
