package com.example

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

/** A simple REST controller that handles HTTP requests.
  */
@RestController
class HelloController {

  /** Handles GET requests to the "/hello" endpoint.
    * @return
    *   A simple string "helloworld".
    */
  @GetMapping(Array("/hello"))
  def sayHello(): String = {
    "helloworld"
  }
}
