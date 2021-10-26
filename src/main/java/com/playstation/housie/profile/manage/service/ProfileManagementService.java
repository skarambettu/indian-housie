package com.playstation.housie.profile.manage.service;

import com.playstation.housie.profile.Profile;
import com.playstation.housie.profile.dao.ProfileDAO;

public class ProfileManagementService {
	private static ProfileDAO profileRepository = new ProfileDAO();
	
	public long createProfile(String name) {
		Profile profile = new Profile();
		profile.setName(name);
		profileRepository.create(profile);
		return profile.getId();
	}
	
	public Profile loadProfile(long id) {
		return profileRepository.load(id);
	}

}
