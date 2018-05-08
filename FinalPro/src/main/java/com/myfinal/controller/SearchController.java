package com.myfinal.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.myfinal.dao.PaymentDAO;
import com.myfinal.dao.ProductDAO;
import com.myfinal.dao.UserDAO;
import com.myfinal.pojo.PayInfo;
import com.myfinal.pojo.Product;
import com.myfinal.pojo.User;

import net.sf.json.JSONArray;

@Controller
@RequestMapping("/search/*")
public class SearchController {
	
	public SearchController(){
	}
	
	@Autowired
	@Qualifier("proDao")
	ProductDAO proDao;
	
	@Autowired
	@Qualifier("userDao")
	UserDAO userDao;
	
	@Autowired
	@Qualifier("payDao")
	PaymentDAO payDao;
	
	@RequestMapping(value = "/search/relate", method = RequestMethod.POST)
	@ResponseBody
	public void ajaxService(HttpServletRequest request,HttpServletResponse response)throws Exception
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");	
		String queryString = request.getParameter("proname");
		List<Product> allpro = proDao.ListAllPro();
		List<Product> results = new ArrayList<Product>();
		for(Product product:allpro) {
			if(product.getProductName().toLowerCase().contains(queryString.toLowerCase())) {
				results.add(product);
			}
		}
		response.getWriter().write(JSONArray.fromObject(results).toString());
		
	}
	
	@RequestMapping(value = "/search/check", method = RequestMethod.POST)
	@ResponseBody
	public String ajaxCheck(HttpServletRequest request,HttpServletResponse response)throws Exception
	{
		String queryString = request.getParameter("name");
		String action = request.getParameter("action");
		User existeduser = userDao.CheckUnique(queryString);
		User existedEmail=userDao.CheckEmailUnique(queryString);
		if(existeduser!=null) {
			return "Username has existed!";
		}	
		else if(existedEmail!=null) {
			return "This Email has existed";
		}
		return null;
	}
	
	@RequestMapping(value = "/search/paycheck", method = RequestMethod.POST)
	@ResponseBody
	public String ajaxCard(HttpServletRequest request,HttpServletResponse response)throws Exception
	{
		String queryString = request.getParameter("name");
		PayInfo payment = payDao.checkUnique(queryString );

		if(payment!=null) {
			return "This card has existed!";
		}	
		
		return null;
	}
}
;