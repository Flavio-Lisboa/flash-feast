package com.flavio.flashfeast.api.controller;

import com.flavio.flashfeast.api.mapper.MenuMapper;
import com.flavio.flashfeast.api.model.MenuModel;
import com.flavio.flashfeast.api.model.input.MenuInput;
import com.flavio.flashfeast.domain.entities.Menu;
import com.flavio.flashfeast.domain.service.MenuService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
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

    @PostMapping("/{companyId}")
    public ResponseEntity<MenuModel> createMenu(@PathVariable int companyId, @Valid @ModelAttribute MenuInput menuInput, @RequestPart("image") MultipartFile image) throws Exception {

        if(image.isEmpty()) throw new FileNotFoundException("add an image");

        Menu menu = menuMapper.toEntity(menuInput);
        Menu menuResponse = menuService.createMenu(companyId, menu, image);

        if (menuResponse == null) return ResponseEntity.notFound().build();

        MenuModel menuModel = menuMapper.toModel(menuResponse);
        return ResponseEntity.ok(menuModel);
    }

    @GetMapping
    public ResponseEntity<List<MenuModel>> findAll() {
        List<Menu> menus = menuService.findAll();
        if(menus.isEmpty()) return ResponseEntity.noContent().build();

        List<MenuModel> menusModel = menuMapper.toCollectionModel(menus);
        return ResponseEntity.ok(menusModel);
    }

    @GetMapping("/company/{idCompany}")
    public ResponseEntity<List<MenuModel>> findAllByCompanyId(@PathVariable int idCompany) {
        List<Menu> menuList = menuService.getMenusByCompanyId(idCompany);

        if(menuList == null) return ResponseEntity.notFound().build();

        List<MenuModel> menuModelList = menuMapper.toCollectionModel(menuList);
        return ResponseEntity.ok(menuModelList);
    }

    @GetMapping("/{idMenu}/company/{idCompany}")
    public ResponseEntity<MenuModel> findMenu(@PathVariable int idCompany, @PathVariable int idMenu) {
        Menu menu = menuService.findMenu(idCompany, idMenu);

        if(menu == null) return ResponseEntity.notFound().build();

        MenuModel menuModel = menuMapper.toModel(menu);
        return ResponseEntity.ok(menuModel);
    }
}
