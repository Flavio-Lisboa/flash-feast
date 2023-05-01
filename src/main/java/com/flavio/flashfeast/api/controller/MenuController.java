package com.flavio.flashfeast.api.controller;

import com.flavio.flashfeast.api.mapper.MenuMapper;
import com.flavio.flashfeast.api.model.MenuModel;
import com.flavio.flashfeast.domain.entities.Menu;
import com.flavio.flashfeast.domain.service.MenuService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/menus")
public class MenuController {

    private final MenuService menuService;
    private final MenuMapper menuMapper;

    public MenuController(MenuService menuService, MenuMapper menuMapper) {
        this.menuService = menuService;
        this.menuMapper = menuMapper;
    }

    @GetMapping
    public ResponseEntity<List<MenuModel>> findAll() {
        List<Menu> menus = menuService.findAll();
        if(menus.isEmpty()) return ResponseEntity.noContent().build();

        List<MenuModel> menusModel = menuMapper.toCollectionModel(menus);
        return ResponseEntity.ok(menusModel);
    }
}
