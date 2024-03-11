package com.jsp.shoppingcart.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.jsp.shoppingcart.dao.CartDao;
import com.jsp.shoppingcart.dao.CustomerDao;
import com.jsp.shoppingcart.dao.OrderDao;
import com.jsp.shoppingcart.dao.ProductDao;
import com.jsp.shoppingcart.dto.Cart;
import com.jsp.shoppingcart.dto.Customer;
import com.jsp.shoppingcart.dto.Item;
import com.jsp.shoppingcart.dto.Orders;
import com.jsp.shoppingcart.dto.Product;

@Controller
public class OrderController {
	
	@Autowired
	OrderDao dao;
	
	@Autowired
	CustomerDao custdao;
	
	@Autowired
	ProductDao pdao;//to reduce the item stock
	
	@Autowired
	CartDao cartdao;
	
	@RequestMapping("/addorder")
	public ModelAndView addOrders() {
		Orders o = new Orders();
		ModelAndView mav = new ModelAndView();
		mav.addObject("ordersobj",o);
		mav.setViewName("ordersform");
		return mav;
	}
	
	@RequestMapping("/saveorder")
	public ModelAndView saveOrder(@ModelAttribute("ordersobj") Orders o, HttpSession session) //when customer logged in we store the data in httpsession
	{
		Customer c = (Customer) session.getAttribute("customerinfo");
		Customer customer =  custdao.findCustomerById(c.getId());//to get the updated cart details we fetch the customer id again
		Cart cart = customer.getCart();//from customer i will fetch the cart
		
		List<Item> items = cart.getItems();//from cart i will get the list of items and we pass list of items to order
		
		List<Item> itemsList = new ArrayList<Item>();//we should not add all the items to this itemslist we add only those item which is lesser than the stock
		
		List<Item> itemswithGreaterQuantity = new ArrayList<Item>();//the quantity which are greter than the stock we add in this list
		
		
		for(Item i : items) {
			Product p = pdao.findProductById(i.getP_id());//fetching the products details bcz we want to check the products stock
			if(i.getQuantity() < p.getStock()) {
				itemsList.add(i);//those are add in this list those product quantity is lesser than the stock
				p.setStock(p.getStock()- i.getQuantity());//reduce the quantity of stock
				
				pdao.updateProduct(p);//update the product stock
			}
			else {
				itemswithGreaterQuantity.add(i);
			}
		}
		
		o.setItems(itemsList);
		double totalpriceoforder = 0;
		
		for(Item i : itemsList) {
			totalpriceoforder = totalpriceoforder + i.getPrice();
		}
		
		o.setTotalprice(totalpriceoforder);
		cart.setItems(itemswithGreaterQuantity);
		
		double totalprice = 0;
		for(Item i: itemswithGreaterQuantity) {
			totalprice = totalprice + i.getPrice();
		}
		
		cart.setTotalprice(totalprice);
//		Cart updatedCart = cartdao.removeAllItemsFromCart(cart.getId());//to make cart as empty we use removelallitemsfromcart we pass the id 
		
		List<Orders> orders = customer.getOrders();//fetch all the products from customer and one customer can do many orders
		if(orders.size() > 0) //means we have already one order so we addd another item to thise same order list
		{
			orders.add(o);
			customer.setOrders(orders);
		}
		else //when customer order for the first time then this block is execute
		{
			List<Orders> orders1 = new ArrayList<Orders>();
			orders1.add(o);
			customer.setOrders(orders1);
		}
		
		customer.setCart(cart);
		cartdao.updateCart(cart);
		dao.saveOrder(o);
		custdao.updateCustomer(customer);
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("msg","Order placed successfully");
		mav.addObject("ordersdetails",o);
		mav.setViewName("CustomerBill");
		
		return mav;
		
		//inside the bill we have list of items so for this we send req to ordersdetails to fetch the details
	}
}




//when ever quantity is greater than the stock we order that item otherwise display one message out of stock
