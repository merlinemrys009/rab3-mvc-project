package rab3.mvc.service;


import java.util.List;


import rab3.mvc.controller.dto.ProfileDTO;
import rab3.mvc.dao.entity.ProfileEntity;

public interface ProfileService {

	String findPassword(String email);

	ProfileEntity findProfileById(int id);

//	ProfileEntity findProfile(String email);

	ProfileEntity findSingleProfile(String email);
	
	int AllRegister();

	String deleteByusername(int id);

	byte[] findPhotoById(int id);

	String updateProfile(ProfileDTO profileDTO);

	String persist(ProfileDTO profileDTO);

	ProfileDTO auth(String username, String password);

	List<ProfileDTO> findProfile(int ppageid, int pageSize);


	
	
	
}
