package com.example;

import com.example.controller.PatientController;
import org.json.JSONException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * Steps to create Integration test:
 * 1. Annotate test class with @RunWith(SpringRunner.class) and @SpringBootTest(classes = SpringBootDemoApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
 * 2. Autowire the random port into the variable so that we can use it create the url.
 * 3. Create an instance of RestTemplate. TestRestTemplate restTemplate = new TestRestTemplate();
 * 4. Create an instance of header. HttpHeaders headers = new HttpHeaders();
 * 5. Create an instance of HttpEntity<String> entity = new HttpEntity<String>(null, headers);
 * 6. Fire a GET request to the specify uri and get the response as a String.
 * 7. Assert the response with the expected result.
 */



@RunWith(SpringRunner.class)
/**
 * The @SpringBootTest annotation tells Spring Boot to go and look for a main configuration
 * class (one with @SpringBootApplication for instance), and use that to start a Spring application context.
 * You can run this test in your IDE or on the command line (mvn test or gradle test) and it should pass.
 *
 * To convince yourself that the context is creating your controller you could add an assertion
 *
 * Note the use of webEnvironment=SpringBootTest.WebEnvironment.RANDOM_PORT to start the server with a random port
 * (useful to avoid conflicts in test environments), and the injection of the port with @LocalServerPort.
 *
 * A nice feature of the Spring Test support is that the application context is cached in between tests,
 * so if you have multiple methods in a test case, or multiple test cases with the same configuration,
 * they only incur the cost of starting the application once. You can control the cache using the @DirtiesContext annotation.
 */
@SpringBootTest(classes = SpringBootDemoApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PatientControllerIntegrationTest {

    /**
     * Autowire the random port into the variable so that we can use it create the url.
     */
    @LocalServerPort
    private int port;

    /**
     * RestTemplate communicates HTTP server using RESTful principals.
     * RestTemplate provides different methods to communicate that will accept URI template,
     * URI variables, response type and request object as arguments.
     * It uses HTTP methods such as GET, POST, HEAD, PUT, DELETE etc. It also handles HTTP connections
     */
    @Autowired
    private TestRestTemplate restTemplate;

    HttpHeaders headers = new HttpHeaders();


    @Autowired
    private PatientController patientController;

    @Test
    public void contextLoads() throws Exception {
        assertThat(patientController).isNotNull();
    }

    @Test
    public void getAllergyType() throws JSONException{

        /**
         * is a helper object which encapsulates header and body of an HTTP request or response.
         * It can be used as a handler method parameter.
         * We use entity so that we have the flexibility of adding in request headers in future.
         *
         * org.springframework.http.RequestEntity<T> extends HttpEntity and adds additional information of HTTP method and uri to the request.
         */
        HttpEntity<String> entity = new HttpEntity<String>(null, headers);

        /**
         * Fire a GET request to the specify uri and get the response as a String.
         *
         * org.springframework.http.ResponseEntity<T> also extends HttpEntity, where we can add additional HttpStatus (see also @ResponseStatus) to the response.
         */
        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/patient/allergyTypes"),
                HttpMethod.GET, entity, String.class);

        String expected = "[\n" +
                "    {\n" +
                "        \"allergyTypeCd\": \"MED\",\n" +
                "        \"descr\": \"Medication\",\n" +
                "        \"paramCd\": \"C_ALLRGYTYP_MED\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"allergyTypeCd\": \"OTHER\",\n" +
                "        \"descr\": \"Other\",\n" +
                "        \"paramCd\": \"C_ALLRGYTYP_OTHER\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"allergyTypeCd\": \"NKDA\",\n" +
                "        \"descr\": \"No Known Drug Allergy\",\n" +
                "        \"paramCd\": \"C_ALLRGYTYP_NKDA\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"allergyTypeCd\": \"SEAFOOD\",\n" +
                "        \"descr\": \"Seafood\",\n" +
                "        \"paramCd\": \"C_ALLRGYTYP_SEAFOOD\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"allergyTypeCd\": \"DYE\",\n" +
                "        \"descr\": \"Dye, Contrast\",\n" +
                "        \"paramCd\": \"C_ALLRGYTYP_DYE\"\n" +
                "    }\n" +
                "]";

        JSONAssert.assertEquals(expected, response.getBody(), false);


    }

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }

}
