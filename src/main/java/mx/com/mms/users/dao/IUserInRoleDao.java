package mx.com.mms.users.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mx.com.mms.users.entities.*;

@Repository
public interface IUserInRoleDao extends JpaRepository<UserInRole, String> {

	public List<UserInRole> findByUser(User user);
}
