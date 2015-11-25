package com.monash.mymonashmate.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MatchMateResult implements Serializable {
	
	private List<MatchMate> matchMateList = new ArrayList<MatchMate>();

	public List<MatchMate> getMatchMateList() {
		return matchMateList;
	}

	public void setMatchMateList(List<MatchMate> matchMateList) {
		this.matchMateList = matchMateList;
	}

	@Override
	public String toString() {
		String text = "";
		for (MatchMate mate: matchMateList) {
			text = text + mate.toString() + "\n";
		}
		return "MatchMateResult [matchMateList=" + text + "]";
	}
	

}
