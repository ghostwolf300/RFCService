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

}
