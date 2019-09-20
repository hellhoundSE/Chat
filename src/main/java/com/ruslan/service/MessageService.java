package com.ruslan.service;

import org.springframework.stereotype.Service;

import com.ruslan.model.Message;


@Service
public interface MessageService {

	public void saveMessage(Message message);
	public void deleteMessageByUd(int id);
	public Message getMessageById(int id);
}
