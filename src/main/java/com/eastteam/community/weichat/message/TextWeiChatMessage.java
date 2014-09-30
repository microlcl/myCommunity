package com.eastteam.community.weichat.message;

/**
 * 微信文本消息
 * @author lichlei
 *
 */
public class TextWeiChatMessage extends BaseWeiChatMessage{
	
	private String content;
	
	public TextWeiChatMessage(Message4Digester message4Digester) {
		super(message4Digester);
		this.content = message4Digester.getContent();
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
