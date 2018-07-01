package com.voidforce.activiti.service.userRole;

import com.voidforce.activiti.bean.UserRole;

public interface UserRoleService {

    Long insert(UserRole userRole);

    UserRole getByUserInfoIdAndRoleId(Long userInfoId, Long roleId);

    void deleteBy(UserRole userRole);

}
