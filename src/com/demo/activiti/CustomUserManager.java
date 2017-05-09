package com.demo.activiti;

import java.util.List;

import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;
import org.activiti.engine.impl.Page;
import org.activiti.engine.impl.UserQueryImpl;
import org.activiti.engine.impl.persistence.entity.IdentityInfoEntity;
import org.activiti.engine.impl.persistence.entity.UserEntityManager;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 自定义的Activiti用户管理器
 */
public class CustomUserManager extends UserEntityManager {
    private static final Log logger = LogFactory.getLog(CustomUserManager.class);

    @Override
    public User findUserById(String userId) {
        return super.findUserById(userId);
    }

    @Override
    public List<User> findUserByQueryCriteria(UserQueryImpl query, Page page) {
        return super.findUserByQueryCriteria(query, page);
    }

    @Override
    public long findUserCountByQueryCriteria(UserQueryImpl query) {
        return super.findUserCountByQueryCriteria(query);
    }

    @Override
    public List<Group> findGroupsByUser(String userId) {
        return super.findGroupsByUser(userId);
    }

    @Override
    public IdentityInfoEntity findUserInfoByUserIdAndKey(String userId, String key) {
        return super.findUserInfoByUserIdAndKey(userId, key);
    }

    @Override
    public List<String> findUserInfoKeysByUserIdAndType(String userId, String type) {
        return super.findUserInfoKeysByUserIdAndType(userId, type);
    }

    @Override
    public Boolean checkPassword(String userId, String password) {
        return super.checkPassword(userId, password);
    }

    @Override
    public List<User> findPotentialStarterUsers(String proceDefId) {
        return super.findPotentialStarterUsers(proceDefId);
    }

}