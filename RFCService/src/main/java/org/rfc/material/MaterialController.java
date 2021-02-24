package org.rfc.material;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.rfc.config.SAPUserBean;
import org.rfc.material.dto.WorkerDTO;
import org.rfc.material.worker.WorkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/material")
public class MaterialController {
	
	private static final Logger logger=LogManager.getLogger(MaterialController.class);
	
	@Autowired
	private SAPUserBean sapUser;
	
	@Autowired
	private MaterialService materialService;
	
	@Autowired
	private WorkerService workerService;
	
	@RequestMapping("/main")
	public ModelAndView createMaterial() {
		ModelAndView mv=new ModelAndView();
		mv.addObject("viewName", "Create Material");
		mv.addObject("viewId",6);
		mv.addObject("sapUser",sapUser.getUserString());
		mv.setViewName("create_material_main");
		return mv;
	}
	
	@RequestMapping("/runs")
	public ModelAndView createMaterialRun() {
		ModelAndView mv=new ModelAndView();
		mv.addObject("viewName", "Create Material - Runs");
		mv.addObject("viewId",7);
		mv.addObject("sapUser",sapUser.getUserString());
		mv.addObject("templates",materialService.getTemplates());
		mv.setViewName("create_material_runs");
		return mv;
	}
	
	@RequestMapping("/execute")
	public ModelAndView executeRun(@RequestParam("runId") int runId) {
		ModelAndView mv=new ModelAndView();
		mv.addObject("viewName", "Create Material - Execute");
		mv.addObject("viewId",9);
		mv.addObject("sapUser",sapUser.getUserString());
		mv.addObject("run",materialService.getRun(runId));
		mv.addObject("workers",workerService.getActiveWorkers(runId));
		mv.setViewName("create_material_exec");
		return mv;
	}
	
	@RequestMapping("/template")
	public ModelAndView createMaterialTemplates() {
		ModelAndView mv=new ModelAndView();
		mv.addObject("viewName", "Create Material - Templates");
		mv.addObject("viewId",8);
		mv.addObject("sapUser",sapUser.getUserString());
		mv.addObject("fieldMap", materialService.getFieldMap());
		mv.addObject("templates",materialService.getTemplates());
		mv.setViewName("create_material_templates");
		return mv;
	}
	
}
