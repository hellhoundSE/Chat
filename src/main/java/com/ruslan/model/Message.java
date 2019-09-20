package com.ruslan.model;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name="message")
public class Message {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="message_id")
	private int id;
	
	@Column(name="content")
	private String content;
	
	@Column(name="time")
	private Timestamp time;
	
    @ManyToOne
    @JoinColumn(name="user_id")
	private User user;
	
	public Message(String content) {
		this.content = content;
		time = new Timestamp(new Date().getTime()); 
	}
	
}
