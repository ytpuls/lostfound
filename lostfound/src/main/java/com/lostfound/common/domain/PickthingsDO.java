package com.lostfound.common.domain;

import java.io.Serializable;
import java.util.Date;



/**
 * 
 * 
 * @author yangtao
 * @email 541502337@qq.com
 * @date 2019-03-13 15:39:11
 */
public class PickthingsDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//LostThings ID 自增
	private Long id;
	//物品名称
	private String thingsname;
	//丢失地点
	private String pickplace;
	//丢失时间
	private Date picktime;
	//物品类型
	private String thingstype;
	//物品编号
	private String thingsno;
	//物品图片
	private String thingsimg;
	//物品描述
	private String thingsdes;
	//暂存地点
	private String storageplace;
	//联系电话
	private String telphone;
	//发布时间
	private Date publishtime;
	//状态
	private Integer status;
	//用户名
	private String username;
	//用户表 ID 外键
	private Long uid;

	/**
	 * 设置：LostThings ID 自增
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * 获取：LostThings ID 自增
	 */
	public Long getId() {
		return id;
	}
	/**
	 * 设置：物品名称
	 */
	public void setThingsname(String thingsname) {
		this.thingsname = thingsname;
	}
	/**
	 * 获取：物品名称
	 */
	public String getThingsname() {
		return thingsname;
	}
	/**
	 * 设置：丢失地点
	 */
	public void setPickplace(String pickplace) {
		this.pickplace = pickplace;
	}
	/**
	 * 获取：丢失地点
	 */
	public String getPickplace() {
		return pickplace;
	}
	/**
	 * 设置：丢失时间
	 */
	public void setPicktime(Date picktime) {
		this.picktime = picktime;
	}
	/**
	 * 获取：丢失时间
	 */
	public Date getPicktime() {
		return picktime;
	}
	/**
	 * 设置：物品类型
	 */
	public void setThingstype(String thingstype) {
		this.thingstype = thingstype;
	}
	/**
	 * 获取：物品类型
	 */
	public String getThingstype() {
		return thingstype;
	}
	/**
	 * 设置：物品编号
	 */
	public void setThingsno(String thingsno) {
		this.thingsno = thingsno;
	}
	/**
	 * 获取：物品编号
	 */
	public String getThingsno() {
		return thingsno;
	}
	/**
	 * 设置：物品图片
	 */
	public void setThingsimg(String thingsimg) {
		this.thingsimg = thingsimg;
	}
	/**
	 * 获取：物品图片
	 */
	public String getThingsimg() {
		return thingsimg;
	}
	/**
	 * 设置：物品描述
	 */
	public void setThingsdes(String thingsdes) {
		this.thingsdes = thingsdes;
	}
	/**
	 * 获取：物品描述
	 */
	public String getThingsdes() {
		return thingsdes;
	}
	/**
	 * 设置：暂存地点
	 */
	public void setStorageplace(String storageplace) {
		this.storageplace = storageplace;
	}
	/**
	 * 获取：暂存地点
	 */
	public String getStorageplace() {
		return storageplace;
	}
	/**
	 * 设置：联系电话
	 */
	public void setTelphone(String telphone) {
		this.telphone = telphone;
	}
	/**
	 * 获取：联系电话
	 */
	public String getTelphone() {
		return telphone;
	}
	/**
	 * 设置：发布时间
	 */
	public void setPublishtime(Date publishtime) {
		this.publishtime = publishtime;
	}
	/**
	 * 获取：发布时间
	 */
	public Date getPublishtime() {
		return publishtime;
	}
	/**
	 * 设置：状态
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	/**
	 * 获取：状态
	 */
	public Integer getStatus() {
		return status;
	}
	/**
	 * 设置：用户名
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	/**
	 * 获取：用户名
	 */
	public String getUsername() {
		return username;
	}
	/**
	 * 设置：用户表 ID 外键
	 */
	public void setUid(Long uid) {
		this.uid = uid;
	}
	/**
	 * 获取：用户表 ID 外键
	 */
	public Long getUid() {
		return uid;
	}
}
