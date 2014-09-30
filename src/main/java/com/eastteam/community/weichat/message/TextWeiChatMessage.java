package com.eastteam.community.weichat.message;

/**
 * 微信文本消息
 * @author lichlei
 *
 */
public class TextWeiChatMessage extends BaseWeiChatMessage{
	
	private String content;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
