package com.csye6225.webapp;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import static io.restassured.RestAssured.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class WebappApplicationTests {

	@LocalServerPort
	private int port;

	@Test
	public void testGetHealthzSuccess() {
		given()
				.baseUri("http://localhost")
				.port(port)
				.get("/healthz")
				.then()
				.statusCode(200)
				.header("Cache-Control", "no-cache");
	}

	@Test
	public void testDeleteHealthzMethodNotAllowed() {
		given()
				.baseUri("http://localhost")
				.port(port)
				.delete("/healthz")
				.then()
				.statusCode(405)
				.header("Cache-Control", "no-cache");
	}

	@Test
	public void testPutHealthzMethodNotAllowed() {
		given()
				.baseUri("http://localhost")
				.port(port)
				.put("/healthz")
				.then()
				.statusCode(405)
				.header("Cache-Control", "no-cache");
	}

	@Test
	public void testPostHealthzMethodNotAllowed() {
		given()
				.baseUri("http://localhost")
				.port(port)
				.post("/healthz")
				.then()
				.statusCode(405)
				.header("Cache-Control", "no-cache");
	}

	@Test
	public void testPatchHealthzMethodNotAllowed() {
		given()
				.baseUri("http://localhost")
				.port(port)
				.patch("/healthz")
				.then()
				.statusCode(405)
				.header("Cache-Control", "no-cache");
	}

	@Test
	public void testPathVariableHealthzMethodNotAllowed() {
		given()
				.baseUri("http://localhost")
				.port(port)
				.get("/healthz/ttt")
				.then()
				.statusCode(400)
				.header("Cache-Control", "no-cache");
	}

	@Test
	public void testParameterHealthzMethodNotAllowed() {
		given()
				.baseUri("http://localhost")
				.port(port)
				.queryParam("test", "test")
				.get("/healthz")
				.then()
				.statusCode(400)
				.header("Cache-Control", "no-cache");
	}

	@Test
	public void testPayloadHealthzMethodNotAllowed() {
		given()
				.baseUri("http://localhost")
				.port(port)
				.contentType("application/json")
				.body("test")
				.get("/healthz")
				.then()
				.statusCode(400)
				.header("Cache-Control", "no-cache");
	}
}
