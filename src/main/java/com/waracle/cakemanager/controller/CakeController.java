package com.waracle.cakemanager.controller;

import java.util.List;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import org.slf4j.Logger;
import com.waracle.cakemanager.model.CakeEntity;
import com.waracle.cakemanager.service.CakeDaoService;


@Controller
public class CakeController {

	@Autowired
	private CakeDaoService service;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@RequestMapping(value =  "/", method = RequestMethod.GET)
	public @ResponseBody ModelAndView retrieveAllCakes()
	{
		ModelAndView model = new ModelAndView("cakes","cakeList",service.listAll());
		return model;
	}
	
	@RequestMapping( value = "/addCake", method = RequestMethod.POST)
	public @ResponseBody ModelAndView addCake(@ModelAttribute("cake") CakeEntity cake)
	{
		List<CakeEntity> cakeList = service.listAll();
		CakeEntity savedCake = null;
		String message="";
		boolean cakeAlreadyExists = false;
		if(!cakeList.contains(cake)) {
			try
			{
				savedCake = service.save(cake);
			}catch (Exception e) {
				logger.info("Error adding cake "+e.getStackTrace());
			}
		}else
		{
			cakeAlreadyExists = true;
		}
		
		ModelAndView model = new ModelAndView("cakeAdditionResponse");
		if(savedCake != null) {
			message =  "Cake added successfully!";
			logger.info("New Cake added "+cake.toString());
		}
		else if(cakeAlreadyExists) {
			message = "Cake already exists in a system.";
			logger.info("Cake already exists "+cake.toString());
		}
		else
			message = "Error adding Cake!";
		model.addObject("message",message);
		
		return model;
	}
	
}

