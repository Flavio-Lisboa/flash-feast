package com.flavio.flashfeast.domain.service;

import com.flavio.flashfeast.api.mapper.MenuWithoutCompanyDataMapper;
import com.flavio.flashfeast.api.model.CompanyMenuModel;
import com.flavio.flashfeast.domain.entities.Company;
import com.flavio.flashfeast.domain.entities.Menu;
import com.flavio.flashfeast.domain.exception.NotFoundException;
import com.flavio.flashfeast.domain.repository.CompanyRepository;
import com.flavio.flashfeast.domain.repository.MenuRepository;
import com.flavio.flashfeast.domain.utils.CloudinaryUtil;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class MenuService {

    private final MenuRepository menuRepository;
    private final CompanyRepository companyRepository;
    private final CloudinaryUtil cloudinaryUtil;
    private final MenuWithoutCompanyDataMapper menuWithoutCompanyDataMapperMapper;

    public MenuService(MenuRepository menuRepository, CompanyRepository companyRepository,
                       CloudinaryUtil cloudinaryUtil, MenuWithoutCompanyDataMapper menuWithoutCompanyDataMapperMapper) {
        this.menuRepository = menuRepository;
        this.companyRepository = companyRepository;
        this.cloudinaryUtil = cloudinaryUtil;
        this.menuWithoutCompanyDataMapperMapper = menuWithoutCompanyDataMapperMapper;
    }

    public List<CompanyMenuModel> findAll() {
        List<CompanyMenuModel> companyMenuModelList = new ArrayList<>();

        List<Company> companies = companyRepository.findAll();
        companies.forEach(company -> {
            List<Menu> menus = menuRepository.getMenusByCompanyId(company.getId());

            if(!menus.isEmpty()) {
                CompanyMenuModel companyMenuModel = CompanyMenuModel.builder()
                        .companyId(company.getId())
                        .companyName(company.getName())
                        .companyLogo(company.getLogo())
                        .menus(menuWithoutCompanyDataMapperMapper.toCollectionModel(menus))
                        .build();
                companyMenuModelList.add(companyMenuModel);
            }
        });

        Collections.shuffle(companyMenuModelList);
        return companyMenuModelList;
    }

    public Menu createMenu(int companyId, Menu menu, MultipartFile image) {
        Optional<Company> companyData = companyRepository.findById(companyId);
        if(companyData.isEmpty()) throw new NotFoundException("Company Not Found");

        String folder = "menu_image";
        String imageUrl = cloudinaryUtil.uploadFile(image, folder);
        Menu menuBuilder = Menu.builder()
                .name(menu.getName())
                .category(menu.getCategory())
                .description(menu.getDescription())
                .availableQuantity(menu.getAvailableQuantity())
                .price(menu.getPrice())
                .image(imageUrl)
                .company(companyData.get())
                .build();
        return menuRepository.save(menuBuilder);
    }

    public Menu findMenu(int idCompany, int idMenu) {
        Optional<Menu> menu = menuRepository.findMenu(idCompany, idMenu);
        return menu.orElseThrow(() -> new NotFoundException("Menu Not Found"));
    }

    public List<Menu> getMenusByCompanyId(int idCompany) {
        List<Menu> menuList= menuRepository.getMenusByCompanyId(idCompany);
        if(menuList.isEmpty()) throw new NotFoundException("Menus Not Found");
        return menuList;
    }

    public void deleteMenuByCompanyId(int idMenu, int idCompany) {
        boolean menuExists = menuRepository.existsById(idMenu);
        if(!menuExists) throw new NotFoundException("Menu Not Found");

        menuRepository.deleteMenuByCompanyId(idMenu, idCompany);
    }

    public Menu updateMenu(int idMenu, int idCompany, Menu menu) {
        boolean menuExists = menuRepository.existsById(idMenu);
        if(!menuExists) throw new NotFoundException("Menu Not Found");

        boolean companyExist = companyRepository.existsById(idCompany);
        if(!companyExist) throw new NotFoundException("Company Not Found");

        menuRepository.updateMenu(idMenu, idCompany, menu);
        return menuRepository.findById(idMenu).get();
    }
}
