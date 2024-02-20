package com.sandey.children.app.qrcode.controller;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sandey.children.app.qrcode.entities.Children;
import com.sandey.children.app.qrcode.entities.QrcodeStatus;
import com.sandey.children.app.qrcode.service.ChildrenService;
import static org.hamcrest.Matchers.hasSize;

@SpringBootTest
@AutoConfigureMockMvc
public class ChildrenControllerTest {
	@MockBean
	ChildrenService childrenService;
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private WebApplicationContext context;

	@BeforeEach
	public void init() {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
	}

	@Test
	public void testAddChildren() throws Exception {
		Children children = buildChildren();
		when(childrenService.addChildren(any())).thenReturn(children);
		String childJson = new ObjectMapper().writeValueAsString(children);
		mockMvc.perform(post("/Children/addChild").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON).content(childJson)).andExpect(status().isOk());
	}

	@Test
	public void testAddChildrenWithInvalidData() throws Exception {
		Children children = buildChildren();
		children.setName(null);
		when(childrenService.addChildren(any())).thenThrow(DataIntegrityViolationException.class);
		String childJson = new ObjectMapper().writeValueAsString(children);
		mockMvc.perform(post("/Children/addChild").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON).content(childJson)).andExpect(
						result -> assertTrue(result.getResolvedException() instanceof DataIntegrityViolationException));
	}

	@Test
	public void testFetchActiveChildren() throws Exception {
		Children children = buildChildren();
		children.getQrcodeStatus().setActive(true);
		List<Children> activeChildList = new ArrayList<>();
		activeChildList.add(children);
		when(childrenService.fetchActiveChildren()).thenReturn(activeChildList);
		mockMvc.perform(get("/Children/fetchActiveChildren").contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$", hasSize(1))).andExpect(status().isOk());
	}

	@Test
	public void testFetchActiveChildrenWith_EmptyList() throws Exception {
		when(childrenService.fetchActiveChildren()).thenReturn(new ArrayList<Children>());
		mockMvc.perform(get("/Children/fetchActiveChildren").contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$", hasSize(0))).andExpect(status().isOk());
	}

	private Children buildChildren() {
		Children children = new Children();
		children.setId(1);
		children.setAge(23);
		children.setGender("F");
		children.setName("Ravi");
		QrcodeStatus qrcodeStatus = new QrcodeStatus();
		qrcodeStatus.setActive(false);
		qrcodeStatus.setIsactive(false);
		qrcodeStatus.setId(1);
		children.setQrcodeStatus(qrcodeStatus);
		return children;
	}
}
