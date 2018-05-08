package com.myfinal.controller;

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

import com.myfinal.exception.UserException;
import com.myfinal.pojo.Person;
import com.myfinal.pojo.User;
import com.myfinal.dao.UserDAO;

@Controller
@RequestMapping("/user/*")
public class UserController {
	
	@Autowired
	@Qualifier("userDao")
	UserDAO userDao;
	
	
	@RequestMapping(value = "/user/login", method = RequestMethod.POST)
	protected ModelAndView loginUser(HttpServletRequest request) throws Exception {

		HttpSession session = (HttpSession) request.getSession();
		
		try {

			System.out.print("loginUser");

			User u = userDao.Verify(request.getParameter("username"), request.getParameter("password"));
			
			if(u == null){
				System.out.println("UserName/Password does not exist");
				return new ModelAndView("error", "errorMessage", "UserName/Password does not exist");
			}
			
			session.setAttribute("user", u);
			
			return new ModelAndView("homepage", "user", u);

		} catch (UserException e) {
			System.out.println("Exception: " + e.getMessage());
			return new ModelAndView("error", "errorMessage", "error while login");
		}

	}
	
	
	@RequestMapping(value = "/user/register", method = RequestMethod.GET)
	protected ModelAndView registerUser() throws Exception {
		System.out.print("registerUser");

		return new ModelAndView("register-user", "user", new User());

	}
	
	@RequestMapping(value = "/user/register", method = RequestMethod.POST)
	protected ModelAndView registerNewUser(HttpServletRequest request,  @ModelAttribute("user") @Validated User user, BindingResult result) throws Exception {
		HttpSession session = (HttpSession) request.getSession();
		
        if(result.hasErrors()){
//        	ModelAndView mv = new ModelAndView();
//        	mv.addObject("user", user);
////        	mv.addObject("userDetail", user.getUserDetail());
//        	mv.setViewName("register-user");
        	return new ModelAndView("register-user", "user", user);
        }

		try {

			System.out.print("registerNewUser");

			User u = userDao.registerUser(user);
			
			return new ModelAndView("homepage", "user", null);

		} catch (UserException e) {
			System.out.println("Exception: " + e.getMessage());
			return new ModelAndView("error", "errorMessage", "error while register");
		}
	}
	
	@RequestMapping(value = "/user/logout", method = RequestMethod.GET)
	protected ModelAndView logOut(HttpServletRequest request) throws Exception {
		try {
			HttpSession session = (HttpSession) request.getSession();
			System.out.print("log out");
			session.invalidate();
			return new ModelAndView("homepage", "user", null);

		} catch (Exception e) {
			System.out.println("Exception: " + e.getMessage());
			return new ModelAndView("error", "errorMessage", "error while logout");
		}	
	}

}
