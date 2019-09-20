package com.ruslan.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ruslan.model.Message;
import com.ruslan.model.User;

@Service
public interface UserService {

	public List<User> findAll();
	public void saveUser(User user);
	public User getUserById(int id);
	public void deleteUserById(int id);
	//public Message getUserByMessage(Message message);
	public User findUserByName(String name);
}
