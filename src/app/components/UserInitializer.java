package app.components;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.entities.User;
import app.repositories.UserRepository;

@Component
public class UserInitializer {
	@Autowired
	private UserRepository repo;
	
	@PostConstruct 
	public void init() {
		if (repo.count()==0) {
			User user = makeUser("Gab", "09953034712");
			repo.save(user);

			user = makeUser("Acia", "09953034712");
			repo.save(user);

			user = makeUser("Evan", "09953034712");
			repo.save(user);

			user = makeUser("Seppi", "09953034712");
			repo.save(user);

			user = makeUser("EJ", "09953034712");
			repo.save(user);
		}
	}
	
	public Quote makeUser(String name, String cellphoneNumber) {
		User u = new User();
		u.setName(name);
		u.setCellphoneNumber(cellphoneNumber)
		return u;
	}
}
