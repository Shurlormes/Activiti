package com.voidforce.activiti.controller;

import com.voidforce.activiti.bean.Menu;
import com.voidforce.activiti.common.bean.HashMapResult;
import com.voidforce.activiti.service.menu.MenuService;
import com.voidforce.activiti.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/menu")
public class MenuController {

	@Autowired
	private MenuService menuService;

	@GetMapping("")
	public String findAll(Menu menu) {
		List<Menu> menuList = menuService.findAll(menu);
		return JsonUtil.toJson(HashMapResult.success(null, menuList));
	}

	@PostMapping("")
	public String insert(@ModelAttribute Menu menu) {
		menuService.insert(menu);
		return JsonUtil.toJson(HashMapResult.success());
	}

	@PutMapping("/{menuId}")
	public String update(@PathVariable Long menuId, @ModelAttribute Menu menu) {
		menuService.update(menu);
		return JsonUtil.toJson(HashMapResult.success());
	}

	@DeleteMapping("/{menuId}")
	public String delete(@PathVariable Long menuId) {
		menuService.delete(menuId);
		return JsonUtil.toJson(HashMapResult.success());
	}

}
