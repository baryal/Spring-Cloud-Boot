package com.example;
/**
 * Steps to create Mock Service test:
 * 1. Annotate test class with @RunWith(SpringRunner.class) and @WebMvcTest(value = EmployeeListController.class, secure = false)
 * 2. Create a MockMvc instance and annotate it with @Autowired private MockMvc mockMvc;
 * 3. Create a instance of service class and annotate with @MockBean. @MockBean private EmployeeService employeeService;
 * 4. Mock the service method return with Mockito.with(). Mockito.when(employeeService.getAllEmployee()).thenReturn(mockEmployees);
 * 5. Create a requestbuilder for the request. RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/employees").accept(MediaType.APPLICATION_JSON);
 * 6. Perform the request using mockMvc to get response. MvcResult result = mockMvc.perform(requestBuilder).andReturn();
 * 7. Assert the response(result.getResponse().getContentAsString()) with the expected result.
 */

import com.example.model.Employee;
import com.example.controller.EmployeeListController;
import com.example.service.EmployeeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 *  SpringRunner is short hand for SpringJUnit4ClassRunner
 *  which extends BlockJUnit4ClassRunner providing the functionality to
 *  launch a Spring TestContext Framework.
 */
@RunWith(SpringRunner.class)
/**
 * WebMvcTest annotation is used for unit testing Spring MVC application.
 * This can be used when a test focuses only Spring MVC components.
 * In this test, we want to launch only ReadingListController.
 * All other controllers and mappings will not be launched
 * when this unit test is executed.
 */
@WebMvcTest(value = EmployeeListController.class, secure = false)
//@SpringBootTest
//@DataJpaTest
public class SpringBootDemoApplicationTests {

	/**
	 * MockMvc is the main entry point for server-side Spring MVC test support.
	 * It allows us to execute requests against the test context.
	 */
	@Autowired
	private MockMvc mockMvc;

	/**
	 * MockBean is used to add mocks to a Spring ApplicationContext.
	 * A mock of studentService is created and auto-wired into the EmployeeListController.
	 */
	@MockBean
	private EmployeeService employeeService;

	@Test
	public void getEmployees() throws Exception {

		List<Employee> mockEmployees = new ArrayList<>();
		Employee employee1 = new Employee();
		employee1.setName("Babu");
		employee1.setLastName("Aryal");
		employee1.setId(1);
		mockEmployees.add(employee1);
		Employee employee2 = new Employee();
		employee2.setName("Sagun");
		employee2.setLastName("Aryal");
		employee2.setId(2);
		mockEmployees.add(employee2);
		Employee employee3 = new Employee();
		employee3.setName("Aaron");
		employee3.setLastName("Aryal");
		employee3.setId(3);
		mockEmployees.add(employee3);


		/**
		 * Mocking the method to retrieve employees to return the specific mockEmployees when invoked.
		 */
		Mockito.when(employeeService.getAllEmployee()).thenReturn(mockEmployees);


		/**
		 * Creating a Request builder to be able to execute
		 * a get request to uri “/employees” with accept header as “application/json”
		 */
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/employees").accept(MediaType.APPLICATION_JSON);

		/**
		 * mockMvc is used to perform the request and return the response back.
		 */
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		System.out.println("Mock Get Response: " + result.getResponse().getContentAsString());
		String expected = "[{\"id\":1,\"name\":\"Babu\",\"lastName\":\"Aryal\",\"active\":false},{\"id\":2,\"name\":\"Sagun\",\"lastName\":\"Aryal\",\"active\":false},{\"id\":3,\"name\":\"Aaron\",\"lastName\":\"Aryal\",\"active\":false}]";


		/**
		 * We are using org.skyscreamer.jsonassert.JSONAssert. This allows us to do partial asserts against a JSON String.
		 * We are passing strict as false since we do not want to check for all fields in the response.
		 */
		JSONAssert.assertEquals(expected, result.getResponse()
				.getContentAsString(), false);
	}

	@Test
	public void addEmployee() throws Exception {

		Employee mockEmployee = new Employee();
		mockEmployee.setName("Babu");
		mockEmployee.setLastName("Aryal");
		mockEmployee.setId(1);


		String employeeJson = "{\"id\":0,\"name\":\"Babu\",\"lastName\":\"Aryal\",\"active\":false}";
		/**
		 * Mocking the method to retrieve employees to return the specific mockEmployees when invoked.
		 */
		Mockito.when(employeeService.addEmployee(Mockito.any(Employee.class))).thenReturn("Successfully added!!");


		/**
		 * Creating a Request builder to be able to execute
		 * a get request to uri “/employees” with accept header as “application/json”
		 */
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/employee/add")
															  .accept(MediaType.APPLICATION_JSON).content(employeeJson).contentType(MediaType.APPLICATION_JSON);

		/**
		 * mockMvc is used to perform the request and return the response back.
		 */
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		System.out.println("Mock Post Response: " + result.getResponse().getContentAsString());


		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.OK.value(), response.getStatus());

		/*assertEquals("http://localhost:8080/employee/add",
				response.getHeader(HttpHeaders.LOCATION));*/
		/**
		 * We are using org.skyscreamer.jsonassert.JSONAssert. This allows us to do partial asserts against a JSON String.
		 * We are passing strict as false since we do not want to check for all fields in the response.
		 */
		assertEquals("Expected employee add ", "Successfully added!!",result.getResponse().getContentAsString());
	}




}
