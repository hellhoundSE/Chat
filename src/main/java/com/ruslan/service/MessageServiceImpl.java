package com.ruslan.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ruslan.dao.MessageDAO;
import com.ruslan.model.Message;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Service
public class MessageServiceImpl implements MessageService {

	@Autowired
	MessageDAO messageDAO;
	
	@Override
	@Transactional
	public void saveMessage(Message message) {
		messageDAO.saveMessage(message);

	}

	@Override
	@Transactional
	public void deleteMessageByUd(int id) {
		messageDAO.deleteMessageByUd(id);

	}

	@Override
	@Transactional
	public Message getMessageById(int id) {
		return messageDAO.getMessageById(id);
	}

}
