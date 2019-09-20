package com.ruslan.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import com.ruslan.model.Message;
import com.ruslan.model.User;

@Component
public interface UserDAO {
	
	public List<User> findAll();
	public void saveUser(User user);
	public User getUserById(int id);
	public void deleteUserById(int id);
	public Message getUserByMessage(Message message);
	public User findUserByName(String name);

}
