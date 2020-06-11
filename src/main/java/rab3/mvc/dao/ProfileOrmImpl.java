package rab3.mvc.dao;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.TypedQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import rab3.mvc.dao.entity.ProfileEntity;

@Repository
@Transactional
public class ProfileOrmImpl implements ProfileDao {
	@Autowired
	private SessionFactory sessionFactory;

	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public ProfileEntity auth(String username, String password) {
		TypedQuery<ProfileEntity> query = getSession().createQuery("from ProfileEntity dt where dt.username=:pusername and dt.password=:ppassword"); // HQL
		query.setParameter("pusername", username);
		query.setParameter("ppassword", password);
		ProfileEntity profileEntity = null;
		try {
			profileEntity = query.getSingleResult();
		} catch (Exception e) {
			// e.printStackTrace();
		}
		return profileEntity;
	}

	@Override
	public String forgetPassword(String email) {
		TypedQuery<ProfileEntity> query = getSession().createQuery("from ProfileEntity dt where dt.email=:pemail"); // HQL
		query.setParameter("pemail", email);
		ProfileEntity profileEntity = null;
		try {
			profileEntity = query.getSingleResult();
		} catch (Exception e) {
			// e.printStackTrace();
		}
		return profileEntity != null ? profileEntity.getPassword() : null;
	}

	@Override
	public String saveProfile(ProfileEntity profileEntity) {
		// Session session=this.getSession();
		this.getSession().save(profileEntity);
		return "success";
	}

	@Override
	public ProfileEntity findById(int id) {
		return this.getSession().get(ProfileEntity.class, id);
	}

	@Override
	public String update(ProfileEntity profileEntity) {
		this.getSession().merge(profileEntity);
		return "done";
	}

	@Override
	public List<ProfileEntity> findAll(int ppageid, int pageSize) {
		Query<ProfileEntity> query = this.getSession().createQuery("from ProfileEntity");
		if (ppageid > 0) {
			query.setFirstResult(0);

		}
		if (pageSize > 0) {
			query.setMaxResults(pageSize);

		}
		return query.list();
	}

	@Override
	public byte[] findPhotoById(int id) {
		return this.getSession().get(ProfileEntity.class, id).getHphoto();
	}

	@Override
	public ProfileEntity searchProfile(String email) {
//		return this.getSession().get(ProfileEntity.class, email);
		TypedQuery<ProfileEntity> query = getSession().createQuery("from ProfileEntity dt where dt.email=:pemail"); // HQL
		query.setParameter("pemail", email);
		ProfileEntity profileEntity = null;
		try {
			profileEntity = query.getSingleResult();
		} catch (Exception e) {
			// e.printStackTrace();
		}
		return profileEntity;

	}

	@Override
	public int AllRegister() {
		Query query = this.getSession().createQuery("select count(*) from ProfileEntity");
		long num = (long) query.getSingleResult();
		return (int) num;
	}

	@Override
	public String deleteByusername(int id) {
		ProfileEntity profileEntity = this.getSession().byId(ProfileEntity.class).load(id);
		this.getSession().delete(profileEntity);
		return "success";
	}

}
