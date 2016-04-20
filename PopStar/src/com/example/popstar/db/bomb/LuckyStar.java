package com.example.popstar.db.bomb;

import cn.bmob.v3.BmobObject;

public class LuckyStar  extends BmobObject {
	
	//保存每一关卡所存的幸运星
	
	private Integer rank;
	private Integer id;
	private Integer score;
	private Integer luckystar;
	
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
	
	public Integer getScore() {
		return score;
	}
	public void setScore(Integer score) {
		this.score = score;
	}	
	public Integer getLuckystar() {
		return luckystar;
	}
	public void setLuckystar(Integer luckystar) {
		this.luckystar = luckystar;
	}
	

}
