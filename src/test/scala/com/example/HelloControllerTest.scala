package com.example

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.context.annotation.ComponentScan
import org.springframework.http.{HttpStatus, RequestEntity}
import org.scalatest.BeforeAndAfterAll
import org.scalatest.BeforeAndAfterEach
import org.springframework.test.context.{TestContextManager}
import java.net.URI
import scala.compiletime.uninitialized // For Scala 3 style uninitialized vars

/** Integration test for the HelloController.
  *
  * @SpringBootTest
  *   starts up the full application context for testing.
  *   - webEnvironment = WebEnvironment.RANDOM_PORT starts the server on a
  *     random port.
  *   - classes = Array(classOf[MinimalApplication]) specifies the main
  *     application class to load.
  */
@SpringBootTest(
  webEnvironment = WebEnvironment.RANDOM_PORT,
  classes = Array(classOf[MinimalApplication]),
  properties = Array(
    "spring.main.allow-bean-definition-overriding=true",
    "logging.level.org.springframework.test=DEBUG",
    "logging.level.org.springframework.web=DEBUG"
  )
)
@ComponentScan(basePackages = Array("com.example"))
class HelloControllerTest extends AnyFunSuite with Matchers with BeforeAndAfterAll with BeforeAndAfterEach {
  
  // Initialize Spring Test Context
  private val testContextManager = new TestContextManager(this.getClass)
  
  override def beforeAll(): Unit = {
    super.beforeAll()
    println("Initializing Spring test context...")
    testContextManager.prepareTestInstance(this)
    println("Spring test context initialization completed.")
  }
  @Autowired
  private var restTemplate: TestRestTemplate = uninitialized // Using Scala 3 uninitialized
  
  // Injects the random port the server started on.
  @LocalServerPort
  private var port: Int = 0 // Initialized by Spring
  
  // Helper method to verify Spring context is properly initialized
  private def verifySpringContext(): Unit = {
    if (restTemplate == null) {
      fail("Spring context not initialized correctly: TestRestTemplate was not injected. Check Spring Boot configuration and dependencies.")
    }
    if (port == 0) {
      fail("Spring context not initialized correctly: LocalServerPort was not injected. Check Spring Boot configuration and dependencies.")
    }
    println(s"Spring context verified: running on port $port with TestRestTemplate available.")
  }

  override def beforeEach(): Unit = {
    super.beforeEach()
    verifySpringContext()
  }

  test("The /hello endpoint should return 'helloworld'") {
    
    println(s"Executing test with server running on port: $port")
    val helloUrl = s"http://localhost:$port/hello"
    println(s"Testing endpoint at: $helloUrl")
    
    val request = RequestEntity.get(new URI(helloUrl)).build()
    val response = restTemplate.exchange(request, classOf[String])
    
    response.getStatusCode shouldBe HttpStatus.OK
    
    response.getBody shouldBe "helloworld"
  }
}
