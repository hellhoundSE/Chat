package com.ruslan.dao;

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ruslan.model.Message;
import com.ruslan.model.User;

@Component
public class UserDAOImpl implements UserDAO{

	@Autowired
	EntityManager entityManager;
	
	@Override
	public void saveUser(User user) {
		Session session = entityManager.unwrap(Session.class);
		session.saveOrUpdate(user);
	}

	@Override
	public User getUserById(int id) {
		Session session = entityManager.unwrap(Session.class);
		User user = session.get(User.class, id);
		return user;
	}

	@Override
	public void deleteUserById(int id) {
		Session session = entityManager.unwrap(Session.class);
		Query theQuery = session.createQuery("delete from user where id = :userId");
		theQuery.setParameter("userId", id);
		theQuery.executeUpdate();
		
	}


	@Override
	public User findUserByName(String name) {
		Session session = entityManager.unwrap(Session.class);
		Query<User> query = session.createQuery("from User where name=:username", User.class);
		query.setParameter("username", name);
		List<User>list = query.list();
		if(list.size()==0)
			return null;
		return list.get(0);
	}

	@Override
	public List<User> findAll() {
		Session session = entityManager.unwrap(Session.class);
		Query<User> query = session.createQuery("from User", User.class);
		List<User> list = query.getResultList();
		return list;
	}

}
