package rab3.mvc.service;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import rab3.mvc.controller.dto.ProfileDTO;
import rab3.mvc.dao.ProfileDao;
import rab3.mvc.dao.entity.ProfileEntity;

@Service
public  class ProfileServiceImpl  implements  ProfileService{//it not open should i end this

	@Autowired
	@Qualifier("profileOrmImpl")
//	@Qualifier("profileImpl")
	private ProfileDao profileDao;
	
	
	@Override
	public  ProfileDTO auth(String username, String password) {
		ProfileEntity result=profileDao.auth(username,password);
		ProfileDTO profileDTO=new ProfileDTO();
		if(result!=null) {
		BeanUtils.copyProperties(result, profileDTO);
		}
		else {
			profileDTO=null;
		}
		return profileDTO;
	}
	
	@Override
	public byte[] findPhotoById(int id) {
		byte[] photo=profileDao.findPhotoById(id);
		return photo;
	}

	@Override
	public String findPassword(String email) {
		String result=profileDao.forgetPassword(email);
		return result;
	}

	@Override
	public String persist(ProfileDTO profileDTO) {
		ProfileEntity profileEntity=new ProfileEntity();
		BeanUtils.copyProperties(profileDTO, profileEntity);
		try {
			profileEntity.setHphoto(profileDTO.getPhoto().getBytes());
			profileEntity.setRole("customer");
			profileEntity.setDoe(new Timestamp(new Date().getTime()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		String result=profileDao.saveProfile(profileEntity);
		return result;
	}

	@Override
	public ProfileEntity findProfileById(int id) {
		ProfileEntity profileEntity=profileDao.findById(id);
		ProfileDTO profileDTO=new  ProfileDTO();
		BeanUtils.copyProperties(profileEntity, profileDTO);
		return profileEntity;
	}

	@Override
	public 	String updateProfile(ProfileDTO profileDTO ) {
		ProfileEntity profileEntity=new ProfileEntity();
		BeanUtils.copyProperties(profileDTO, profileEntity);
		String result=profileDao.update(profileEntity);
		return result;
	}

//	@Override
//	public List<ProfileDTO> findProfile(int ppageid, int pageSize){
//		 List<ProfileEntity> profileEntities=profileDao.findAll(ppageid, pageSize);
//		 List<ProfileDTO> profileDTOs=new ArrayList<>();
//		 for(ProfileEntity entity: profileEntities) {
//			 ProfileDTO profileDTO=new  ProfileDTO();
//				BeanUtils.copyProperties(entity, profileDTO);
//				profileDTOs.add(profileDTO);
//		 }
//		 return profileDTOs;
//	}


	@Override
	public ProfileEntity findSingleProfile(String email) {
		ProfileEntity result= profileDao.searchProfile(email);
		return result;
	}


	@Override
	public int AllRegister() {
		int result=profileDao.AllRegister();
		return result;
	}

//	@Override
//	public int count() {
//		int result=profileDao.count();
//		return result;
//	}

	@Override
	public String deleteByusername(int id) {
		String result=profileDao.deleteByusername(id);
		return result;
	}



	@Override
	public List<ProfileDTO> findProfile(int ppageid, int pageSize) {
		List<ProfileEntity>listOfEntity=profileDao.findAll(ppageid, pageSize);
		List<ProfileDTO> profileDTO= new ArrayList<>();
		for(ProfileEntity peEntity:listOfEntity) {
			ProfileDTO profileDTOs=new ProfileDTO();
			BeanUtils.copyProperties(peEntity, profileDTOs);
			profileDTO.add(profileDTOs);
		}
		return profileDTO;
	}


	

}
