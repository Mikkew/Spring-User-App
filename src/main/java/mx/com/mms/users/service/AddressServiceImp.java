package mx.com.mms.users.service;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.com.mms.users.dao.IAddressDao;
import mx.com.mms.users.entities.*;

@Service
public class AddressServiceImp implements IAddressService {
	
	@Autowired
	private IAddressDao addressDao;
	
	@Autowired
	private IProfileService profileService;

	@Override
	@Transactional(readOnly = true)
	public List<Address> findAll(Integer pageNo, Integer pageSize, String sortBy) {
		Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
		Page<Address> pagedResult = addressDao.findAll(paging);
		return (pagedResult.hasContent()) ? (pagedResult.getContent()) : (new ArrayList<Address>());
	}

	@Override
	@Transactional(readOnly = true)
	public Address findById(String id) {
		return addressDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public Address save(Address address) {
		Profile profile = profileService.findById(address.getProfileId());
		address.setProfile( profile );
		return addressDao.save(address);
	}

	@Override
	@Transactional
	public void delete(Address address) {
		addressDao.delete(address);
	}

	@Override
	@Transactional
	public Address update(Address address, String id) {
		Address addressUpdate = addressDao.findById(id).orElse(null);
		if(addressUpdate != null) {
			Profile profile = profileService.findById(address.getProfileId());
			addressUpdate.setStreet( address.getStreet() );
			addressUpdate.setNumber( address.getNumber() );
			addressUpdate.setProfile(profile);
		}
		return addressUpdate;
	}
}
