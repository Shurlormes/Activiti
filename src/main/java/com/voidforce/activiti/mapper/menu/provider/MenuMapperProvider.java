package com.voidforce.activiti.mapper.menu.provider;

import com.voidforce.activiti.bean.Menu;
import com.voidforce.activiti.common.constant.CommonConstant;
import org.apache.commons.lang3.StringUtils;

public class MenuMapperProvider {

	public String findAll(Menu menu) {
		menu.setDeleted(CommonConstant.NOT_DELETED);
		StringBuilder sql = new StringBuilder(" SELECT A.*, " +
			" !(SELECT COUNT(*) FROM MENU B WHERE B.DELETED = #{deleted} AND B.PARENT_ID = A.MENU_ID) AS LEAF " +
			" FROM MENU A " +
			" WHERE A.DELETED = #{deleted} ");

		if(menu.getMenuId() != null) {
			sql.append(" AND A.MENU_ID = #{menuId} ");
		}

		if(menu.getParentId() != null) {
			sql.append(" AND A.PARENT_ID = #{parentId} ");
		}

		sql.append(" ORDER BY A.SORT ");

		return sql.toString();
	}

	public String update(Menu menu) {
		StringBuilder sql = new StringBuilder(" UPDATE MENU SET ");

		if(StringUtils.isNotEmpty(menu.getMenuName())) {
			sql.append(" MENU_NAME = #{menuName}, ");
		}

		if(StringUtils.isNotEmpty(menu.getPath())) {
			sql.append(" PATH = #{path}, ");
		}

		if(StringUtils.isNotEmpty(menu.getComponent())) {
			sql.append(" COMPONENT = #{component}, ");
		}

		if(StringUtils.isNotEmpty(menu.getIcon())) {
			sql.append(" ICON = #{icon}, ");
		}

		if(menu.getSort() != null) {
			sql.append(" SORT = #{sort}, ");
		}

		sql.deleteCharAt(sql.lastIndexOf(","));

		sql.append(" WHERE MENU_ID = #{menuId} ");

		return sql.toString();
	}
}
