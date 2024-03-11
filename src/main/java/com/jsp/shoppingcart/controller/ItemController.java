package com.jsp.shoppingcart.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.jsp.shoppingcart.dao.CartDao;
import com.jsp.shoppingcart.dao.CustomerDao;
import com.jsp.shoppingcart.dao.ItemDao;
import com.jsp.shoppingcart.dao.ProductDao;
import com.jsp.shoppingcart.dto.Cart;
import com.jsp.shoppingcart.dto.Customer;
import com.jsp.shoppingcart.dto.Item;
import com.jsp.shoppingcart.dto.Product;

@Controller
public class ItemController {
	
	@Autowired
	ItemDao dao;
	
	@Autowired
	ProductDao pdao;
	
	@Autowired
	CartDao cdao;
	
	@Autowired
	CustomerDao custdao;
	
	@RequestMapping("/additem")
	public ModelAndView addItem(@RequestParam("id") int id) {
		Product p = pdao.findProductById(id);
		ModelAndView mav = new ModelAndView();
		mav.addObject("prodobj", p);
		mav.setViewName("itemform");
		return mav;
	}
	
	@RequestMapping("/additemtocart")
	public ModelAndView addItemTocart(ServletRequest req,HttpSession session) {
		int product_id = Integer.parseInt(req.getParameter("id"));
		String brand = req.getParameter("brand");
		double price = Double.parseDouble(req.getParameter("price"));
		String model = req.getParameter("model");
		String category = req.getParameter("category");
		int quantity = Integer.parseInt(req.getParameter("quantity"));
		
		
		Item item = new Item();
		item.setBrand(brand);
		item.setCatagory(category);
		item.setModel(model);
		item.setQuantity(quantity);
//		once after customer buys product we reduce the quantity then we use product_id
		item.setP_id(product_id);//product is for merchant and item is for customer
		item.setPrice(quantity*price);//
		
		Customer customer = (Customer) session.getAttribute("customerinfo");
		Cart c = customer.getCart();//fetching cart from the customer and we get null bcz we fetch for the first time
		
		
//		if the no cart is created for the customer
		if(c == null) {
//			double totalprice = 0;
			Cart cart = new Cart();//first save the cart
			
			List<Item> items = new ArrayList<>();
			items.add(item);
			
			cart.setItems(items);
			cart.setName(customer.getName());//fetching name from customer and providing name to the customer
		
//			inside cart we have another variable called as total price so we have to multiply all the itmes who added in the carts
//			for(Item i : items) {
//				totalprice = totalprice + i.getPrice();
//			}
//			
//			cart.setTotalprice(totalprice);
			
			
			cart.setTotalprice(item.getPrice());
			
			customer.setCart(cart);
			 
			dao.saveItem(item);//item has been save at the first time
			cdao.saveCart(cart);
			
			custdao.updateCustomer(customer);//update the customer after once save the cart 
		}
		else {
			List<Item> items = c.getItems();
			if(items.size() > 0) //if the items is present in the cart then if block execute
			{
				items.add(item);
				c.setItems(items);
				double totalprice = 0;
				for(Item i : items)
					totalprice = totalprice + i.getPrice();
				c.setTotalprice(totalprice) ;
				customer.setCart(c);
				
				dao.saveItem(item);
				cdao.updateCart(c);
				custdao.updateCustomer(customer);
			}
			else //if the items is not present then else block is execute
			{
				List<Item> itemslist = new ArrayList<Item>();
				itemslist.add(item);
				c.setItems(itemslist);
				c.setTotalprice(item.getPrice());//bcz this block execute for the first time so then we no need to calculate totalprice
			}
		}
		ModelAndView mav = new ModelAndView();
		mav.addObject(mav);
		mav.setViewName("redirect://displayproducts");
		return mav;
	}
}











