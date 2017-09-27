package com.monda.demo.controller;

import com.monda.demo.enums.ResultEnum;
import com.monda.demo.util.FileUtils;
import com.monda.demo.util.HttpUtils;
import com.monda.demo.util.qiniu.UploadUtils;
import com.monda.demo.vo.ResultVo;
import com.qiniu.common.QiniuException;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 文件上传控制器
 * @author yangjian
 * @since 17-9-8.
 */
@Controller
@RequestMapping("/upload")
public class UploadController {

	@Autowired
	private UploadUtils uploadUtils;

	/**
	 * 文件上传
	 * @param file
	 * @return
	 */
	@PostMapping(value = "/putFile")
	@ResponseBody
	public ResultVo putFile(HttpServletRequest request, MultipartFile file) {

		String prefix = request.getParameter("fileType");
		String base64 = request.getParameter("base64");

		if (null == prefix) {
			prefix = "image";
		}
		ResultVo resultVo = ResultVo.success();
		ResultVo upload;
		if (null != base64 && base64.equals("1")) {
			String base64Data = request.getParameter("img_base64_data");
			upload = uploadUtils.base64Upload(prefix, base64Data);
		} else {
			upload = uploadUtils.uploadFile(prefix, file);
		}

		System.out.println(upload.getItem());
		if (upload.isSuccess()) {
			resultVo.setItem(upload.getItem());
			resultVo.setMessage("上传成功.");
		} else {
			resultVo.setCode(ResultEnum.FAIL.getCode());
			resultVo.setMessage(upload.getMessage());
		}
		return resultVo;
	}

	/**
	 * 文件列表管理
	 * @param request
	 * @return
	 */
	@GetMapping(value = "/list")
	@ResponseBody
	public ResultVo list(HttpServletRequest request) throws QiniuException {

		String prefix = StringUtils.trim(request.getParameter("fileType"));
		String marker = StringUtils.trim(request.getParameter("marker"));
		if (null == prefix
				|| null == marker
				|| marker.equals("no_records")) {
			return ResultVo.fail();
		}
		return uploadUtils.getFileList(prefix, marker, 15);

	}

	/**
	 * 文件搜索
	 * @param request
	 * @return
	 */
	@GetMapping(value = "/search")
	@ResponseBody
	public ResultVo search(HttpServletRequest request) throws IOException {

		Integer page = Integer.valueOf(request.getParameter("page"));
		String kw = StringUtils.trim(request.getParameter("kw"));
		if (page == 0) {
			page= 1;
		}
		String apiUrl = "http://image.so.com/j?src=tab_www";
		HashMap<String, Object> params = new HashMap<>();
		params.put("q", kw);
		params.put("sn", page);
		params.put("pn", 15);
		Map json = HttpUtils.getJson(apiUrl, params);
		ArrayList<Map> files = new ArrayList<>();
		ResultVo resultVo = ResultVo.instance();
		if (null != json && null != json.get("list")) {
			List<Map> list = (List<Map>) json.get("list");
			list.forEach((item) -> {
				HashMap<String, Object> map = new HashMap<>();
				// 这里为了防止搜索的图片禁止盗链，前端无法显示，这里提供一个图片抓取的后端页面
				map.put("thumbURL", "/upload/grap?img_url="+item.get("thumb"));
				map.put("oriURL", item.get("img"));
				map.put("width", item.get("width"));
				map.put("height", item.get("height"));
				files.add(map);
			});
		}

		if (files.size() > 0) {
			resultVo.setCode(ResultEnum.SUCCESS.getCode());
			resultVo.setItems(files);
		} else {
			resultVo.setCode(ResultEnum.FAIL.getCode());
		}
		return resultVo;

	}

	/**
	 * 抓取网络图片
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@GetMapping(value = "/grap")
	@ResponseBody
	public ResultVo grap(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String act = StringUtils.trim(request.getParameter("act"));
		String imgUrl = StringUtils.trim(request.getParameter("img_url"));
		File tmpPath = new File(ResourceUtils.getURL("classpath:").getPath(), "/static/tmp/");
		if(!tmpPath.exists()) {
			tmpPath.mkdir();
		}

		ResultVo resultVo = ResultVo.instance();
		if(null != act && act.equals("grapImage")) {
			//抓取原图并上传到七牛
			String[] imgs = StringUtils.trim(request.getParameter("urls")).split(",");
			ArrayList<Object> list = new ArrayList<>();
			for (String img : imgs) {
				String filePath = tmpPath.getAbsolutePath() + "/" + FileUtils.getFilenameFromPath(img);
				HttpUtils.download(img, filePath); //先下载原图
				ResultVo upload = uploadUtils.uploadFile("image", filePath); //然后上传到七牛
				if (upload.isSuccess()) {
					list.add(upload.getItem());
				}
			}

			if (list.size() > 0) {
				resultVo.setCode(ResultEnum.SUCCESS.getCode());
				resultVo.setItems(list);
			} else {
				resultVo.setCode(ResultEnum.FAIL.getCode());
				resultVo.setMessage("图片抓取失败.");
			}
			return resultVo;

		} else { //作为临时图片显示

			String filePath = tmpPath.getAbsolutePath() + "/" + FileUtils.getFilenameFromPath(imgUrl);
			HttpUtils.download(imgUrl, filePath);
			showImage(response, filePath);
		}
		return null;
	}

	/**
	 * 显示图片
	 * @param response
	 * @param imagePath
	 * @throws IOException
	 */
	private static void showImage(HttpServletResponse response, String imagePath) throws IOException {

		response.setContentType("image/jpeg;");//设定输出的类型
		OutputStream outputStream = response.getOutputStream();
		File file = new File(imagePath);
		FileInputStream fileInputStream = new FileInputStream(file);
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		int len;
		while ((len = fileInputStream.read()) != -1){
			byteArrayOutputStream.write(len);
		}
		outputStream.write(byteArrayOutputStream.toByteArray());
		outputStream.flush();
	}

}
