package com.config.client.ConfigClient.Controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RefreshScope
@RestController
public class ConfigClientController {

	@Value("${msg:Hello world - Config Server is not working..pelase check}")
	private String message;
	
	@RequestMapping("/msg")
	public String getMessage(){
		return this.message;
	}
}
