
package com.igomall.dao.impl;

import org.springframework.stereotype.Repository;

import com.igomall.dao.UserDao;
import com.igomall.entity.User;

/**
 * Dao - 用户
 * 
 * @author BOOTX Team
 * @version 6.1
 */
@Repository
public class UserDaoImpl extends BaseDaoImpl<User, Long> implements UserDao {

}