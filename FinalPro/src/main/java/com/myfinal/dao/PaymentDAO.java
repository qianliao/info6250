package com.myfinal.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;

import com.myfinal.exception.PayInfoException;
import com.myfinal.exception.ProductException;
import com.myfinal.pojo.Cart;
import com.myfinal.pojo.PayInfo;
import com.myfinal.pojo.User;

public class PaymentDAO extends DAO{
	public PaymentDAO(){}
	
	public PayInfo AddUserPay(PayInfo payment) throws PayInfoException{
		try {
			begin();
			System.out.println("add pay to userid");
			System.out.println(payment.getVerifiedId());
			getSession().save(payment);
			commit();
			return payment;
		}catch (HibernateException e) {
			rollback();
			throw new PayInfoException("Exception while adding payment: " + e.getMessage());
		}
	}
	
	public List<PayInfo> listPayment(long userID) throws PayInfoException{
	    	
	    	try {
	            begin();	  
	            Query q = getSession().createQuery("from PayInfo pay where pay.user.userID=:userID");
	            q.setLong("userID", userID);
	            List<PayInfo> payList =q.list();
	            commit();
	            return payList;
	        } catch (HibernateException e) {
	            rollback();
	            throw new PayInfoException("could not list all pay information", e);
	        }
	    	
	    }
	
	public PayInfo checkUnique(String cardNumber)throws PayInfoException{
		try {
			begin();
			Query q = getSession().createQuery("from PayInfo where cardNumber= :cardNumber");
			q.setString("cardNumber", cardNumber);		
			PayInfo payment = (PayInfo) q.uniqueResult();
			commit();
			return payment;
		}catch (HibernateException e) {
			rollback();
			throw new PayInfoException("Exception while adding payment: " + e.getMessage());
		}
	}
	

}
