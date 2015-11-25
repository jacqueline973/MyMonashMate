/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.monash.mymonashmate.server;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author betty
 */
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
        return "MatchMateResult [matchMateList=" + matchMateList + "]";
    }
}
