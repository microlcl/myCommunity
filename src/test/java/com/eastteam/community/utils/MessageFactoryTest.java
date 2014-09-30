package com.eastteam.community.utils;

import java.io.IOException;

import org.junit.Test;

import com.eastteam.community.weichat.message.BaseWeiChatMessage;
import com.eastteam.community.weichat.message.MessageFactory;

public class MessageFactoryTest {
	
	@Test
	public void testCreateWeichatMessage() throws IOException {
		StringBuffer buffer = FileUtils.readPackageResource("/com/eastteam/community/utils/message.xml");
		BaseWeiChatMessage weichatMessage = MessageFactory.createWeiChatMessage(buffer.toString());
		System.out.println(weichatMessage);
	}

}
