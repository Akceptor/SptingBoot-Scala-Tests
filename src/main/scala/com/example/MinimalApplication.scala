package com.example

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

/** Main application class for the Spring Boot application.
  * @SpringBootApplication
  *   enables auto-configuration, component scanning, and more.
  */
@SpringBootApplication
class MinimalApplication

object MinimalApplication {
  def main(args: Array[String]): Unit = {
    SpringApplication.run(classOf[MinimalApplication], args: _*)
  }
}
