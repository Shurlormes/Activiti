package com.voidforce.activiti.mapper.role.provider;

import com.voidforce.activiti.bean.Role;
import com.voidforce.activiti.common.constant.CommonConstant;
import org.apache.commons.lang3.StringUtils;

public class RoleMapperProvider {

	public String findAll(Role role) {
		role.setDeleted(CommonConstant.NOT_DELETED);
		StringBuilder sql = new StringBuilder(" SELECT * FROM ROLE " +
			" WHERE DELETED = #{deleted} ");

		if(role.getRoleId() != null) {
			sql.append(" AND ROLE_ID = #{roleId} ");
		}

		if(StringUtils.isNotEmpty(role.getRole())) {
			role.setRole("%" + role.getRole() + "%");
			sql.append(" AND ROLE LIKE #{role} ");
		}

		if(StringUtils.isNotEmpty(role.getRoleName())) {
			role.setRoleName("%" + role.getRoleName() + "%");
			sql.append(" AND ROLE_NAME LIKE #{roleName} ");
		}

		return sql.toString();
	}

	public String update(Role role) {
		StringBuilder sql = new StringBuilder(" UPDATE ROLE SET ");

		if(StringUtils.isNotEmpty(role.getRole())) {
			sql.append(" ROLE = #{role}, ");
		}

		if(StringUtils.isNotEmpty(role.getRoleName())) {
			sql.append(" ROLE_NAME = #{roleName}, ");
		}

		sql.deleteCharAt(sql.lastIndexOf(","));

		sql.append(" WHERE ROLE_ID = #{roleId} ");

		return sql.toString();
	}
}
