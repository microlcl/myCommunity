package com.eastteam.community.web;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.eastteam.community.weichat.message.BaseWeiChatMessage;
import com.eastteam.community.weichat.message.MessageFactory;
import com.eastteam.community.weichat.message.TextWeiChatMessage;

@Controller
@RequestMapping(value = "/weixin")
public class WeixinController {
	
	private static Logger logger = LoggerFactory.getLogger(WeixinController.class);
	
	
	@RequestMapping(method = RequestMethod.GET,value = "/api/connect")
	@ResponseBody
	public String connect(@RequestParam(value = "echostr", defaultValue = "not from weixin")String echo) {
		logger.info("echo={}", echo);
		return echo;
	}

	@RequestMapping(method = RequestMethod.POST,value = "/api/connect")
	@ResponseBody
	public String receiveMessage(HttpServletRequest request) {
		String message = null;
		try {
			message = this.getMessage(request);
			logger.info("receive message:{}", message);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error("error occered when receive message.", e);
		}
		BaseWeiChatMessage weichatMessage = MessageFactory.createWeiChatMessage(message);
		if (weichatMessage.getMessageType().equalsIgnoreCase("text")) {
			TextWeiChatMessage textMsg = (TextWeiChatMessage)weichatMessage;
			String from = textMsg.getToUserName();
			String to = textMsg.getFromUserName();
			String response = this.buildResponseMsg(from, to);
			logger.info("return message:{}", response);
			return response;
			
		}
		
		return "done";
	}	
	
	private String getMessage(HttpServletRequest request) throws IOException {
	    InputStream is = request.getInputStream();
	    int size = request.getContentLength();
	    byte[] buffer = new byte[size];
	    byte[] xmldataByte = new byte[size];
	    int count = 0;
	    int rbyte = 0;
	    while (count < size) {
	        rbyte = is.read(buffer);
	        for (int i = 0; i < rbyte; i++) {
	            xmldataByte[count + i] = buffer[i];
	        }
	        count += rbyte;
	    }
	    is.close();
	    String requestStr = new String(xmldataByte, "UTF-8");
	    return requestStr;
	}	
	
	private String buildResponseMsg (String from, String to) {
		StringBuffer msgbuffer = new StringBuffer();
		msgbuffer.append("<xml>")
				 .append("<ToUserName><![CDATA[").append(to).append( "]]></ToUserName>")
				 .append("<FromUserName><![CDATA[").append(from).append("]]></FromUserName>")
				 .append("<CreateTime>12345678</CreateTime>")
				 .append("<MsgType><![CDATA[text]]></MsgType>")
				 .append("<Content><![CDATA[你好, just a test]]></Content>")
				 .append("</xml>");
		
		return msgbuffer.toString();
		
	}

}
