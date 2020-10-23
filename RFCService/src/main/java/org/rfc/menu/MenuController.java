package org.rfc.menu;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
public class MenuController {
	
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
		mv.setViewName("po_create");
		return mv;
	}

}
