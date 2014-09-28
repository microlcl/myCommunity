package com.eastteam.community.web;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * Controller里面如果需要读取配置信息，继承此Class
 */
public class PropertiesController {
	@Autowired
  	@Qualifier("configProperties")
  	private Properties configProperties;	
}
