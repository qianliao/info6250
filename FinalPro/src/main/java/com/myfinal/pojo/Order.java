package com.myfinal.pojo;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.ArrayList;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="order_table")
public class Order {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "orderID", unique=true, nullable = false)
	private long orderID;
	
	@Column(name="status")
	private String status;
	
	@Column(name="ordertime")
	private Date orderTime;
	
	@OneToMany(cascade={CascadeType.ALL}, fetch=FetchType.LAZY,mappedBy = "order",targetEntity=Product.class)
	private Set<Product> prolist = new HashSet<Product>();
	
	@OneToMany(cascade={CascadeType.ALL}, fetch=FetchType.LAZY,mappedBy = "order",targetEntity=Product.class)
	private List<Cart> productcart = new ArrayList<Cart>();;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="userID") 
	private User user;
	
	@OneToOne(mappedBy="order")
	private Shipment ship;
	
	public Order(Date orderTime) {
		super();
		this.orderTime = orderTime;
	}
	
	public Order() {}

	public long getOrderID() {
		return orderID;
	}

	public void setOrderID(long orderID) {
		this.orderID = orderID;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}

	public Set<Product> getProlist() {
		return prolist;
	}

	public void setProlist(Set<Product> prolist) {
		this.prolist = prolist;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<Cart> getProductcart() {
		return productcart;
	}

	public void setProductcart(List<Cart> productcart) {
		this.productcart = productcart;
	}

	public Shipment getShip() {
		return ship;
	}

	public void setShip(Shipment ship) {
		this.ship = ship;
	}
	
	
	
	
	
	
	
	
	
	
	
	 
	 

}
