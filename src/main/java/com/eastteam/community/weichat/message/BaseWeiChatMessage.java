package com.eastteam.community.weichat.message;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * WeiChat消息基类，可扩展为文本，声音，视频，地理位置，链接消息等
 * @author lichlei
 *
 */
public class BaseWeiChatMessage {
	
	private String fromUserName;
	private String toUserName;
	private String createTime;
	private String messageType;
	private String messageId;
	
	public BaseWeiChatMessage() {
		super();
	}
	
	public BaseWeiChatMessage(String fromUserName, String toUserName, String createTime, String messageType, String messageId) {
		this.fromUserName = fromUserName;
		this.toUserName = toUserName;
		this.createTime = createTime;
		this.messageType = messageType;
		this.messageId = messageId;		
	}
	
	public BaseWeiChatMessage(Message4Digester message4Digester) {
		this.fromUserName = message4Digester.getFromUserName();
		this.toUserName = message4Digester.getToUserName();
		this.createTime = message4Digester.getCreateTime();
		this.messageType = message4Digester.getMessageType();
		this.messageId = message4Digester.getMessageId();		
	}	

	public String getFromUserName() {
		return fromUserName;
	}
	public void setFromUserName(String fromUserName) {
		this.fromUserName = fromUserName;
	}
	public String getToUserName() {
		return toUserName;
	}
	public void setToUserName(String toUserName) {
		this.toUserName = toUserName;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getMessageType() {
		return messageType;
	}
	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}

	public String getMessageId() {
		return messageId;
	}
	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
