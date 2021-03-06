package org.onetwo.common.dbm.model.dao;

import java.util.List;

import org.onetwo.common.db.dquery.annotation.DbmRepository;
import org.onetwo.common.db.dquery.annotation.QueryDispatcher;
import org.onetwo.common.dbm.model.entity.UserAutoidEntity;
import org.onetwo.common.utils.Page;


@DbmRepository
public interface UserAutoidDao2 {

	public List<UserAutoidEntity> findUserList(@QueryDispatcher String status);
	
	public void findUserPage(Page<UserAutoidEntity> page, String userName);

}
