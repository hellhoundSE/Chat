package com.ruslan.dao;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ruslan.model.Message;

@Component
public class MessageDAOImpl implements MessageDAO {
	
	@Autowired
	private EntityManager entityManager;

	@Override
	public void saveMessage(Message message) {
		Session session = entityManager.unwrap(Session.class);
		session.saveOrUpdate(message);
	}

	@Override
	public void deleteMessageByUd(int id) {
		Session session = entityManager.unwrap(Session.class);
		Query theQuery = session.createQuery("delete from message where id = :messageId");
		theQuery.setParameter("messageId", id);
		theQuery.executeUpdate();
	}

	@Override
	public Message getMessageById(int id) {
		Session session = entityManager.unwrap(Session.class);
		Message message = session.get(Message.class, id);
		return message;
	}

}
