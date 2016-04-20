package com.example.popstar.db.bomb;

import cn.bmob.v3.BmobObject;

public class UseStar extends BmobObject {
	
	private Integer rank;
	private Integer id;
	private String Time;
	private Integer changecolor;
	private Integer changecr;
	
	public Integer getRank() {
		return rank;
	}
	public void setRank(Integer rank) {
		this.rank = rank;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	
	public String getTime() {
		return Time;
	}
	public void setTime(String time) {
		Time = time;
	}
	public Integer getChangecolor() {
		return changecolor;
	}
	public void setChangecolor(Integer changecolor) {
		this.changecolor = changecolor;
	}
	public Integer getChangecr() {
		return changecr;
	}
	public void setChangecr(Integer changecr) {
		this.changecr = changecr;
	}
	

}
