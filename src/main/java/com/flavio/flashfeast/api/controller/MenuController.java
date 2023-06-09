package com.flavio.flashfeast.api.controller;

import com.flavio.flashfeast.api.mapper.MenuMapper;
import com.flavio.flashfeast.api.model.CompanyMenuModel;
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

    @CrossOrigin
    @GetMapping
    public ResponseEntity<List<CompanyMenuModel>> findAll() {
        List<CompanyMenuModel> menus = menuService.findAll();
        return ResponseEntity.ok(menus);
    }

    @GetMapping("/companies/{idCompany}")
    public ResponseEntity<List<MenuModel>> findMenusByCompanyId(@PathVariable int idCompany) {
        List<Menu> menuList = menuService.getMenusByCompanyId(idCompany);

        List<MenuModel> menuModelList = menuMapper.toCollectionModel(menuList);
        return ResponseEntity.ok(menuModelList);
    }

    @GetMapping("/{idMenu}/companies/{idCompany}")
    public ResponseEntity<MenuModel> findMenu(@PathVariable int idCompany, @PathVariable int idMenu) {
        Menu menu = menuService.findMenu(idCompany, idMenu);

        MenuModel menuModel = menuMapper.toModel(menu);
        return ResponseEntity.ok(menuModel);
    }

    @PostMapping("/companies/{companyId}")
    public ResponseEntity<MenuModel> createMenu(@PathVariable int companyId, @Valid @ModelAttribute MenuInput menuInput, @RequestPart("image") MultipartFile image) throws Exception {

        if(image.isEmpty()) throw new FileNotFoundException("add an image");

        Menu menu = menuMapper.toEntity(menuInput);
        Menu menuResponse = menuService.createMenu(companyId, menu, image);

        MenuModel menuModel = menuMapper.toModel(menuResponse);
        return ResponseEntity.ok(menuModel);
    }

    @PutMapping("/{idMenu}/companies/{idCompany}")
    public ResponseEntity<MenuModel> updateMenu(@PathVariable int idMenu, @PathVariable int idCompany, @Valid @RequestBody MenuInput menuInput) {
        Menu menu = menuMapper.toEntity(menuInput);
        Menu menuResponse = menuService.updateMenu(idMenu, idCompany, menu);

        MenuModel menuModel = menuMapper.toModel(menuResponse);
        return ResponseEntity.ok(menuModel);
    }

    @DeleteMapping("/{idMenu}/companies/{idCompany}")
    public ResponseEntity<?> deleteMenuByCompanyId(@PathVariable int idMenu, @PathVariable int idCompany) {
        menuService.deleteMenuByCompanyId(idMenu, idCompany);
        return ResponseEntity.ok().build();
    }
}
