package mx.com.mms.users.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mx.com.mms.users.entities.Profile;

@Repository
public interface IProfileDao extends JpaRepository<Profile, String> {

}
