package com.voidforce.activiti.controller;

import com.github.pagehelper.PageInfo;
import com.voidforce.activiti.bean.Role;
import com.voidforce.activiti.common.bean.HashMapResult;
import com.voidforce.activiti.common.constant.CommonConstant;
import com.voidforce.activiti.service.role.RoleService;
import com.voidforce.activiti.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/role")
public class RoleController {

	@Autowired
	private RoleService roleService;

	@GetMapping("")
	public String findAll(@RequestParam(required = false, defaultValue = CommonConstant.DEFAULT_PAGE_NUMBER) Integer page,
	                      @RequestParam(required = false, defaultValue = CommonConstant.DEFAULT_PAGE_LIMIT) Integer limit,
	                      @ModelAttribute Role role) {
		PageInfo<Role> rolePageInfo = roleService.findAllForPage(page, limit, role);
		return JsonUtil.toJson(HashMapResult.success(null, rolePageInfo));
	}


	@GetMapping("/{roleId}")
	public String findAll(@PathVariable Long roleId) {
		Role role = roleService.getById(roleId);
		return JsonUtil.toJson(HashMapResult.success(null, role));
	}

	@PostMapping("")
	public String insert(@ModelAttribute Role role) {
		roleService.insert(role);
		return JsonUtil.toJson(HashMapResult.success());
	}

	@PutMapping("/{roleId}")
	public String update(@PathVariable Long roleId,
	                     @ModelAttribute Role role) {
		roleService.update(role);
		return JsonUtil.toJson(HashMapResult.success());
	}

	@DeleteMapping("/{roleId}")
	public String delete(@PathVariable Long roleId) {
		roleService.delete(roleId);
		return JsonUtil.toJson(HashMapResult.success());
	}
}
