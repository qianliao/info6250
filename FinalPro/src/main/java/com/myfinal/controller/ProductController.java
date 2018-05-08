package com.myfinal.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.myfinal.dao.CartDAO;
import com.myfinal.dao.UserDAO;
import com.myfinal.exception.ProductException;
import com.myfinal.pojo.Cart;
import com.myfinal.pojo.User;
import com.myfinal.pojo.Product;
import com.myfinal.pojo.Order;
import com.myfinal.dao.ProductDAO;
import com.myfinal.dao.OrderDAO;

@Controller
@RequestMapping("/sell/*/**")
public class ProductController {
	
	@Autowired
	@Qualifier("cartDao")
	CartDAO cartDao;
	
	@Autowired
	@Qualifier("userDao")
	UserDAO userDao;
		
	@Autowired
	@Qualifier("proDao")
	ProductDAO proDao;
	
	@Autowired
	@Qualifier("orderDao")
	OrderDAO orderDao;
	
	@RequestMapping(value="/sell/manage", method = RequestMethod.GET)
	public ModelAndView initializeForm(HttpServletRequest request) throws Exception {		
		ModelAndView mv = new ModelAndView();	
		mv.setViewName("managepage");
		return mv;
	}
	
	@RequestMapping(value="/sell/manage/update", method = RequestMethod.GET)
	public ModelAndView UpdateProduct(HttpServletRequest request) throws Exception {		
		ModelAndView mv = new ModelAndView();	
		mv.addObject("product", new Product());
		List<Product> prolist = proDao.ListAllPro();
		mv.addObject("prolist",prolist);
		mv.setViewName("updatepro");
		return mv;
	}
	
	@RequestMapping(value="/sell/manage/update", method = RequestMethod.POST)
	public ModelAndView UpdateStockAndPrice(@ModelAttribute("product")  @Validated Product product, BindingResult result) throws Exception {		
        if(result.hasErrors()){

    		ModelAndView mv = new ModelAndView();	
    		mv.addObject("product", product);
    		List<Product> prolist = proDao.ListAllPro();
    		mv.addObject("prolist",prolist);
    		mv.setViewName("updatepro");
    		return mv;
        }
		
		try {			
			
			User u = userDao.FindUser(product.getVerifiedId());
			System.out.println(product.getVerifiedId());
			product.setUser(u);
			System.out.println(product.getProductName()+"+"+product.getVerifiedId());
			Product pro = proDao.FindPro(product.getProductName());
			pro = proDao.UpdateProduct(pro, product.getStock(), product.getPrice());		   
		    return new ModelAndView("update-success", "product",pro );
			
		} catch (ProductException e) {
			System.out.println(e.getMessage());
			return new ModelAndView("error", "errorMessage", "error while updating products");
		}
	}
	
	//Èë¿â
	@RequestMapping(value="/sell/manage/in", method = RequestMethod.GET)
	public ModelAndView InStock(HttpServletRequest request) throws Exception {		
		ModelAndView mv = new ModelAndView();	
		mv.addObject("sell", new Product());
		mv.addObject("user", new Product().getUser());
		mv.setViewName("addtostock");
		return mv;
	}
	
	@RequestMapping(value = "/sell/manage/in", method = RequestMethod.POST)
	public ModelAndView addToStore(@ModelAttribute("sell")  @Validated Product sell, BindingResult result) throws Exception {

        if(result.hasErrors()){

        	return new ModelAndView("addtostock", "sell", sell);
        }
		try {			
			
			User u = userDao.FindUser(sell.getVerifiedId());
			System.out.println(sell.getVerifiedId());
			sell.setUser(u);
			System.out.println(sell.getProductName()+"+"+sell.getVerifiedId());
			Product pro = proDao.FindPro(sell.getProductName());

			if(pro!=null) {
				return new ModelAndView("error", "errorMessage","This item existed" );		
			}
			else {
				sell = proDao.AddToStore(sell);
			}
		   
		    return new ModelAndView("addtostock", "product",sell );
			
		} catch (ProductException e) {
			System.out.println(e.getMessage());
			return new ModelAndView("error", "errorMessage", "error while adding to store");
		}
		
		
	}
	
	@RequestMapping(value="/sell/manage/out", method = RequestMethod.GET)
	public ModelAndView OutStock(HttpServletRequest request) throws Exception {		
		ModelAndView mv = new ModelAndView();	
		List<Order> orderlist=orderDao.listAllOrder();
		List<Order> temporder = new ArrayList<Order>();
		for(Order order : orderlist) {
			if(order.getStatus().equals("processing")) {
				temporder.add(order);
			}
		}
		mv.addObject("orderlist", temporder);
		mv.setViewName("outstock");
		return mv;
	}
	
	@RequestMapping(value="/sell/manage/out", method = RequestMethod.POST)
	public ModelAndView ShipOrder(HttpServletRequest request) throws Exception {		
		
		String[] orders = request.getParameterValues("orderid");	
		for(String orderid:orders) {
			Order order = orderDao.findOrder(Long.parseLong(orderid));
			order.setStatus("confirmed");
		}
		List<Order> orderlist=orderDao.listAllOrder();
		List<Order> temporder = new ArrayList<Order>();
		for(Order order : orderlist) {
			if(order.getStatus().equals("processing")) {
				temporder.add(order);
			}
		}
		return new ModelAndView("outstock","orderlist",temporder);
	}

}
