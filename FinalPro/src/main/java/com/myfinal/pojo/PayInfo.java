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
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name="payment_table")
public class PayInfo {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "paymentID", unique=true, nullable = false)
	private long paymentID;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="userID") 
	private User user;
	
	@Column(name="cardtype")
	private String cardType;
	
	@NotBlank(message="please enter your card number")
	@Pattern(regexp="^([1-9]{1})(\\d{15})$",message="card number fomat is wrong")
	@Column(name="cardnumber")
	private String cardNumber;
	
	@NotBlank(message="please enter your expired time")
	@Pattern(regexp="^\\d{1,2}\\/\\d{1,2}$",message="card number fomat is wrong")
	@Column(name="expired")
	private String expiredTime;
	
	@Transient
	int verifiedId;
	
	public PayInfo(String cardType, String cardNumber, String expiredTime) {
		this.cardType = cardType;
		this.cardNumber = cardNumber;
		this.expiredTime = expiredTime;
	}
	
	public PayInfo() {
		
	}
	

	public long getPaymentID() {
		return paymentID;
	}

	public void setPaymentID(long paymentID) {
		this.paymentID = paymentID;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public String getExpiredTime() {
		return expiredTime;
	}

	public void setExpiredTime(String expiredTime) {
		this.expiredTime = expiredTime;
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
	
	
	

}
