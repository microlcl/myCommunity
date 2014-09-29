package com.eastteam.community.utils;

import java.io.File;

import org.apache.commons.digester3.xmlrules.FromXmlRulesModule;

public class DigesterRulesModule extends FromXmlRulesModule {

	@Override
	protected void loadRules() {
		loadXMLRules( new File( "/digester/rules/digester-rules.xml" ) );		
	}
	

}
