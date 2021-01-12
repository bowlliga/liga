package com.csslect.app.raspcontroller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.csslect.app.raspcommand.RaspCommand;
import com.csslect.app.raspcommand.RaspGetDataCommand;
import com.csslect.app.raspcommand.RaspSetDataCommand;

@Controller
public class RaspController {
	
	RaspCommand command;
	
	@RequestMapping(value="/raspGetData", method = {RequestMethod.GET, RequestMethod.POST}  )
	public String arduGetLed(HttpServletRequest req, Model model){
		System.out.println("raspGetData()");
		
		command = new RaspGetDataCommand();
		command.execute(model);
		
		return "raspGetData";
	}
	
	@RequestMapping(value = "/raspSetData", method = {RequestMethod.GET, RequestMethod.POST})
	public String arduSetLed(HttpServletRequest req, Model model) {
		
	     System.out.println("store_id : " + req.getParameter("store_id"));     
	     System.out.println("store_name : " + req.getParameter("store_name")); 
	     System.out.println("table_num : " + req.getParameter("table_num")); 
	     System.out.println("table_value : " + req.getParameter("table_value")); 
	     
	     String store_id = req.getParameter("store_id");
	     String store_name = req.getParameter("store_name");
	     String table_num = req.getParameter("table_num");
	     String table_value = req.getParameter("table_value");
	     
	     model.addAttribute("store_id", store_id);
	     model.addAttribute("store_name", store_name);
	     model.addAttribute("table_num", table_num);
	     model.addAttribute("table_value", table_value);
	     
	     command = new RaspSetDataCommand();
	     command.execute(model); 
		
	     return "raspSetData";
	}
	
	

}
