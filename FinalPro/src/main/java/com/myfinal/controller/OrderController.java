package com.myfinal.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import com.myfinal.dao.CartDAO;
import com.myfinal.dao.OrderDAO;
import com.myfinal.dao.UserDAO;
import com.myfinal.exception.PayInfoException;
import com.myfinal.exception.ProductException;
import com.myfinal.dao.PaymentDAO;
import com.myfinal.dao.ProductDAO;
import com.myfinal.pojo.Cart;
import com.myfinal.pojo.Order;
import com.myfinal.pojo.PayInfo;
import com.myfinal.pojo.Product;
import com.myfinal.pojo.User;

@Controller
@RequestMapping("/order/*")
public class OrderController {

	@Autowired
	@Qualifier("cartDao")
	CartDAO cartDao;
	
	@Autowired
	@Qualifier("payDao")
	PaymentDAO payDao;
	
	@Autowired
	@Qualifier("orderDao")
	OrderDAO orderDao; 
	
	@Autowired
	@Qualifier("userDao")
	UserDAO userDao;
	
	@Autowired
	@Qualifier("proDao")
	ProductDAO proDao;
	

	
	@RequestMapping(value="/order/user-check", method = RequestMethod.GET)
	public ModelAndView initializeForm(HttpServletRequest request) throws Exception {		
		ModelAndView mv = new ModelAndView();	
		HttpSession session = (HttpSession) request.getSession();
		User user = (User)session.getAttribute("user");
		//payment list
		List<PayInfo> paylist = payDao.listPayment(user.getUserID());		
		System.out.println(paylist);
		mv.addObject("paylist", paylist);
		// check order's product list 
		List<Cart> productList = cartDao.listAllProduct(user.getUserID());
		List<Cart> tempcart = new ArrayList<Cart>();
		for(Cart pro :productList) {
			if(pro.getOrder()==null) {
				tempcart.add(pro);
			}
		}
		if(tempcart.size()==0) {
			mv.addObject("errorMessage","please add products");
			mv.setViewName("error");
			return mv;
		}else {
			System.out.println("now list cart");
			mv.addObject("productList", tempcart);
			mv.setViewName("user-checkout");
			return mv;
		}
	}

	@RequestMapping(value="/order/user-check", method = RequestMethod.POST)
	public ModelAndView addNewOrder(HttpServletRequest request) throws Exception {
		HttpSession session = (HttpSession) request.getSession();
		User user = (User)session.getAttribute("user");
		Order order = new Order();
		Date date = new Date();
		order.setStatus("processing");
		order.setOrderTime(date);
		order.setUser(user);
		List<Cart> productList = cartDao.listAllProduct(user.getUserID());
		List<Cart> tempcart = new ArrayList<Cart>();
		for(Cart pro :productList) {
			if(pro.getOrder()==null) {
				tempcart.add(pro);
			}
		}
		List<Product> originals=proDao.ListAllPro();
		for(Cart pro:tempcart) {
			for(Product product:originals) {
				if(pro.getProductName().equals(product.getProductName())) {
					if((product.getStock()-pro.getQuantity())>1) {
						proDao.UpdateOrderStock(pro, product);	
					}else {
						return new ModelAndView("error","errorMessage","stock not enough");
					}								
				}
			}		
			pro.setOrder(order);
			pro.setStock(pro.getStock()-pro.getQuantity());
		}
		order.setProductcart(tempcart);
		orderDao.AddNewOrder(order);
	    return new ModelAndView("check-success","message","your order has been confirmed");

	}
	
	@RequestMapping(value="/order/history", method = RequestMethod.GET)
	public ModelAndView OrderHistory(HttpServletRequest request) throws Exception {		
//		ModelAndView mv = new ModelAndView();
		HttpSession session = (HttpSession) request.getSession();
		User user = (User)session.getAttribute("user");
		List<Order> orderlist = orderDao.listConsumerOrder(user.getUserID());
//		
//		mv.addObject("orderlist", orderlist);
//		mv.setViewName("orderhistory");
		return new ModelAndView("orderhistory","orderlist", orderlist);
	}
	
	@RequestMapping(value="/order/payment", method = RequestMethod.GET)
	public ModelAndView addNewPay(HttpServletRequest request) throws Exception {		
		ModelAndView mv = new ModelAndView();	
		mv.addObject("payment", new PayInfo());
		mv.setViewName("payinfo");
		return mv;
	}
	
	@RequestMapping(value="/order/payment", method = RequestMethod.POST)
	public ModelAndView backToCheck(@ModelAttribute("payment") @Validated PayInfo payment, BindingResult result) throws Exception {		
		 if(result.hasErrors()){
        	return new ModelAndView("payinfo", "payment", payment);
        }
		try {			
			User u = userDao.FindUser(payment.getVerifiedId());
			System.out.println(payment.getVerifiedId());
			payment.setUser(u);
		    payment = payDao.AddUserPay(payment);
		    return new ModelAndView("redirect:/order/user-check", "payment", payment);
			
		} catch (PayInfoException e) {
			System.out.println(e.getMessage());
			return new ModelAndView("error", "errorMessage", "error while adding new payinfo");
		}
	}
	

}
