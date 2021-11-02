package com.management.user;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.management.user.entities.Role;
import com.management.user.models.UserModel;

public class UserControllerTest extends UserManagementServiceApplicationTests{
	@Autowired
	private WebApplicationContext webApplicationContext;

	private static MockMvc mockMvc;

	@BeforeEach
	public void setup() {
		if (mockMvc == null) {
			synchronized (UserManagementServiceApplicationTests.class) {
				if (mockMvc == null) {
					mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
				}
			}
		}
	}

	@Test
	public void testGetUser() throws Exception {
		mockMvc.perform(get("/user-service/users/106")).andExpect(status().isOk())
				.andExpect(content().contentType("application/json")).andExpect(jsonPath("$.userId").value(106))
				.andExpect(jsonPath("$.userName").value("yuio")).andExpect(jsonPath("$.password").value("admin@1234"))
				.andExpect(jsonPath("$.firstName").value("admin"));
	}

	@Test
	public void testPostUser() throws Exception {
		String uri = "/user-service/users";
		UserModel userModel = new UserModel();
		userModel.setUserId(101);
		userModel.setUserName("yuio");
		userModel.setPassword("admin@1234");
		userModel.setFirstName("admin");

		String inputJson = mapToJson(userModel);
		MvcResult mvcResult = mockMvc.perform(
				MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
				.andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);

		String content = mvcResult.getResponse().getContentAsString();
		assertEquals(content, prepareResponseContent("OK", "User Successfully inserted", content));
	}

	@Test
	public void testUpdateUser() throws Exception {
		String uri = "/user-service/users";
		UserModel userModel = new UserModel();
		userModel.setUserId(106);
		Set<Role> roles = new HashSet<>();
		Role role = new Role();
		role.setRoleId(1005);
		roles.add(role);
		userModel.setRoles(roles);

		String inputJson = mapToJson(userModel);
		MvcResult mvcResult = mockMvc.perform(
				MockMvcRequestBuilders.put(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
				.andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);

		String content = mvcResult.getResponse().getContentAsString();
		assertEquals(content, prepareResponseContent("OK", "User Successfully updated", content));
	}

	@Test
	public void testDeleteUser() throws Exception {
		String uri = "/user-service/users/101";
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.delete(uri)).andReturn();
		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		String content = mvcResult.getResponse().getContentAsString();
		assertEquals(content, prepareResponseContent("OK", "User Successfully deleted", content));
	}

	private String prepareResponseContent(String status, String message, String content) {
		String[] splitContent = content.split(",");
		String timeStamp = splitContent[1];
		StringBuilder sb = new StringBuilder();
		sb.append("{\"status\":\"").append(status + "\",").append(timeStamp).append(",\"message\":\"")
				.append(message + "\"}");
		return sb.toString();
	}

	protected <T> T mapFromJsonNew(String json, Class<T> clazz)
			throws JsonParseException, JsonMappingException, IOException {

		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.readValue(json, clazz);
	}
}
