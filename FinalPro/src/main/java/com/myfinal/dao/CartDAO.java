package com.myfinal.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.myfinal.pojo.*;
import com.myfinal.exception.ProductException;



public class CartDAO extends DAO{
	public CartDAO() {}
	
	@Autowired
	@Qualifier("userDao")
	UserDAO userDao;
	
	public Cart AddToCart(Cart product) throws ProductException{
		try {
			begin();
			System.out.println("add product to user cart");
			System.out.println(product.getVerifiedId());
			getSession().save(product);
			commit();
			return product;
		}catch (HibernateException e) {
			rollback();
			throw new ProductException("Exception while adding product: " + e.getMessage());
		}
	}
	
	public Cart UpdateCart(Cart oriitem,Cart newitem) throws ProductException{
		try {
			begin();
			System.out.println("update product qty");
			System.out.println(oriitem.getVerifiedId());
			oriitem.setQuantity(oriitem.getQuantity()+newitem.getQuantity());
			getSession().update(oriitem);
			commit();
			return oriitem;
		}catch (HibernateException e) {
			rollback();
			throw new ProductException("Exception while updating product: " + e.getMessage());
		}
	}
	
	public Cart FindPro(String productName,long userID) throws ProductException{
		try {
			
			begin();//pro where pro.user.userID=:userID
			Query q = getSession().createQuery("from Cart pro where pro.productName=:productName and pro.user.userID=:userID and pro.order.orderID is null");
			q.setString("productName", productName);
			q.setLong("userID", userID);
			Cart pro = (Cart) q.uniqueResult();
			System.out.println(pro);
			commit();
			return pro;
			
		}catch (HibernateException e) {
			rollback();
			throw new ProductException("Exception while finding product: " + e.getMessage());
		}
	}
	
	public boolean FindOrderID(String productName,long userID) throws ProductException{
		try {
			
			begin();
			Query q = getSession().createQuery("from Cart pro where pro.productName=:productName and pro.user.userID=:userID and pro.order.orderID is not null");
			q.setString("productName", productName);
			q.setLong("userID", userID);
	        Cart result = (Cart) q.uniqueResult();   
			System.out.println(result);
			commit();
			return result!=null?true:false;
			
		}catch (HibernateException e) {
			rollback();
			throw new ProductException("Exception while finding product: " + e.getMessage());
		}
	}
	
	public void RemoveFromCart(Cart product) throws ProductException{
		try {
			begin();
			System.out.println("delete product to user cart");
			getSession().delete(product);
			commit();
		}catch (HibernateException e) {
			rollback();
			throw new ProductException("Exception while deleting product: " + e.getMessage());
		}
		
	}
	
	
    public List<Cart> listAllProduct(long userID) throws Exception{
    	
    	try {
            begin();	  
            Query q = getSession().createQuery("from Cart pro where pro.user.userID=:userID");
            q.setLong("userID", userID);
            List<Cart> productList =q.list();
            commit();
            return productList;
        } catch (HibernateException e) {
            rollback();
            throw new ProductException("could not list all products", e);
        }
    	
    }
    
    
	public void EmptyCart(long userID) throws ProductException{
		try {
			begin();
			System.out.println("empty cart");
			String hql= "delete Cart cart where cart.user.userID=:userID";
			getSession().createQuery(hql).setLong("userID", userID).executeUpdate();
			commit();
		}catch (HibernateException e) {
			rollback();
			throw new ProductException("Exception while empty cart: " + e.getMessage());
		}
		
	}
	

}
