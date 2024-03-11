package com.jsp.shoppingcart.controller;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.jsp.shoppingcart.dao.MerchantDao;
import com.jsp.shoppingcart.dao.ProductDao;
import com.jsp.shoppingcart.dto.Merchant;
import com.jsp.shoppingcart.dto.Product;

@Controller
public class ProductController {
	
	@Autowired
	ProductDao dao;
	
	@Autowired
	MerchantDao mdao;
	
	@RequestMapping("/addproduct")
	public ModelAndView addProduct() {
		Product p = new Product();
		ModelAndView mav = new ModelAndView();
		mav.addObject("productobj",p);
		mav.setViewName("productform");
		return mav;
	}
	
	@RequestMapping("/saveproduct")
	public ModelAndView saveProduct(@ModelAttribute("productobj") Product p, HttpSession session) {
		Merchant merchant = (Merchant) session.getAttribute("merchantinfo");
		
//		to check the details of product which user previously entered on not
		List<Product> products = merchant.getProducts();
		if(products.size() > 0) {
			products.add(p);
		}
		else {
			List<Product> productslist = new ArrayList<Product>();
			productslist.add(p);
			
			merchant.setProducts(productslist);
		}
		
		dao.saveProduct(p);
		mdao.updateMerchant(merchant);
		ModelAndView mav = new ModelAndView();
		mav.addObject("msg","data saved successfully");
		mav.setViewName("merchantoptions");
		return mav;
	}
	
	@RequestMapping("/deleteproduct") //in httpsession if we provide same key then it replace the old value to new value
	public ModelAndView deleteProduct(@RequestParam("id")int id,HttpSession session)
	{
		Merchant merchant = (Merchant) session.getAttribute("merchantinfo");
		Merchant m = mdao.deleteProductFromMerchant(merchant.getId(), id);
		
		mdao.updateMerchant(m);
		dao.deleteProductById(id);
		
		session.setAttribute("merchantinfo",m);
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("viewallproducts");
		return mav;
	}
	
	
	
	
	
	
//	@RequestMapping("/updateproduct")
//	public ModelAndView updateProduct(@RequestParam("id")int id,HttpSession session)
//	{
//		Merchant merchant = (Merchant) session.getAttribute("merchantinfo");
//		Merchant m = mdao.updateProductFromMerchant(merchant.getId(), id);
//		
//		mdao.updateMerchant(m);
//		dao.updateProduct(null);
//		
//		session.setAttribute("merchantinfo",m);
//		
//		ModelAndView mav = new ModelAndView();
//		mav.setViewName("viewallproducts");
//		return mav;
//	}
	
	
	
	
	
	
	@RequestMapping("/displayproducts")
	public ModelAndView displayProducts() {
		List<Product> products = dao.findAllProducts();
		ModelAndView mav = new ModelAndView();
		mav.addObject("productslist", products);
		mav.setViewName("viewallproductstocustomer");
		return mav;
	}
	
	@RequestMapping("/displayproductsbybrand")
	public ModelAndView displayProductsByBrand(ServletRequest req) {
		String brand = req.getParameter("brand");
		List<Product> products =  dao.findProductByBrand(brand);
		ModelAndView mav = new ModelAndView();
		mav.addObject("productslist", products);
		mav.setViewName("viewallproductstocustomer");
		return mav;
	}
	
	@RequestMapping("/displayproductsbycategory")
	public ModelAndView displayProductsByCategory(ServletRequest req) {
		String category = req.getParameter("category");
		List<Product> products =  dao.findProductBycategory(category);
		ModelAndView mav = new ModelAndView();
		mav.addObject("productslist", products);
		mav.setViewName("viewallproductstocustomer");
		return mav;
	}
	
	
	
	
//	@RequestMapping("/displayproductsbyrange")
//	public ModelAndView displayProductsByRange(@RequestParam("range") String req) {
//		String[] rangeValues = range.split("-");
//		double minPrice = Double.parseDouble(rangeValues[0]);
//	    double maxPrice = Double.parseDouble(rangeValues[1]);
//	    
//		List<Product> products =  dao.findProductByRange(minPrice, maxPrice);
//		ModelAndView mav = new ModelAndView();
//		mav.addObject("productslist", products);
//		mav.setViewName("viewallproductstocustomer");
//		return mav;
//	}
}
