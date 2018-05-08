package com.myfinal.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;

import com.myfinal.exception.ProductException;
import com.myfinal.pojo.Cart;
import com.myfinal.pojo.Product;

public class ProductDAO extends DAO{
	public ProductDAO() {}
	
	public Product AddToStore(Product pro) throws ProductException{
		try {
			begin();
			System.out.println("add product to BloomeWebStore");
			System.out.println(pro.getVerifiedId());
			getSession().save(pro);
			commit();
			return pro;
		}catch (HibernateException e) {
			rollback();
			throw new ProductException("Exception while adding product: " + e.getMessage());
		}
	}
	
	public Product UpdateProduct(Product product,int stock,double price) throws ProductException{
		try {
			begin();
			System.out.println("update product's price and stock");
			product.setStock(stock);
			product.setPrice(price);
			getSession().update(product);
			commit();
			return product;
		}catch (HibernateException e) {
			rollback();
			throw new ProductException("Exception while adding product: " + e.getMessage());
		}
	}
	
	public Product UpdateOrderStock(Cart product,Product pro) throws ProductException{
		try {
			begin();
			System.out.println("update product stock after order");
			int addnum = product.getQuantity();
			pro.setStock(pro.getStock()-addnum);
			getSession().update(pro);
			commit();
			return product;
		}catch (HibernateException e) {
			rollback();
			throw new ProductException("Exception while updating product: " + e.getMessage());
		}
	}
	
	public Product UpdateStock(Product product,int stock) throws ProductException{
		try {
			begin();
			System.out.println("update product stock");
			System.out.println(product.getVerifiedId());
			int addnum = product.getStock();
			product.setStock(stock+addnum);
			getSession().update(product);
			commit();
			return product;
		}catch (HibernateException e) {
			rollback();
			throw new ProductException("Exception while updating product: " + e.getMessage());
		}
	}
	
	public List<Product> ListAllPro()throws Exception{
    	
    	try {
            begin();	  
            Query q = getSession().createQuery("from Product pro where pro.class=Product");
            List<Product> productList =q.list();
            commit();
            return productList;
        } catch (HibernateException e) {
            rollback();
            throw new ProductException("could not list all products", e);
        }
    	
    }
	
	public Product FindPro(String productName) throws ProductException{
		try {
			
			begin();//pro where pro.user.userID=:userID
			Query q = getSession().createQuery("from Product pro where pro.class=Product and pro.productName=:productName");
			q.setString("productName", productName);
			Product pro = (Product) q.uniqueResult();
			System.out.println(pro);
			commit();
			return pro;
			
		}catch (HibernateException e) {
			rollback();
			throw new ProductException("Exception while finding product: " + e.getMessage());
		}
	}
	
	public List<Product> OrderbyLow() throws ProductException{
		try {
			begin();	  
            Query q = getSession().createQuery("from Product pro where pro.class=Product order by pro.price desc");
            List<Product> productList =q.list();
            commit();
            return productList;
			
		}catch (HibernateException e) {
			rollback();
			throw new ProductException("Exception while order product: " + e.getMessage());
		}
	}
	
	public List<Product> OrderbyHigh() throws ProductException{
		try {
			begin();	  
            Query q = getSession().createQuery("from Product pro where pro.class=Product order by pro.price asc");
            List<Product> productList =q.list();
            commit();
            return productList;
			
		}catch (HibernateException e) {
			rollback();
			throw new ProductException("Exception while order product: " + e.getMessage());
		}
	}
	public List<Product> SearchResultsAsc(String queryString) throws ProductException{
		try {
			begin();	  
            Query q = getSession().createQuery("from Product pro where pro.class=Product and pro.productName like:queryString order by pro.price asc");
            q.setString("queryString", "%"+queryString+"%");
            List<Product> productList =q.list();
            commit();
            return productList;
			
		}catch (HibernateException e) {
			rollback();
			throw new ProductException("Exception while order search: " + e.getMessage());
		}
	}
	public List<Product> SearchResultsDesc(String queryString) throws ProductException{
		try {
			begin();	  
            Query q = getSession().createQuery("from Product pro where pro.class=Product and pro.productName like:queryString order by pro.price desc");
            q.setString("queryString", "%"+queryString+"%");
            List<Product> productList =q.list();
            commit();
            return productList;
			
		}catch (HibernateException e) {
			rollback();
			throw new ProductException("Exception while order search: " + e.getMessage());
		}
	}
	
	public List<Product> SearchProduct(String queryString) throws ProductException{
		try {
			
			begin();
            Query q = getSession().createQuery("from Product pro where pro.class=Product and pro.productName like:queryString");
            q.setString("queryString", "%"+queryString+"%");
            List<Product> searchResults =q.list();
			commit();
			return searchResults ;
			
		}catch (HibernateException e) {
			rollback();
			throw new ProductException("Exception while searching: " + e.getMessage());
		}
	}
}
