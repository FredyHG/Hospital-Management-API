package dev.fredyhg.com.hospitalquerysystem.controller.impl;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
public class IndexController {

    @GetMapping(path = {"/"})
	public void redirectToDocumentation(HttpServletResponse httpServletResponse) {
	    httpServletResponse.setHeader("Location", "/swagger-ui/index.html#/");
	    httpServletResponse.setStatus(302);
	}

}
