package com.myfinal.controller;

import java.util.ArrayList;
import java.util.Collections;
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


import com.myfinal.pojo.User;
import com.myfinal.dao.CartDAO;
import com.myfinal.dao.ProductDAO;
import com.myfinal.dao.UserDAO;
import com.myfinal.pojo.Cart;
import com.myfinal.pojo.Product;
import com.myfinal.exception.ProductException;;

@Controller
@RequestMapping("/cart/*")
public class CartController {
	@Autowired
	@Qualifier("cartDao")
	CartDAO cartDao;
	
	@Autowired
	@Qualifier("userDao")
	UserDAO userDao;
	
	@Autowired
	@Qualifier("proDao")
	ProductDAO proDao;
	
	@RequestMapping(value="/cart/orderby", method = RequestMethod.GET)
	public ModelAndView OrderbyPrice(HttpServletRequest request) throws Exception {		
		ModelAndView mv = new ModelAndView();	
		HttpSession session = (HttpSession) request.getSession();
		String action = request.getParameter("action");
		String query = (String)session.getAttribute("queryString");
		if(action.equals("asc")) {
			List<Product> allpro = proDao.OrderbyHigh();
			mv.addObject("allpro", allpro);
		}else if(action.equals("desc")){
			List<Product> allpro = proDao.OrderbyLow();
			mv.addObject("allpro", allpro);			
		}
		else if(action.equals("sasc")){
			List<Product> allpro = proDao.SearchResultsAsc(query);
			mv.addObject("allpro", allpro);			
		}else if(action.equals("sdesc")){
			List<Product> allpro = proDao.SearchResultsDesc(query);
			mv.addObject("allpro", allpro);			
		}
		mv.addObject("cart", new Cart());
		mv.setViewName("shoppage");
		return mv;
	}
	
	@RequestMapping(value="/cart/add", method = RequestMethod.GET)
	public ModelAndView initializeForm(HttpServletRequest request) throws Exception {		
		ModelAndView mv = new ModelAndView();	
		String queryString = request.getParameter("queryString");
		HttpSession session = (HttpSession) request.getSession();
		session.setAttribute("queryString", queryString);
		List<Product> searchResults = proDao.SearchProduct(queryString);
		List<Product> allpro = proDao.ListAllPro();
		if(queryString==null) {
			mv.addObject("allpro", allpro);
			mv.addObject("message", "No such Items");
		}else {
			mv.addObject("searchResults", searchResults);
			mv.addObject("message", "Following is your search results!");
		}
		mv.addObject("cart", new Cart());
		mv.setViewName("shoppage");
		return mv;
	}
	
	
	@RequestMapping(value = "/cart/add", method = RequestMethod.POST)
	public ModelAndView addToCart(HttpServletRequest request , @ModelAttribute("cart")  @Validated Cart cart, BindingResult result) throws Exception {
		if(result.hasErrors()){
			ModelAndView mv = new ModelAndView();
			List<Product> allpro = proDao.ListAllPro();
			mv.addObject("allpro", allpro);
			mv.addObject("cart", cart);
			mv.setViewName("shoppage");
			return mv;
        }
		try {			
			
			User u = userDao.FindUser(cart.getVerifiedId());
			cart.setUser(u);
			System.out.println(cart.getProductName()+"+"+cart.getVerifiedId());
			boolean flag=cartDao.FindOrderID(cart.getProductName(),cart.getVerifiedId());//cuo
			
			if(flag){
				Cart pro = cartDao.FindPro(cart.getProductName(),cart.getVerifiedId());
				if(pro!=null) {
					pro.setVerifiedId(cart.getVerifiedId());
					cart=cartDao.UpdateCart(pro, cart);
				}
				else {
					cart = cartDao.AddToCart(cart);
				}
			}else {
				Cart pro = cartDao.FindPro(cart.getProductName(),cart.getVerifiedId());
				if(pro!=null) {
					pro.setVerifiedId(cart.getVerifiedId());
					cart=cartDao.UpdateCart(pro, cart);
				}
				else {
					cart = cartDao.AddToCart(cart);
				}
			}
			return new ModelAndView("shop-success", "product", cart);
			
		} catch (ProductException e) {
			System.out.println(e.getMessage());
			return new ModelAndView("error", "errorMessage", "error while adding to cart");
		}
		
		
	}
	
	
	@RequestMapping(value = "/cart/list", method = RequestMethod.GET)
	public ModelAndView ListProducts(HttpServletRequest request) throws Exception {
		try {
			HttpSession session = (HttpSession) request.getSession();
			User user = (User)session.getAttribute("user");
			System.out.println(user.getUserID());
			List<Cart> productList = cartDao.listAllProduct(user.getUserID());
			List<Cart> tempcart = new ArrayList<Cart>();
			for(Cart pro :productList) {
				if(pro.getOrder()==null) {
					tempcart.add(pro);
					System.out.println(pro);
				}
			}
			System.out.println("now list cart");
			System.out.println(tempcart);
			return new ModelAndView("mycart", "productList",tempcart);
			
		} catch (ProductException e) {			
			return new ModelAndView("error", "errorMessage", "error while list cart");
		}
		
		
	}
	
	@RequestMapping(value = "/cart/delete", method = RequestMethod.GET)
	public ModelAndView removeProduct(HttpServletRequest request) throws Exception {
		try {			
			String removePro = request.getParameter("productname");
			HttpSession session = (HttpSession) request.getSession();
			User user = (User)session.getAttribute("user");
			List<Cart> productList = cartDao.listAllProduct(user.getUserID());
			List<Cart> tempcart = new ArrayList<Cart>();
			for(Cart pro :productList) {
				if(pro.getOrder()==null) {
					tempcart.add(pro);
				}
			}
			for(Cart pro:tempcart) {
				if(pro.getProductName().equals(removePro)) {
					cartDao.RemoveFromCart(pro);
					tempcart.remove(pro);
					break;
				}
			}
						
			return new ModelAndView("mycart", "productList", tempcart);
			
		} catch (ProductException e) {
			System.out.println(e.getMessage());
			return new ModelAndView("error", "errorMessage", "error while delete");
		}
		
		
	}


}
