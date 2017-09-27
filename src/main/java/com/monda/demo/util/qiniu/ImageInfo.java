package com.monda.demo.util.qiniu;

/**
 * 七牛图片信息实体
 * Created by yangjian on 17-9-27.
 */
public class ImageInfo {

	/**
	 * 图片尺寸
	 */
	private long size;

	/**
	 * 图片格式
	 */
	private String format;

	/**
	 * 图片宽度
	 */
	private Integer width;

	/**
	 * 图片高度
	 */
	private Integer height;

	/**
	 * 颜色模式
	 */
	private String colorModel;

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public Integer getWidth() {
		return width;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}

	public Integer getHeight() {
		return height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

	public String getColorModel() {
		return colorModel;
	}

	public void setColorModel(String colorModel) {
		this.colorModel = colorModel;
	}
}
