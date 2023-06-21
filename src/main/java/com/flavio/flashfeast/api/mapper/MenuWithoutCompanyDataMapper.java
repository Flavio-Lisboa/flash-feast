package com.flavio.flashfeast.api.mapper;

import com.flavio.flashfeast.api.model.MenuWithoutCompanyDataModel;
import com.flavio.flashfeast.domain.entities.Menu;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Component
public class MenuWithoutCompanyDataMapper {

    private final ModelMapper modelMapper;

    public MenuWithoutCompanyDataModel toModel(Menu menu) {
        return modelMapper.map(menu, MenuWithoutCompanyDataModel.class);
    }

    public List<MenuWithoutCompanyDataModel> toCollectionModel(List<Menu> menus) {
        return menus.stream().map(this::toModel).collect(Collectors.toList());
    }
}
