package com.myfinal.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.myfinal.dao.OrderDAO;
import com.myfinal.dao.ShipDAO;
import com.myfinal.dao.UserDAO;
import com.myfinal.pojo.Order;
import com.myfinal.pojo.Shipment;
import com.myfinal.pojo.User;

@Controller
@RequestMapping("/ship/*")
public class ShipController {

	@Autowired
	@Qualifier("orderDao")
	OrderDAO orderDao; 
	
	@Autowired
	@Qualifier("userDao")
	UserDAO userDao;
	
	@Autowired
	@Qualifier("shipDao")
	ShipDAO shipDao;
	
	@RequestMapping(value="/ship/manage", method = RequestMethod.GET)
	public ModelAndView OutStock(HttpServletRequest request) throws Exception {		
		ModelAndView mv = new ModelAndView();	
		List<Order> orderlist=orderDao.listAllOrder();
		List<Order> neworders = new ArrayList<Order>();
		for(Order order : orderlist) {
			if(order.getStatus().equals("confirmed")) {
				neworders.add(order);
			}
		}
		mv.addObject("orderlist", neworders);
		mv.setViewName("manageship");
		return mv;
	}
	
	@RequestMapping(value="/ship/manage", method = RequestMethod.POST)
	public ModelAndView ShipOrder(HttpServletRequest request) throws Exception {		
		HttpSession session = (HttpSession) request.getSession();
		User user = (User)session.getAttribute("user");
		Date date = new Date();
		//Shipment ship = new Shipment(user.getUsername(),date);
		String[] orders = request.getParameterValues("orderid");	
		for(String orderid:orders) {
			Order order = orderDao.findOrder(Long.parseLong(orderid));
			order.setStatus("shipped");
			Shipment ship = new Shipment(user.getUsername(),date);
			order.setShip(ship);
			ship.setOrder(order);
			ship.setUser(user);
			shipDao.AddNewShipment(ship);
		}
		List<Order> orderlist=orderDao.listAllOrder();
		List<Order> temporder = new ArrayList<Order>();
		for(Order order : orderlist) {
			if(order.getStatus().equals("confirmed")) {
				temporder.add(order);
			}
		}
		return new ModelAndView("manageship","orderlist",temporder);
	}
	
	@RequestMapping(value="/ship/history", method = RequestMethod.GET)
	public ModelAndView OrderHistory(HttpServletRequest request) throws Exception {		
		ModelAndView mv = new ModelAndView();
		HttpSession session = (HttpSession) request.getSession();
		User user = (User)session.getAttribute("user");
		List<Shipment> shiplist = shipDao.listShip(user.getUserID());
		mv.addObject("shiplist", shiplist);
		mv.setViewName("shiphistory");
		return mv;
	}
}
