package com.voidforce.activiti.mapper.menu;

import com.voidforce.activiti.bean.Menu;
import com.voidforce.activiti.mapper.menu.provider.MenuMapperProvider;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface MenuMapper {

    @Insert(" INSERT INTO MENU (MENU_NAME, PATH, COMPONENT, ICON, PARENT_ID, SORT, DELETED) " +
            " VALUES (#{menuName}, #{path}, #{component}, #{icon}, #{parentId}, #{sort}, #{deleted}) ")
    @Options(keyProperty = "menuId", useGeneratedKeys = true)
    void insert(Menu menu);

    @Select(" SELECT * FROM MENU WHERE DELETED = #{deleted} AND PATH = #{path} ")
    @Results(
            id = "simpleMapper",
            value = {
                    @Result(column = "MENU_ID", property = "menuId"),
                    @Result(column = "MENU_NAME", property = "menuName"),
                    @Result(column = "PATH", property = "path"),
                    @Result(column = "COMPONENT", property = "component"),
                    @Result(column = "ICON", property = "icon"),
                    @Result(column = "PARENT_ID", property = "parentId"),
                    @Result(column = "SORT", property = "sort"),
                    @Result(column = "DELETED", property = "deleted"),
                    @Result(column = "LEAF", property = "leaf"),
            }
    )
    Menu getByPath(@Param("path") String path, @Param("deleted") Integer deleted);


    @Select(" SELECT T1.* FROM MENU T1 " +
            " INNER JOIN ROLE_MENU T2 ON T2.DELETED = #{deleted} AND FIND_IN_SET(T2.ROLE_ID, #{roleIds}) AND T2.MENU_ID = T1.MENU_ID " +
            " WHERE T1.DELETED = #{deleted} " +
            " GROUP BY T1.MENU_ID " +
            " ORDER BY T1.SORT ")
    @ResultMap("simpleMapper")
    List<Menu> findByRoleIds(@Param("roleIds") String roleIds, @Param("deleted") Integer deleted);

    @SelectProvider(method = "findAll", type = MenuMapperProvider.class)
    @ResultMap("simpleMapper")
    List<Menu> findAll(Menu menu);

    @Update(" UPDATE MENU SET DELETED = 1 WHERE MENU_ID = #{menuId} ")
	void delete(Long menuId);

    @UpdateProvider(method = "update", type = MenuMapperProvider.class)
    void update(Menu menu);
}
