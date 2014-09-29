package com.eastteam.community.utils;

import static org.apache.commons.digester3.binder.DigesterLoader.*;

import org.apache.commons.digester3.Digester;
import org.apache.commons.digester3.binder.DigesterLoader;

public class DigesterUtils {
	
	public static Digester  getDigester() {
		DigesterLoader loader = newLoader( new DigesterRulesModule() );
		Digester digester= loader.newDigester();
		return digester;

	}

}
