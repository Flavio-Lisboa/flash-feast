package com.flavio.flashfeast.domain.service;

import com.flavio.flashfeast.domain.entities.Menu;
import com.flavio.flashfeast.domain.repository.MenuRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class MenuService {

    private final MenuRepository menuRepository;

    public MenuService(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    public List<Menu> findAll() {
        return menuRepository.findAll();
    }
}
