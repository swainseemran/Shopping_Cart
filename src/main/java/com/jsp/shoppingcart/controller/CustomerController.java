package com.jsp.shoppingcart.controller;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.jsp.shoppingcart.dao.CustomerDao;
import com.jsp.shoppingcart.dao.MerchantDao;
import com.jsp.shoppingcart.dto.Customer;
import com.jsp.shoppingcart.dto.Merchant;

@Controller
public class CustomerController {
	
	@Autowired
	CustomerDao cdao;
	
	@RequestMapping("/addcustomer")
	public ModelAndView addMerchant() {
		Customer c = new Customer();
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("customerobj", c);
		mav.setViewName("customerform");
		
		return mav;
	}
	
	@RequestMapping("/savecustomer")
	public ModelAndView saveCustomer(@ModelAttribute("customerobj") Customer c) {
		cdao.saveCustomer(c);
		ModelAndView mav = new ModelAndView();
		mav.addObject("msg","Registered successfully");
		mav.setViewName("customerloginform");
		
		return mav;
	}
	
	@RequestMapping("/customerloginvalidation")
	public ModelAndView login(ServletRequest req, HttpSession session) {
		String email = req.getParameter("email");
		String password = req.getParameter("password");
		
		Customer c = cdao.login(email, password);
		ModelAndView mav = new ModelAndView();
		
		if(c != null) {
			mav.addObject("msg","Customer Successfully logged in");
			mav.setViewName("customeroptions");
			session.setAttribute("customerinfo", c);//we are storing customer information inside the session
			return mav;
		}
		else {
			mav.addObject("msg","customer provide invalid credentials");
			mav.setViewName("customerloginform");
			return mav;
		}
	}
}
