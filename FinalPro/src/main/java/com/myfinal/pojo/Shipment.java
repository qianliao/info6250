package com.myfinal.pojo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="shippment_table")
public class Shipment {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "shipID", unique=true, nullable = false)
	private long shipID;
	
	@Column(name="shipname")
	private String shipname;
	
	@Column(name="shiptime")
	private Date shipTime;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="userID") 
	private User user;

	@OneToOne
	@JoinColumn(name="orderID") 
	private Order order;
	
	public Shipment() {}
	
	public Shipment(String shipname,Date shipTime) {
		super();
		this.shipname=shipname;
		this.shipTime = shipTime;
	}

	public long getShipID() {
		return shipID;
	}

	public String getShipname() {
		return shipname;
	}

	public void setShipname(String shipname) {
		this.shipname = shipname;
	}

	public void setShipID(long shipID) {
		this.shipID = shipID;
	}

	public Date getShipTime() {
		return shipTime;
	}

	public void setShipTime(Date shipTime) {
		this.shipTime = shipTime;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}
	
	
}
