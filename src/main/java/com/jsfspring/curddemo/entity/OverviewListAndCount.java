package com.jsfspring.curddemo.entity;

import java.util.List;

public class OverviewListAndCount<T> {
	
	private List<T> overviewList;
	
	private int count;

	public List<T> getOverviewList() {
		return overviewList;
	}

	public void setOverviewList(List<T> overviewList) {
		this.overviewList = overviewList;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

}
