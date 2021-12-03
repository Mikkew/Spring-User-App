package mx.com.mms.users.service;

import java.util.List;

import mx.com.mms.users.entities.Address;

public interface IAddressService {
	public List<Address> findAll(Integer pageNo, Integer pageSize, String sortBy);
	
	public Address findById(String id);
	
	public Address save(Address address);
		
	public void delete(Address address);
	
	public Address update(Address address, String id);
}
