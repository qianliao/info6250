package com.myfinal.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;

@Entity
@Table(name="userdetail_table")
public class Person {
	
	@Id
	@GeneratedValue(generator = "generator")
	@GenericGenerator(name = "generator", strategy = "foreign", parameters = @Parameter(name = "property", value = "user"))
	@Column(name = "personID", unique=true, nullable = false)
	private long personID;
	
	@OneToOne(mappedBy="userDetail") 
	private User user;
	
	@NotBlank(message="FirstName can't be blank")
	@Column(name = "firstName")
	private String firstName;
	
	@NotBlank(message="LastName can't be blank")
	@Column(name = "lastName")
	private String lastName;
	
	@NotBlank(message="Email can't be blank")
	@Email(message="Email Format is wrong") 
	@Column(name = "email",unique=true)
	private String email;
	
	@NotBlank(message="Tel can't be blank")
	@Pattern(regexp="\\d{3}-?\\d{3}-\\d{4}$",message="Tel Format is wrong")
	@Column(name = "tel",unique=true)
	private String tel;
	
	@NotBlank(message="Address can't be blank")
	@Pattern(regexp="^[a-zA-Z][\\.a-zA-Z\\s,0-9]*?[a-zA-Z]+$",message="Address Format is wrong")
	@Column(name = "address")
	private String address;
	


	public Person(String firstName, String lastName, String email, String tel, String address) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.tel = tel;
		this.address = address;
	}
	
	public Person() {};
	

	public long getPersonID() {
		return personID;
	}

	public void setPersonID(long personID) {
		this.personID = personID;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}


	
	
	
	

}
