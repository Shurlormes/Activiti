package com.voidforce.activiti.service;

import com.voidforce.activiti.common.bean.HashMapResult;
import com.voidforce.activiti.common.util.JsonUtil;
import org.activiti.engine.IdentityService;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SimpleIdentityService {
	private static final Logger logger = LoggerFactory.getLogger(SimpleIdentityService.class);

	@Autowired
	private IdentityService identityService;

	public String updateUser(String id, String name) {
		User dbUser = identityService.createUserQuery().userId(id).singleResult();
		if (dbUser != null) {
			logger.warn("user {}-{} already exists, will do update", id, name);
			if(!name.equals(dbUser.getFirstName())) {
				dbUser.setFirstName(name);
				identityService.saveUser(dbUser);
			}
		} else {
			User user = identityService.newUser(id);
			user.setFirstName(name);
			identityService.saveUser(user);
		}

		return JsonUtil.toJson(HashMapResult.success());
	}

	public String deleteUser(String id) {
		identityService.deleteUser(id);
		return JsonUtil.toJson(HashMapResult.success());
	}

	public String updateGroup(String id, String name) {
		Group dbGroup = identityService.createGroupQuery().groupId(id).singleResult();
		if (dbGroup != null) {
			logger.warn("group {}-{} already exists, will do update", id, name);
			if(!name.equals(dbGroup.getName())) {
				dbGroup.setName(name);
				identityService.saveGroup(dbGroup);
			}
		} else {
			Group group = identityService.newGroup(id);
			group.setName(name);
			identityService.saveGroup(group);
		}

		return JsonUtil.toJson(HashMapResult.success());
	}

	public String deleteGroup(String id) {
		identityService.deleteGroup(id);
		return JsonUtil.toJson(HashMapResult.success());
	}

	public String updateMemberShip(String userId, String groupId) {
		User user = identityService.createUserQuery().userId(userId).singleResult();
		if(user == null) {
			logger.warn("user {} not exists", userId);
			return JsonUtil.toJson(HashMapResult.failure("user " + userId + " not exists"));
		}

		Group group = identityService.createGroupQuery().groupId(groupId).singleResult();
		if(group == null) {
			logger.warn("group {} not exists", groupId);
			return JsonUtil.toJson(HashMapResult.failure("group " + groupId + " not exists"));
		}

		this.deleteMemberShip(userId, groupId);
		identityService.createMembership(userId, groupId);

		return JsonUtil.toJson(HashMapResult.success());
	}

	public String deleteMemberShip(String userId, String groupId) {
		identityService.deleteMembership(userId, groupId);
		return JsonUtil.toJson(HashMapResult.success());
	}
}
