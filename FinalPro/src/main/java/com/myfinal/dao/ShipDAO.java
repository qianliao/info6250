package com.myfinal.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;

import com.myfinal.exception.OrderException;
import com.myfinal.exception.ShipException;
import com.myfinal.pojo.Order;
import com.myfinal.pojo.Shipment;

public class ShipDAO extends DAO{
	public ShipDAO() {}
	
	public Shipment AddNewShipment(Shipment shipment) throws ShipException{
		try {
			begin();
			System.out.println("add express to system");
			System.out.println(shipment.getUser().getUserID());
			System.out.println(shipment.getOrder().getOrderID());
			getSession().save(shipment);
			commit();
			return shipment;
		}catch (HibernateException e) {
			rollback();
			throw new ShipException("Exception while adding shipinfo: " + e.getMessage());
		}
	}
	
    public List<Shipment> listShip(long userID) throws Exception{
    	
    	try {
            begin();	  
            Query q = getSession().createQuery("from Shipment ship where ship.user.userID=:userID");
            q.setLong("userID", userID);
            List<Shipment> shipList =q.list();
            commit();
            return shipList;
        } catch (HibernateException e) {
            rollback();
            throw new ShipException("could not list ship information", e);
        }
    	
    }
	

}
