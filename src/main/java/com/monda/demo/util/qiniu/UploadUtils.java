package com.monda.demo.util.qiniu;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.monda.demo.enums.ResultEnum;
import com.monda.demo.util.FileUtils;
import com.monda.demo.util.HttpUtils;
import com.monda.demo.util.IdUtil;
import com.monda.demo.vo.ResultVo;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.storage.model.FetchRet;
import com.qiniu.storage.model.FileInfo;
import com.qiniu.storage.model.FileListing;
import com.qiniu.util.Auth;
import com.qiniu.util.UrlSafeBase64;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * 七牛云上传工具
 * @author yangjian
 * @since 17-9-8.
 */
@Component
public class UploadUtils {

	static final Logger LOGGER = LoggerFactory.getLogger(UploadUtils.class);
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

	@Value("${qiniu.allowFileExts}")
	private String allowFileExts;
	/**
	 * 空间配置
     */
	private Configuration cfg;

	/**
	 * 认证对象
     */
	private Auth auth;

	private IdUtil idUtil;

	/**
	 * 初始化token
     */
	public void initToken() {

		// 这里默认是华南区,其他区的根据情况修改 Zone 的值
		cfg = new Configuration(Zone.zone2());
		auth = Auth.create(accessKey, secretKey);
		idUtil = IdUtil.getInstance();

	}

	/**
	 * 上传表单文件
	 * @param prefix
	 * @param upFile
	 * @return
     */
	public ResultVo uploadFile(String prefix, MultipartFile upFile) {

		initToken();

		UploadManager uploadManager = new UploadManager(cfg);
		ResultVo resultVo = ResultVo.success();

		//检查文件大小
		this.checkFileSize(upFile.getSize(), resultVo);
		if (!resultVo.isSuccess()) {
			return resultVo;
		}
		this.checkFileExtension(upFile, resultVo);
		if (!resultVo.isSuccess()) {
			return resultVo;
		}

		String upToken = auth.uploadToken(bucket);
		String extension = FileUtils.getFileExtesion(upFile.getOriginalFilename());
		String filename = prefix+"-"+idUtil.getNewId()+"."+extension;
		try {
			Response response = uploadManager.put(upFile.getBytes(), filename, upToken);
			//解析上传成功的结果
			if (response.statusCode == 200) {
				resultVo.setCode(ResultEnum.SUCCESS.getCode());
				HashMap<String, Object> image = new HashMap<>();
				image.put("url", domain+filename);
				resultVo.setItem(image);
			}
		} catch (Exception e) {
			resultVo.setCode(ResultEnum.FAIL.getCode());
			LOGGER.error("{}", e);
		}

		return resultVo;
	}

	/**
	 * 上传本地文件
	 * @param prefix
	 * @param filePath
	 * @return
	 */
	public ResultVo uploadFile(String prefix, String filePath) {

		initToken();

		UploadManager uploadManager = new UploadManager(cfg);
		ResultVo resultVo = ResultVo.success();

		//检查文件大小
		File file = new File(filePath);
		this.checkFileSize(file.length(), resultVo);
		if (!resultVo.isSuccess()) {
			return resultVo;
		}

		String upToken = auth.uploadToken(bucket);
		String extension = FileUtils.getFileExtesion(filePath);
		String filename = prefix+"-"+idUtil.getNewId()+"."+extension;
		try {
			Response response = uploadManager.put(file, filename, upToken);
			//解析上传成功的结果
			DefaultPutRet putRet = OBJECT_MAPPER.readValue(response.bodyString(), DefaultPutRet.class);
			resultVo.setItem(domain+putRet.key);
		} catch (Exception e) {
			resultVo.setCode(ResultEnum.FAIL.getCode());
			LOGGER.error("{}", e);
		}

		return resultVo;
	}

	/**
	 * 抓取网络资源到空间
	 * @param remoteSrcUrl
	 * @param prefix
	 * @return
	 */
	public ResultVo fetch(String remoteSrcUrl, String prefix) {

		initToken();

		BucketManager bucketManager = new BucketManager(auth, cfg);
		ResultVo resultVo = ResultVo.success();

		//抓取网络资源到空间
		String extension = FileUtils.getFileExtesion(remoteSrcUrl);
		String filename = prefix+"-"+idUtil.getNewId()+"."+extension;
		try {
			FetchRet fetchRet = bucketManager.fetch(remoteSrcUrl, bucket, filename);
			resultVo.setItem(domain+fetchRet.key);
		} catch (QiniuException e) {
			resultVo.setCode(ResultEnum.FAIL.getCode());
			LOGGER.error("{}", e);
		}
		return resultVo;
	}

	/**
	 * base64 图片上传
	 * @param prefix
	 * @param data
	 * @return
	 */
	public ResultVo base64Upload(String prefix, String data) {

		initToken();
		String key = prefix+"-"+idUtil.getNewId()+".png";
		String url = "http://upload-z2.qiniu.com/putb64/-1/key/" + UrlSafeBase64.encodeToString(key);
		//非华东空间需要根据注意事项 1 修改上传域名
		RequestBody requestBody = RequestBody.create(null, data.substring(22)); //这里要剔除开头的说明数据，否则上传会失败 data:image/png;base64,
		Request request = new Request.Builder().
				url(url).
				addHeader("Content-Type", "application/octet-stream")
				.addHeader("Authorization", "UpToken " + auth.uploadToken(bucket))
				.post(requestBody).build();
		ResultVo resultVo = ResultVo.success();

		try {
			OkHttpClient client = new OkHttpClient();
			okhttp3.Response response = client.newCall(request).execute();
			String ret = response.body().string();
			Map map = OBJECT_MAPPER.readValue(ret, Map.class);

			resultVo.setCode(ResultEnum.SUCCESS.getCode());
			resultVo.setItem(domain+map.get("key"));

			response.close();

		} catch (IOException e) {
			LOGGER.error("{}", e);
			resultVo.setCode(ResultEnum.FAIL.getCode());
			resultVo.setMessage("上传失败.");
		}
		return resultVo;

	}

	/**
	 * 获取文件列表
	 * @param prefix
	 * @param marker
	 * @param limit
	 * @return
	 * @throws QiniuException
	 */
	public ResultVo getFileList(String prefix, String marker, Integer limit) throws IOException {

		initToken();

		BucketManager bucketManager = new BucketManager(auth, cfg);
		String delimiter = "";
		FileListing fileListing = bucketManager.listFiles(bucket, prefix, marker, limit, delimiter);

		List<Map> list = new ArrayList<>();
		ResultVo resultVo = ResultVo.success();
		if (fileListing.items.length > 0) {
			for (FileInfo item : fileListing.items) {
				Map<String, Object> map = new HashMap<>();
				if (isImage(item.key)) {
					ImageInfo info = getImageInfo(item.key);
					if (null != info) {
						map.put("height", info.getHeight());
						map.put("width", info.getWidth());
					}
				} else {
					map.put("filesize", FileUtils.getRemoteFileSize(domain+item.key));
				}
				map.put("thumbURL", domain+item.key);
				map.put("oriURL", domain+item.key);
				map.put("filesize", domain+item.fsize);

				list.add(map);
			}
			resultVo.setItems(list);
			if (null != fileListing.marker) {
				resultVo.setItem(fileListing.marker);
			} else {
				resultVo.setItem("no_records");
			}
		} else {
			resultVo.setCode(ResultEnum.FAIL.getCode());
			resultVo.setMessage("没有获取到任何文件");
		}

		return resultVo;

	}

	/**
	 * 检查文件大小
	 * @param size
	 * @param resultVo
     */
	private void checkFileSize(long size, ResultVo resultVo) {

		if (maxFileSize * 1024 * 1024 < size) {
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

		String extension = FileUtils.getFileExtesion(multipartFile.getOriginalFilename());
		List<String> extList;
		if (isImage(multipartFile.getOriginalFilename())) {
			extList = Arrays.asList(allowImgExts.split("\\|"));
		} else {
			extList = Arrays.asList(allowFileExts.split("\\|"));
		}
		if (!extList.contains(extension)) {
			resultVo.setCode(ResultEnum.FAIL.getCode());
			resultVo.setMessage("非法的文件后缀");
		}
	}

	/**
	 * 获取图片信息
	 * @param key
	 * @return
	 */
	public ImageInfo getImageInfo(String key) {
		try {
			String ret = HttpUtils.get(domain + key + "?imageInfo", null);
			if (null != ret) {
				return OBJECT_MAPPER.readValue(ret, ImageInfo.class);
			}
			return null;
		} catch (IOException e) {
			return null;
		}
	}

	/**
	 * 判断某个文件是否为图片
	 * @param fileName
	 * @return
	 */
	private static boolean isImage(String fileName) {

		String extesion = FileUtils.getFileExtesion(fileName);
		return "|jpge|jpg|bpm|gif|png".indexOf(extesion) != -1;
	}

}
