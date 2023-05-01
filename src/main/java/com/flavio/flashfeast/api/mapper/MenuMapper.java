package com.flavio.flashfeast.api.mapper;

import com.flavio.flashfeast.api.model.MenuModel;
import com.flavio.flashfeast.api.model.input.MenuInput;
import com.flavio.flashfeast.domain.entities.Menu;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Component
public class MenuMapper {

    private final ModelMapper modelMapper;

    public Menu toEntity(MenuInput menuInput) {
        return modelMapper.map(menuInput, Menu.class);
    }

    public MenuModel toModel(Menu menu) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        return modelMapper.map(menu, MenuModel.class);
    }

    public List<MenuModel> toCollectionModel(List<Menu> menus) {
        return menus.stream().map(this::toModel).collect(Collectors.toList());
    }
}
