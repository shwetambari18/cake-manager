package com.waracle.cakemanager.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.waracle.cakemanager.exception.CakeNotFoundException;
import com.waracle.cakemanager.model.CakeEntity;
import com.waracle.cakemanager.model.CakeRepository;

@Service
@Transactional
public class CakeDaoService {

    @Autowired
    CakeRepository repo;
     
    public CakeEntity save(CakeEntity cake) throws Exception {
        return repo.save(cake);
    }
     
    public List<CakeEntity> listAll() {
        return (List<CakeEntity>) repo.findAll();
    }
     
    public CakeEntity getCakeById(Long id) throws CakeNotFoundException {
    	Optional<CakeEntity> cake = repo.findById(id);
		if(!cake.isPresent())
			 throw new CakeNotFoundException("id "+ id);
        return repo.findById(id).get();
    }
     
    public void delete(Long id) throws CakeNotFoundException {
    	Optional<CakeEntity> cake = repo.findById(id);
		if(!cake.isPresent())
			 throw new CakeNotFoundException("id "+ id);
        repo.deleteById(id);
    }
}