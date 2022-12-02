package dev.dracarys.com.hospitalquerysystem.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
public class IndexController {

    @GetMapping(path = {"/"})
	public void redirecionaFrontend(HttpServletResponse httpServletResponse) {
	    httpServletResponse.setHeader("Location", "/swagger-ui/index.html#/");
	    httpServletResponse.setStatus(302);
	}

}
