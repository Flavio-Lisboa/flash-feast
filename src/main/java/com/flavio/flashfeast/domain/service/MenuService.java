package com.flavio.flashfeast.domain.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.flavio.flashfeast.domain.entities.Company;
import com.flavio.flashfeast.domain.entities.Menu;
import com.flavio.flashfeast.domain.repository.CompanyRepository;
import com.flavio.flashfeast.domain.repository.MenuRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
public class MenuService {

    private final MenuRepository menuRepository;
    private final Cloudinary cloudinary;
    private final CompanyRepository companyRepository;


    public MenuService(MenuRepository menuRepository, Cloudinary cloudinary, CompanyRepository companyRepository) {
        this.menuRepository = menuRepository;
        this.cloudinary = cloudinary;
        this.companyRepository = companyRepository;
    }

    public List<Menu> findAll() {
        return menuRepository.findAll();
    }

    public Menu createMenu(int companyId, Menu menu, MultipartFile image) {
        Optional<Company> companyData = companyRepository.findById(companyId);
        if(companyData.isEmpty()) return null;

        String imageUrl = uploadFile(image);

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

    private String uploadFile(MultipartFile logo) {
        try {
            File uploadedLogo = convertMultipartToFile(logo);
            Map<?, ?> uploadResult = cloudinary.uploader().upload(uploadedLogo, ObjectUtils.asMap("folder", "menu_image"));
            boolean isDeleted = uploadedLogo.delete();

            if (isDeleted)System.out.println("File successfully deleted");
            else System.out.println("File doesn't exist");

            return uploadResult.get("secure_url").toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private File convertMultipartToFile(MultipartFile logo) throws IOException {
        File convertedLogo = new File(Objects.requireNonNull(logo.getOriginalFilename()));
        FileOutputStream fileOutputStream = new FileOutputStream(convertedLogo);
        fileOutputStream.write(logo.getBytes());
        fileOutputStream.close();
        return convertedLogo;
    }
}
