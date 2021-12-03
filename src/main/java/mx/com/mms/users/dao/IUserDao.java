package mx.com.mms.users.dao;

import java.util.Optional;

import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import mx.com.mms.users.entities.User;

@Repository
public interface IUserDao extends JpaRepository<User, String> {
	
	public Optional<User> findByUsername(String username);
	
	public Optional<User> findByUsernameAndPassword(String username, String password);
	
	@Query("SELECT u.username FROM User u")
	public Page<String> findUsernames(Pageable paging);
}
