package com.eastteam.community.utils;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.digester3.Digester;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

import com.eastteam.community.weichat.message.TextWeiChatMessage;


public class DigesterTest {	
	private static Logger logger = LoggerFactory.getLogger(DigesterTest.class);
	
	@Test
	public void testParser() throws IOException, SAXException {
		InputStream in = FileUtils.getPackageResourceStream("/com/eastteam/community/utils/message.xml");
		Digester digester = DigesterUtils.getDigester();
		TextWeiChatMessage message =  (TextWeiChatMessage)digester.parse(in);
		logger.info(message.toString());
		assertNotNull(message);
		assertNotNull(message.getFromUserName());
		assertNotNull(message.getToUserName());
		assertNotNull(message.getMessageType());
		assertNotNull(message.getContent());
		assertNotNull(message.getCreateTime());
		assertNotNull(message.getMessageId());
		
	}

}
