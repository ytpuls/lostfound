package com.lostfound.common.domain;

import java.io.Serializable;
import java.util.Date;



/**
 * 
 * 
 * @author yangtao
 * @email 541502337@qq.com
 * @date 2019-03-22 15:58:34
 */
public class MessageDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//ID
	private Long id;
	//感谢标题
	private String title;
	//感谢内容
	private String substance;
	//状态
	private Integer status;
	//感谢人
	private String username;
	//感谢时间
	private Date leavetime;

	/**
	 * 设置：ID
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * 获取：ID
	 */
	public Long getId() {
		return id;
	}
	/**
	 * 设置：感谢标题
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * 获取：感谢标题
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * 设置：感谢内容
	 */
	public void setSubstance(String substance) {
		this.substance = substance;
	}
	/**
	 * 获取：感谢内容
	 */
	public String getSubstance() {
		return substance;
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
	 * 设置：感谢人
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	/**
	 * 获取：感谢人
	 */
	public String getUsername() {
		return username;
	}
	/**
	 * 设置：感谢时间
	 */
	public void setLeavetime(Date leavetime) {
		this.leavetime = leavetime;
	}
	/**
	 * 获取：感谢时间
	 */
	public Date getLeavetime() {
		return leavetime;
	}
}
