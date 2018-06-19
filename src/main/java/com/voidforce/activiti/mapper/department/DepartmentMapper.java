package com.voidforce.activiti.mapper.department;

import com.voidforce.activiti.bean.Department;
import org.apache.ibatis.annotations.*;

public interface DepartmentMapper {

    @Insert("INSERT INTO DEPARTMENT (PARENT_ID, NAME) VALUES (#{parentId}, #{name})")
    @Options(useGeneratedKeys = true, keyProperty = "departmentId")
    void insert(Department department);

    @Select("SELECT * FROM DEPARTMENT WHERE DEPARTMENT_ID = #{departmentId}")
    @Results(
            id = "simpleMapper",
            value = {
                    @Result(property = "departmentId", column = "DEPARTMENT_ID"),
                    @Result(property = "parentId", column = "PARENT_ID"),
                    @Result(property = "name", column = "NAME")
            }
    )
    Department getById(Long departmentId);

    @Select("SELECT * FROM DEPARTMENT WHERE PARENT_ID = #{departmentId}")
    @ResultMap("simpleMapper")
    Department getParentByChildId(Long departmentId);

}
