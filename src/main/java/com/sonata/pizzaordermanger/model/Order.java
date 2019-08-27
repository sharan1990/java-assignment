package com.sonata.pizzaordermanger.model;

import java.io.Serializable;

public class Order implements Serializable , Comparable<Order> {

	private String orderType;
	private long time;
	
	public Order(String type,long time) {
		this.orderType=type;
		this.time=time;
	}
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	public long getTime() {
		return time;
	}
	public void setTime(long time) {
		this.time = time;
	}
	@Override
	public int compareTo(Order o) {
		// TODO Auto-generated method stub
		if(this.getTime()>o.getTime()) {
			return 1;
		}else if(this.getTime()<o.getTime()) {
			return -1;
		}else
		return 0;
	}
	
	public String toString() {

	    return ""+this.orderType +"		"+this.time+"";

	  }
	
}
