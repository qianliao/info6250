package com.myfinal.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;


@Entity
@Table(name="product_table")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)  
public class Product {
	
	@Id
	@GeneratedValue(strategy=GenerationType.TABLE)
	@Column(name = "productID", unique=true, nullable = false)
	private long productID;
	
	@NotBlank(message="please enter productname")
	@Column(name="productname")
	private String productName;
	
	@NotNull(message="please enter price")
	@Min(value=1,message="price should greater than 1")
	@Column(name="price")
	private double price;
	
	@NotBlank(message="Please enter description")
	@Column(name="description")
	private String description;
	
	@NotNull  (message="please enter stock")
	@Min(value=1,message="stock should greater than 1")
	@Column(name="stock")
	private int stock;
	
	@NotBlank(message="Seller can't be blank")
	@Column(name="seller")
	private String seller;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="userID") 
	private User user;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="orderID") 
	private Order order;
	
	@Transient
	int verifiedId;
	

	public Product(String productName, double price, String description,int stock, String seller) {
		this.productName = productName;
		this.price = price;
		this.description=description;
		this.stock=stock;
		this.seller=seller;
	}
	
	public Product() {}

	public long getProductID() {
		return productID;
	}

	public void setProductID(long productID) {
		this.productID = productID;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}
	
	public String getSeller() {
		return seller;
	}

	public void setSeller(String seller) {
		this.seller = seller;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public int getVerifiedId() {
		return verifiedId;
	}

	public void setVerifiedId(int verifiedId) {
		this.verifiedId = verifiedId;
	}
	
	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}


	
	
	
	
	


}
