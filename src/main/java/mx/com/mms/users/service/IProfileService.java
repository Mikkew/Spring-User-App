package mx.com.mms.users.service;

import org.springframework.data.domain.Page;

import mx.com.mms.users.entities.Profile;

public interface IProfileService {
	public Page<Profile> findAll(Integer pageNo, Integer pageSize, String sort);

	public Profile findById(String id);

	public Profile save(Profile profile);

	public void delete(Profile profile);

}
