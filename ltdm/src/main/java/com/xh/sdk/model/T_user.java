package com.xh.sdk.model;

import java.util.Date;

public class T_user {
	//主键
	private int id;
	//用户名
	private String name;
	//密码
	private String password;
	//公司
	private String company;
	//权限等级（暂时只对应菜单）
	private String role;
	//对应菜单
	private String roleMenu;
	//创建时间
	private Date createTime;
	//修改时间
	private Date updateTime;
	
	//查询条件
	//开始时间
	private String startTime;
	//结束时间
	private String endTime;
	
	private int start;
	private int end;
	
	
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public int getStart() {
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}
	public int getEnd() {
		return end;
	}
	public void setEnd(int end) {
		this.end = end;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getRoleMenu() {
		return roleMenu;
	}
	public void setRoleMenu(String roleMenu) {
		this.roleMenu = roleMenu;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
}
