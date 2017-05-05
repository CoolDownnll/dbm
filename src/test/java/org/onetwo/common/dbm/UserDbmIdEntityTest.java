package org.onetwo.common.dbm;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.Collection;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.onetwo.common.base.DbmBaseTest;
import org.onetwo.common.db.BaseEntityManager;
import org.onetwo.common.db.builder.Querys;
import org.onetwo.common.dbm.model.entity.UserTableDbmIdEntity;
import org.onetwo.common.utils.LangOps;

/**
 * @author wayshall
 * <br/>
 */
public class UserDbmIdEntityTest extends DbmBaseTest {

	@Resource
	private BaseEntityManager entityManager;
	

	@Test
	public void testSample(){
		UserTableDbmIdEntity user = new UserTableDbmIdEntity();
		user.setUserName("dbm");
		
		//save
		Long userId = entityManager.save(user).getId();
		assertThat(userId, notNullValue());
		
		//user querys dsl api
		UserTableDbmIdEntity queryUser = Querys.from(entityManager, UserTableDbmIdEntity.class)
											.where()
												.field("userName").is(user.getUserName())
											.end()
											.toQuery()
											.one();
		assertThat(queryUser, equalTo(user));
	}
	
	@Test
	public void testSaveList(){
		List<UserTableDbmIdEntity> users = LangOps.generateList(1000, i->{
			UserTableDbmIdEntity user = new UserTableDbmIdEntity();
			user.setUserName("dbm-"+i);
			return user;
		});
		
		//save
		Collection<UserTableDbmIdEntity> dbUsers = entityManager.saves(users);
		dbUsers.forEach(u->{
			assertThat(u.getId(), notNullValue());
		});
	}
}
