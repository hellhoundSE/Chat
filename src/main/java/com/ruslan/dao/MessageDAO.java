package com.ruslan.dao;

import org.springframework.stereotype.Component;

import com.ruslan.model.Message;

@Component
public interface MessageDAO {

	public void saveMessage(Message message);
	public void deleteMessageByUd(int id);
	public Message getMessageById(int id);
	
}
