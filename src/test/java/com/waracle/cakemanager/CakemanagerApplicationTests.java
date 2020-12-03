package com.waracle.cakemanager;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.waracle.cakemanager.controller.CakeController;
import com.waracle.cakemanager.model.CakeEntity;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class CakemanagerApplicationTests {

	@Autowired
	private CakeController cakeController;
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
    private WebApplicationContext wac;
	
	private CakeEntity cakeEntity;
	
	@Test
	void contextLoads() {
		assertThat(cakeController).isNotNull();
	}
	
	@BeforeEach
	void setup()
	{
		DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac);
        this.mockMvc = builder.build();
	}
	
	@Test
	@Order(1)
	public void canRetriveAllCakes() throws Exception {
		List<CakeEntity> cakeList = new ArrayList<>(); 
		cakeList.add(new CakeEntity(1L, "Blackforest", "Chocolate","Small","Freshcream",new BigDecimal("21.50")));
		cakeList.add(new CakeEntity(2L, "Unicorn", "Vanilla","Large","Freshcream",new BigDecimal("11.00")));
		cakeList.add(new CakeEntity(3L, "DarkFantasy", "Chocolate","Medium","Buttercream",new BigDecimal("17.35")));
		cakeList.add(new CakeEntity(4L, "Pineapple", "Vanilla","Small","Freshcream",new BigDecimal("18.50")));
		cakeList.add(new CakeEntity(5L, "Choco Berry", "Cheese","Medium","Fondant",new BigDecimal("20.00")));
		
		ResultMatcher ok = MockMvcResultMatchers.status().isOk();
		ResultMatcher matcher = MockMvcResultMatchers.model().attribute("cakeList", cakeList);
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/");
	    this.mockMvc.perform(builder)
	                   .andExpect(ok)
	                   .andExpect(matcher);
	}
	
	@Test
	@Order(2)
	public void canAddCake_ForValidInput() throws Exception {
		ResultMatcher ok = MockMvcResultMatchers.status().isOk();
		String message =  "Cake added successfully!";
		
		cakeEntity = new CakeEntity(5L, "Choco Berry", "Cheese","Medium","Fondant",new BigDecimal("20.00"));
		
		ResultMatcher matcher = MockMvcResultMatchers.model().attribute("message", message);
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/addCake").flashAttr("cake", cakeEntity);
	    this.mockMvc.perform(builder)
	                   .andExpect(ok)
	                   .andExpect(matcher);
	}
	
	@Test
	public void canNotAddCake_IfAlreadyExists() throws Exception {
		ResultMatcher ok = MockMvcResultMatchers.status().isOk();
		String message =  "Cake already exists in a system.";
		cakeEntity = new CakeEntity(4L, "Pineapple", "Vanilla","Small","Freshcream",new BigDecimal("18.50"));
		
		ResultMatcher matcher = MockMvcResultMatchers.model().attribute("message", message);
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/addCake").flashAttr("cake", cakeEntity);
	    this.mockMvc.perform(builder)
	                   .andExpect(ok)
	                   .andExpect(matcher);
	}
	
	@Test
	public void canNotAddCake_IfNullValue() throws Exception {
		ResultMatcher ok = MockMvcResultMatchers.status().isOk();
		String message =  "Error adding Cake!";
		cakeEntity = new CakeEntity(6L, "Raspberry", "Fruit","Small","",new BigDecimal("18.50"));
		
		ResultMatcher matcher = MockMvcResultMatchers.model().attribute("message", message);
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/addCake").flashAttr("cake", cakeEntity);
	    this.mockMvc.perform(builder)
	                   .andExpect(ok)
	                   .andExpect(matcher);
	}


}
