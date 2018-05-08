package com.myfinal.pojo;


import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotBlank;


@Entity
@Table(name="user_table")
public class User {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "userID", unique=true, nullable = false)
	private long userID;
	
	@NotBlank(message="username cant be blank")
	@Column(name="username")
	private String username;
	
	@NotBlank(message="password cant be blank")
	@Column(name="password")
	private String password;
	
	@Column(name="role")
	private String role;
	
	//shopping cart
	@OneToMany(cascade={CascadeType.ALL}, fetch=FetchType.LAZY,mappedBy = "user",targetEntity=Product.class)
	private Set<Cart> userCart = new HashSet<Cart>();
	
	//user order history
	@OneToMany(cascade={CascadeType.ALL}, fetch=FetchType.LAZY,mappedBy = "user",targetEntity=Order.class)
	private Set<Order> userOrder = new HashSet<Order>();
	
	// seller' store product
	@OneToMany(cascade={CascadeType.ALL}, fetch=FetchType.LAZY,mappedBy = "user",targetEntity=Product.class)
	private Set<Product> sellerStore= new HashSet<Product>();
	
	// one user has many card
	@OneToMany(cascade={CascadeType.ALL}, fetch=FetchType.LAZY,mappedBy = "user",targetEntity=PayInfo.class)
	private Set<PayInfo> paySet = new HashSet<PayInfo>();
	
	// one user only to one person
	@Valid
	@OneToOne 
	@PrimaryKeyJoinColumn
	private Person userDetail;
	
	public User(String username, String password,String role) {
		this.username = username;
		this.password = password;
		this.role=role;
	}

	public User() {
	
	}
	public Person getUserDetail() {
		return userDetail;
	}

	public void setUserDetail(Person userDetail) {
		this.userDetail = userDetail;
	}

	public long getUserID() {
		return userID;
	}

	public void setUserID(long userID) {
		this.userID = userID;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<Cart> getUserCart() {
		return userCart;
	}

	public void setUserCart(Set<Cart> userCart) {
		this.userCart = userCart;
	}
	
	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Set<Product> getSellerStore() {
		return sellerStore;
	}

	public void setSellerStore(Set<Product> sellerStore) {
		this.sellerStore = sellerStore;
	}

	public Set<PayInfo> getPaySet() {
		return paySet;
	}

	public void setPaySet(Set<PayInfo> paySet) {
		this.paySet = paySet;
	}

	public Set<Order> getUserOrder() {
		return userOrder;
	}

	public void setUserOrder(Set<Order> userOrder) {
		this.userOrder = userOrder;
	}
	
	
	

}
