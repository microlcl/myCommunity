package com.eastteam.community.weichat.message;

import org.apache.commons.digester3.Digester;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.eastteam.community.utils.DigesterUtils;

public class MessageFactory {
	
	private static Logger logger = LoggerFactory.getLogger(MessageFactory.class);
	
	public static BaseWeiChatMessage createWeiChatMessage(String message) {
		Digester digester = DigesterUtils.getDigester();
		try {
			Message4Digester msgBean =  (Message4Digester)digester.parse(message);
			if (msgBean.getMessageType().equalsIgnoreCase("text")) {
				return new TextWeiChatMessage(msgBean);
			} 
			else if (msgBean.getMessageType().equalsIgnoreCase("event")){
				logger.warn("even message is not implemented so far!");
				return null;
			}
			else {
				String error = "This message tryp is not implemented: " + msgBean.getMessageType();
				throw new RuntimeException(error);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("MessageFactory occurred error",e);
			return null;
		} 
	}

}
