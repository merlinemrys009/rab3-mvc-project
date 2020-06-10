package rab3.mvc.dao;

import java.io.IOException;
import java.sql.Types;
import java.util.List;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.SqlLobValue;
import org.springframework.jdbc.support.lob.DefaultLobHandler;
import org.springframework.jdbc.support.lob.LobHandler;
import org.springframework.stereotype.Repository;
import rab3.mvc.dao.entity.ProfileEntity;

@Repository
public class ProfileImpl implements ProfileDao {
	@Autowired
//	 @Qualifier("ddatasource")
	private DataSource datasource;

	@Override
	public ProfileEntity searchProfile(String email) {
		System.out.println("email is " + email);
		JdbcTemplate jdbcTemplate = new JdbcTemplate(datasource);
		String sql = "select id,username,name, email,gender from webtabledemo where email =?";
		ProfileEntity s_profile1 = null;
		try {
			s_profile1 = jdbcTemplate.queryForObject(sql, new Object[] { email },
					new BeanPropertyRowMapper<>(ProfileEntity.class));
			System.out.println("s_profile= " + s_profile1);
		} catch (DataAccessException e) {
			e.printStackTrace();

		}

		return s_profile1;

	}

	@Override
	public String forgetPassword(String email) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(datasource);
		String sql = "select password  from webtabledemo where email =?";
		String password = null;
		try {
			password = jdbcTemplate.queryForObject(sql, new Object[] { email }, String.class);
		} catch (DataAccessException e) {
		}
		return password;
	}

	@Override
	public String saveProfile(ProfileEntity profileEntity) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(datasource);
		LobHandler lobHandler = new DefaultLobHandler();
		SqlLobValue sqlLobValue = null;
		try {
			sqlLobValue = new SqlLobValue(profileEntity.getPhoto().getBytes(), lobHandler);
		} catch (IOException e) {
			e.printStackTrace();
		}
		jdbcTemplate.update("insert into webtabledemo(username,password,name,email,gender,photo)  values(?,?,?,?,?,?)",
				new Object[] { profileEntity.getUsername(), profileEntity.getPassword(), profileEntity.getName(),
						profileEntity.getEmail(), profileEntity.getGender(), sqlLobValue },
				new int[] { Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.BLOB });
		return "success";
	}

	@Override
	public byte[] findPhotoById(int id) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(datasource);
		String sql = "select photo from webtabledemo where id =" + id;
		byte[] photo = jdbcTemplate.queryForObject(sql, byte[].class);
		return photo;
	}

	@Override
	public ProfileEntity findById(int id) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(datasource);
		String sql = "select id,username,name,email,gender,role from webtabledemo where id =" + id;
		ProfileEntity profileEntity = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(ProfileEntity.class));
		return profileEntity;
	}

	@Override
	public String update(ProfileEntity profileEntity) {
		LobHandler lobHandler=new DefaultLobHandler();
        SqlLobValue sqlLobValue=null;
		JdbcTemplate jdbcTemplate = new JdbcTemplate(datasource);
		try {
			if(profileEntity.getPhoto()==null || profileEntity.getPhoto().getBytes().length<3) {
				jdbcTemplate.update("update webtabledemo set username=? ,name =? ,email =? ,gender =?  where id =?",
						new Object[]{profileEntity.getUsername(),profileEntity.getName(),
								profileEntity.getEmail(),profileEntity.getGender(),profileEntity.getId()},
						new int[] {Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.INTEGER});
			}else {
				 sqlLobValue = new SqlLobValue(profileEntity.getPhoto().getBytes(),lobHandler);
				jdbcTemplate.update("update webtabledemo set username=? ,name =? ,email =? ,gender =? ,photo= ?  where id =?",
						new Object[]{profileEntity.getUsername(),profileEntity.getName(),
								profileEntity.getEmail(),profileEntity.getGender(),sqlLobValue,profileEntity.getId()},
						new int[] {Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.BLOB,Types.INTEGER});
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	return "success";
}

	@Override
	public List<ProfileEntity> findAll(int ppageid, int pageSize) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(datasource);
		List<ProfileEntity> profileEntity = jdbcTemplate.query(
				"select  id,username,password,name,email,gender,role  from webtabledemo limit"+(ppageid)+ ","+pageSize,
				new BeanPropertyRowMapper<>(ProfileEntity.class));
		return profileEntity;
	}

	@Override
	public int AllRegister() {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(datasource);
		String sql = "SELECT COUNT(*) FROM webtabledemo";
		return jdbcTemplate.queryForObject(sql, Integer.class);
	}

	@Override
	public String deleteByusername(String username) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(datasource);
		String sql = "DELETE from webtabledemo where username =?";
		jdbcTemplate.update(sql, new Object[] { username });
		return "success";
	}

	
}
