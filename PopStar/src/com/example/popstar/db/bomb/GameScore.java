package com.example.popstar.db.bomb;

import cn.bmob.v3.BmobObject;

public class GameScore extends BmobObject{

	//保存每一关卡的最高分数
	private Integer rank;
	private Integer score;
   
    
    public Integer getRank() {
		return rank;
	}
	public void setRank(Integer rank) {
		this.rank = rank;
	}
	public Integer getScore() {
		return score;
	}
	public void setScore(Integer score) {
		this.score = score;
	}
	
   
}