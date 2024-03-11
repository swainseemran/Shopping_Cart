package com.jsp.shoppingcart.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.jsp.shoppingcart.dao.CartDao;
import com.jsp.shoppingcart.dao.CustomerDao;
import com.jsp.shoppingcart.dto.Cart;
import com.jsp.shoppingcart.dto.Customer;
import com.jsp.shoppingcart.dto.Item;

@Controller
public class CartController {
	
	@Autowired
	CartDao dao;
	
	@Autowired
	CustomerDao custdao;
	
	@RequestMapping("/fetchitemsfromcart")
	public ModelAndView fetchItemsFromCart(HttpSession session) {
		Customer c = (Customer) session.getAttribute("customerinfo");//fetch data from http session, when customer loggd in then customer data store in side session  and we not go through the session bcz we cant get updated table
		
		Customer customer = custdao.findCustomerById(c.getId());//to get the updated cart we once again fetch the data bcz in http session the data is not updated then we provide same key or id  to update the data to new one
		Cart cart = customer.getCart();
		List<Item> items = cart.getItems();
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("itemslist", items);//store all the items
		mav.addObject("totalprice", cart.getTotalprice());//fetch the totalprice of cart
		mav.setViewName("displaycartitemstocustomer");
		return mav;
	}
}
