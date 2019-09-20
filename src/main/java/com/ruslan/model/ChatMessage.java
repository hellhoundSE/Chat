package com.ruslan.model;

import lombok.Data;

@Data
public class ChatMessage {
	
	private String content;
	private String user;
	private MessageType type;

	public enum MessageType {
		CHAT, LEAVE, JOIN
	}


}
