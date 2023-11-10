package com.nuatli.rest.webservices.restfulwebservices.user;

import java.util.List;
import java.util.function.Predicate;
import java.time.LocalDate;
import java.util.ArrayList;

import org.springframework.stereotype.Component;


@Component
public class UserDaoService {
	// JPA/Hibernate > Database
	// UserDao Service > Static List
	
	
	private static List<User> users = new ArrayList<User>();
	
	private static int usersCount = 0;
	
	static {
		users.add(new User(++usersCount,"Adam",LocalDate.now().minusYears(30)));
		users.add(new User(++usersCount,"Eve",LocalDate.now().minusYears(25)));
		users.add(new User(++usersCount,"Jane",LocalDate.now().minusYears(20)));
	}
	
	
	public List<User> findAll(){
		return users;
	}
	
	
	public User findUserWithId(int id) {
		try {
			/*
			return users
				.stream()
				.filter(u -> u.getId() == id)
				.findFirst()
				.get();
			*/
			/*
			  Predicate < ? super User> predicate = user -> user.getId().equals(id);
			  return users.stream().filter(predicate).findFirst().get();
			 */
			return users
					.stream()
					.filter(u -> u.getId() == id)
					.findFirst().orElse(null);
		}catch (Exception e) {
			System.out.println(e);
			return null;
		}
		
	}
	
	public User save(User user) {
		user.setId(++usersCount);
		users.add(user);
		return user;
	}
	
	public void deleteUserWithId(int id) {
		try {
			Predicate < ? super User> predicate = user -> user.getId().equals(id);
			users.removeIf(predicate);
		}catch (Exception e) {
			System.out.println(e);
		}
		
	}
	
	
}
