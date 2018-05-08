package com.myfinal.dao;


import org.hibernate.HibernateException;
import org.hibernate.Query;

import com.myfinal.exception.UserException;
import com.myfinal.pojo.Person;
import com.myfinal.pojo.User;

public class UserDAO extends DAO{
	
	public UserDAO() {}
	
	public User registerUser(User user)throws UserException {
		try {
			begin();
			System.out.println("register new user in dao");
			Person person = new Person(user.getUserDetail().getFirstName(),user.getUserDetail().getLastName(),
					user.getUserDetail().getEmail(),user.getUserDetail().getTel(),user.getUserDetail().getAddress());
			User u = new User(user.getUsername(),user.getPassword(),user.getRole());
			u.setUserDetail(person);
			person.setUser(user);
			getSession().save(user);
			getSession().save(person);
			commit();
			return u;
		}catch (HibernateException e) {
			rollback();
			throw new UserException("Exception while creating user: " + e.getMessage());
		}
	}
	
	public User Verify(String username, String password) throws UserException {
		try {
			begin();
			Query q = getSession().createQuery("from User where username = :username and password = :password");
			q.setString("username", username);
			q.setString("password", password);			
			User user = (User) q.uniqueResult();
			commit();
			return user;
		} catch (HibernateException e) {
			rollback();
			throw new UserException("Could not get user " + username, e);
		}
	}
	
	public User FindUser(long userId) throws UserException {
		try {
			begin();
			Query q = getSession().createQuery("from User where userID= :userID");
			q.setLong("userID", userId);		
			User user = (User) q.uniqueResult();
			commit();
			return user;
		} catch (HibernateException e) {
			rollback();
			throw new UserException("Could not get user " + userId, e);
		}
	}
	
	public User CheckUnique(String username) throws UserException{
		try {
			begin();
			Query q = getSession().createQuery("from User where username= :username");
			q.setString("username", username);		
			User user = (User) q.uniqueResult();
			commit();
			return user;
		} catch (HibernateException e) {
			rollback();
			throw new UserException("Could not get user " + username, e);
		}
	}
	
	public User CheckEmailUnique(String email) throws UserException{
		try {
			begin();
			Query q = getSession().createQuery("from User user where user.userDetail.email= :email");
			q.setString("email", email);		
			User user = (User) q.uniqueResult();
			commit();
			return user;
		} catch (HibernateException e) {
			rollback();
			throw new UserException("Could not get user " + email, e);
		}
	}

	

}
