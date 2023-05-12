package com.flavio.flashfeast.domain.service;

import com.flavio.flashfeast.api.model.input.CurrentOrderInput;
import com.flavio.flashfeast.domain.entities.Company;
import com.flavio.flashfeast.domain.entities.CurrentOrder;
import com.flavio.flashfeast.domain.entities.Menu;
import com.flavio.flashfeast.domain.entities.User;
import com.flavio.flashfeast.domain.enums.Status;
import com.flavio.flashfeast.domain.exception.DomainException;
import com.flavio.flashfeast.domain.repository.CompanyRepository;
import com.flavio.flashfeast.domain.repository.CurrentOrderRepository;
import com.flavio.flashfeast.domain.repository.MenuRepository;
import com.flavio.flashfeast.domain.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

@Service
@Transactional
public class CurrentOrderService {

    private final CurrentOrderRepository currentOrderRepository;
    private final CompanyRepository companyRepository;
    private final MenuRepository menuRepository;
    private final MenuService menuService;
    private final UserRepository userRepository;

    public CurrentOrderService(CurrentOrderRepository currentOrderRepository, CompanyRepository companyRepository, MenuRepository menuRepository, MenuService menuService, UserRepository userRepository) {
        this.currentOrderRepository = currentOrderRepository;
        this.companyRepository = companyRepository;
        this.menuRepository = menuRepository;
        this.menuService = menuService;
        this.userRepository = userRepository;
    }

    public void createOrder(int idCompany, int idMenu, int idUser, CurrentOrderInput currentOrderInput) {
        Company company = companyRepository.findById(idCompany).orElse(null);
        Menu menu = menuRepository.findById(idMenu).orElse(null);
        User user = userRepository.findById(idUser).orElse(null);

        assert menu != null;
        if(menu.getAvailableQuantity() < currentOrderInput.getQuantity()) throw new DomainException("Order an appropriate amount");
        int newAvailableQuantity = menu.getAvailableQuantity() - currentOrderInput.getQuantity();
        menu.setAvailableQuantity(newAvailableQuantity);

        menuService.updateMenu(idMenu, idCompany, menu);

        BigDecimal totalPrice = menu.getPrice().multiply(BigDecimal.valueOf(currentOrderInput.getQuantity()));
        CurrentOrder currentOrder = CurrentOrder.builder()
                .name(menu.getName())
                .quantity(currentOrderInput.getQuantity())
                .totalPrice(totalPrice)
                .image(menu.getImage())
                .status(Status.WAITING_FOR_COMPANY)
                .dateTime(new Date())
                .expirationTime(System.currentTimeMillis() + 1000 * 60 * 60 * 2) //2h
                .company(company)
                .menu(menu)
                .user(user)
                .build();
        currentOrderRepository.save(currentOrder);
    }
}
