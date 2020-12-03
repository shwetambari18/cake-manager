package com.waracle.cakemanager;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.waracle.cakemanager.controller.CakeRestController;
import com.waracle.cakemanager.exception.CakeAlreadyExistsException;
import com.waracle.cakemanager.exception.CakeNotFoundException;
import com.waracle.cakemanager.model.CakeEntity;
import com.waracle.cakemanager.service.CakeDaoService;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class CakemanagerRESTApplicationTests {

	@Autowired
	private CakeRestController cakeController;
	
	@MockBean
	private CakeDaoService service;
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private TestRestTemplate restTemplate;
	
	private CakeEntity cakeEntity;
	
	@Test
	void contextLoads() {
		assertThat(cakeController).isNotNull();
	}
	
	@BeforeEach
	void setup()
	{
        this.mockMvc = MockMvcBuilders.standaloneSetup(cakeController).build();
	}
	
	@Test
	@Order(1)
	public void canRetriveAllCakes() throws Exception {
		List<CakeEntity> cakeList = new ArrayList<>(); 
		cakeList.add(new CakeEntity(1L, "Blackforest", "Chocolate","Small","Freshcream",new BigDecimal("21.50")));
		cakeList.add(new CakeEntity(2L, "Unicorn", "Vanilla","Large","Freshcream",new BigDecimal("11.00")));
		cakeList.add(new CakeEntity(3L, "DarkFantasy", "Chocolate","Medium","Buttercream",new BigDecimal("17.35")));
		cakeList.add(new CakeEntity(4L, "Pineapple", "Vanilla","Small","Freshcream",new BigDecimal("18.50")));
		
		when(service.listAll()).thenReturn(cakeList);

		MockHttpServletResponse response = mockMvc.perform(get("/rest/")
		                					.accept(MediaType.APPLICATION_JSON))
											.andReturn().getResponse();
		
		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		String jsonCakeList = new ObjectMapper().writeValueAsString(cakeList);
		assertThat(response.getContentAsString()).isEqualTo(jsonCakeList);
	}
	
	@Test
	@Order(2)
	public void canAddCake_ForValidInput() throws Exception {
		cakeEntity = new CakeEntity(5L, "ChocoBerry", "Cheese","Medium","Fondant",new BigDecimal("20.00"));
		when(service.save(cakeEntity)).thenReturn(cakeEntity);
	    ResponseEntity<String> cakeResponse = restTemplate.postForEntity("/rest/cakes/",cakeEntity, String.class);
        assertThat(cakeResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
	}
	
	@Test
	@Order(3)
    public void canNotRetrieveByIdWhenDoesNotExist() throws CakeNotFoundException {
		when(service.getCakeById(10L)).thenThrow(new CakeNotFoundException("id "+10));
        ResponseEntity<CakeEntity> cakeResponse = restTemplate.getForEntity("/rest/cake/20", CakeEntity.class);
        assertThat(cakeResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
	
	@Test
	@Order(4)
	public void canNotAddCake_IfNullValue() throws Exception {
		cakeEntity = new CakeEntity(6L, "Raspberry", "Fruit","Small","",new BigDecimal("18.50"));
		when(service.save(cakeEntity)).thenThrow(new ConstraintViolationException("Empty Field Violation", null));
		ResponseEntity<CakeEntity> cakeResponse = restTemplate.postForEntity("/rest/cakes", cakeEntity,CakeEntity.class);
		assertThat(cakeResponse.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
		assertThat(cakeResponse.getBody().getId()).isNull();
	}
	
	@Test
	@Order(5)
	public void canNotAddCake_IfAlreadyExists() throws Exception {
		cakeEntity = new CakeEntity(3L, "Pineapple", "Vanilla","Small","Freshcream",new BigDecimal("18.50"));
		when(service.save(cakeEntity)).thenThrow(new CakeAlreadyExistsException("Cake already exists"));
		ResponseEntity<CakeEntity> cakeResponse = restTemplate.postForEntity("/rest/cakes", cakeEntity,CakeEntity.class);
		assertThat(cakeResponse.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
		assertThat(cakeResponse.getBody().getId()).isNull();
	}
	
	@Test
	@Order(6)
    public void canRetrieveByIdWhenExist() throws CakeNotFoundException {
		cakeEntity = new CakeEntity(2L, "Unicorn", "Vanilla","Large","Freshcream",new BigDecimal("11.00"));
		when(service.getCakeById(2L)).thenReturn(cakeEntity);
        ResponseEntity<CakeEntity> cakeResponse = restTemplate.getForEntity("/rest/cakes/2", CakeEntity.class);
        assertThat(cakeResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(cakeResponse.getBody().equals(new CakeEntity(2L, "Unicorn", "Vanilla","Big","Freshcream",new BigDecimal("11.00"))));
    }
}
