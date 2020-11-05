package org.rfc.menu;

import javax.annotation.Resource;

import org.rfc.config.SAPUserBean;
import org.rfc.sap.SapUserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
public class MenuController {
	
	@Autowired
	private SAPUserBean sapUser;
	
	@RequestMapping("/menu")
	public ModelAndView rfcMenu() {
		ModelAndView mv=new ModelAndView();
		mv.addObject("viewName", "RFC menu");
		mv.addObject("viewId",1);
		mv.setViewName("menu");
		return mv;
	}
	
	@RequestMapping("/po/create")
	public ModelAndView poCreate() {
		ModelAndView mv=new ModelAndView();
		mv.addObject("viewName", "Create PO");
		mv.addObject("viewId",2);
		mv.addObject("sapUser",sapUser.getUserString());
		mv.setViewName("po_create");
		return mv;
	}
	
	@RequestMapping("/po/detail")
	public ModelAndView poDetail() {
		ModelAndView mv=new ModelAndView();
		mv.addObject("viewName", "View PO");
		mv.addObject("viewId",3);
		mv.addObject("sapUser",sapUser.getUserString());
		mv.setViewName("po_detail");
		return mv;
	}
	
	@RequestMapping("/material/salesPrice")
	public ModelAndView salesPrice() {
		ModelAndView mv=new ModelAndView();
		mv.addObject("viewName", "Change sales price");
		mv.addObject("viewId",4);
		mv.addObject("sapUser",sapUser.getUserString());
		mv.setViewName("sales_price");
		return mv;
	}
	
	@RequestMapping("/po/confirmations")
	public ModelAndView confirmations() {
		ModelAndView mv=new ModelAndView();
		mv.addObject("viewName", "Order Confirmations");
		mv.addObject("viewId",5);
		mv.addObject("sapUser",sapUser.getUserString());
		mv.setViewName("confirmations");
		return mv;
	}

}
