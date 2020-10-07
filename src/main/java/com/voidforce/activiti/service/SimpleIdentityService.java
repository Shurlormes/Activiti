package com.voidforce.activiti.service;

import com.voidforce.activiti.common.bean.HashMapResult;
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

	public HashMapResult updateUser(String id, String name) {
		User dbUser = identityService.createUserQuery().userId(id).singleResult();
		if (dbUser != null) {
			if(!name.equals(dbUser.getFirstName())) {
				dbUser.setFirstName(name);
				identityService.saveUser(dbUser);
			}
		} else {
			User user = identityService.newUser(id);
			user.setFirstName(name);
			identityService.saveUser(user);
		}

		return HashMapResult.success();
	}

	public HashMapResult deleteUser(String id) {
		identityService.deleteUser(id);
		return HashMapResult.success();
	}

	public HashMapResult updateGroup(String id, String name) {
		Group dbGroup = identityService.createGroupQuery().groupId(id).singleResult();
		if (dbGroup != null) {
			if(!name.equals(dbGroup.getName())) {
				dbGroup.setName(name);
				identityService.saveGroup(dbGroup);
			}
		} else {
			Group group = identityService.newGroup(id);
			group.setName(name);
			identityService.saveGroup(group);
		}

		return HashMapResult.success();
	}

	public HashMapResult deleteGroup(String id) {
		identityService.deleteGroup(id);
		return HashMapResult.success();
	}

	public HashMapResult updateMemberShip(String userId, String groupId) {
		User user = identityService.createUserQuery().userId(userId).singleResult();
		if(user == null) {
			logger.error("user {} not exists", userId);
			return HashMapResult.failure("user " + userId + " not exists");
		}

		Group group = identityService.createGroupQuery().groupId(groupId).singleResult();
		if(group == null) {
			logger.error("group {} not exists", groupId);
			return HashMapResult.failure("group " + groupId + " not exists");
		}

		identityService.createMembership(userId, groupId);

		return HashMapResult.success();
	}

	public HashMapResult deleteMemberShip(String userId, String groupId) {
		identityService.deleteMembership(userId, groupId);
		return HashMapResult.success();
	}
}
