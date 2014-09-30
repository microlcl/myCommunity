package com.eastteam.community.utils;

import static org.apache.commons.digester3.binder.DigesterLoader.*;

import org.apache.commons.digester3.Digester;
import org.apache.commons.digester3.binder.DigesterLoader;

public class DigesterUtils {
	
	private static Digester digester = null;
	
	public static Digester  getDigester() {
		if (digester != null) {
			return digester;
		}
		DigesterLoader loader = newLoader( new DigesterRulesModule() );
		digester= loader.newDigester();
		return digester;

	}

}
