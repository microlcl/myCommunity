package com.eastteam.community.utils;

import org.apache.commons.digester3.xmlrules.FromXmlRulesModule;

public class DigesterRulesModule extends FromXmlRulesModule {

	@Override
	protected void loadRules() {
		loadXMLRules(FileUtils.getPackageResourceStream("/digester/rules/messageRules.xml"));
	}	

}
