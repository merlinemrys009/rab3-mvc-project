package rab3.mvc.dao;

import java.util.List;

import rab3.mvc.dao.entity.ProfileEntity;

public interface ProfileDao {
	String forgetPassword(String email);
	String saveProfile(ProfileEntity profileEntity);
	ProfileEntity findById(int id);
	String update(ProfileEntity profileEntity);
	List<ProfileEntity> findAll(int ppageid, int pageSize);
	ProfileEntity searchProfile(String email);
	int AllRegister();
	String deleteByusername(int id);
	byte[] findPhotoById(int id);
	default ProfileEntity auth(String username, String password) {
		return null;
	}
	

	
	
}
