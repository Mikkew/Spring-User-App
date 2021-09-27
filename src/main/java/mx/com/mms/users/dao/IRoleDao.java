package mx.com.mms.users.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mx.com.mms.users.entities.Role;

@Repository
public interface IRoleDao extends JpaRepository<Role, String> {

}
