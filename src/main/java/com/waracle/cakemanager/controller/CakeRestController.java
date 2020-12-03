package com.waracle.cakemanager.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URI;
import java.util.List;
import javax.validation.Valid;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.google.gson.Gson;
import com.waracle.cakemanager.exception.CakeAlreadyExistsException;
import com.waracle.cakemanager.exception.CakeNotFoundException;
import com.waracle.cakemanager.model.CakeEntity;
import com.waracle.cakemanager.service.CakeDaoService;


@RestController
public class CakeRestController {

	@Autowired
	private CakeDaoService service;
	
	@GetMapping("/rest/cakes/{id}")
	public ResponseEntity<CakeEntity> findById(@Valid @PathVariable long id) throws CakeNotFoundException
	{
		CakeEntity cake = service.getCakeById(id);
		ResponseEntity<CakeEntity> cakeResponse = ResponseEntity.ok().body(cake);
		return cakeResponse;
	}
	
	@GetMapping("/rest/")
	public List<CakeEntity> retrieveAllCakes(){
		return service.listAll();
	}
	
	@PostMapping("/rest/cakes")
	public ResponseEntity<CakeEntity> createCake(@Valid @RequestBody CakeEntity cake) throws Exception
	{
		List<CakeEntity> cakeList = service.listAll();
		CakeEntity savedCake = null;
		if(!cakeList.contains(cake)) {
			try {
				savedCake = service.save(cake);
				URI location =  ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedCake.getId()).toUri();
			 	return ResponseEntity.created(location).body(savedCake);
			}catch(ConstraintViolationException e) {
				return ResponseEntity.badRequest().body(null);
			}
		}else
		{
			throw new CakeAlreadyExistsException("Cake already exists");
		}
	}
	
	@DeleteMapping("/rest/cakes/{id}")
	public void deleteById(@Valid @PathVariable long id) throws CakeNotFoundException
	{
		service.delete(id);
	}
	
	@GetMapping("/rest/cakes")
	public ResponseEntity<InputStreamResource> downloadJsonFile()  throws IOException 
	{
	    String cakesJson = new Gson().toJson(service.listAll());
	    cakesJson = cakesJson.replaceAll("\\+", " ").replaceAll("\\{", "\n\\{");
	
	    HttpHeaders headers = new HttpHeaders();
	    headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
	    headers.add("Pragma", "no-cache");
	    headers.add("Expires", "0");

	    return ResponseEntity
	            .ok()
	            .contentLength(cakesJson.getBytes().length)
	            .contentType(
	                    MediaType.parseMediaType("application/json"))
	            .header("Content-Disposition", "attachment; filename=\"cakes.json\"")
	            .body(new InputStreamResource(new ByteArrayInputStream(cakesJson.getBytes())));
	}
	
}

