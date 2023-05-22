package com.flavio.flashfeast.domain.service;

import com.flavio.flashfeast.api.model.input.CurrentOrderInput;
import com.flavio.flashfeast.domain.entities.Company;
import com.flavio.flashfeast.domain.entities.CurrentOrder;
import com.flavio.flashfeast.domain.entities.Menu;
import com.flavio.flashfeast.domain.entities.User;
import com.flavio.flashfeast.domain.enums.Status;
import com.flavio.flashfeast.domain.exception.DomainException;
import com.flavio.flashfeast.domain.exception.NotFoundException;
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
        Company company = companyRepository.findById(idCompany).orElseThrow(() -> new NotFoundException("Company Not Found"));
        Menu menu = menuRepository.findById(idMenu).orElseThrow(() -> new NotFoundException("Menu Not Found"));
        User user = userRepository.findById(idUser).orElseThrow(() -> new NotFoundException("User Not Found"));

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
                .expirationTime(System.currentTimeMillis() + 1000 * 60 * 30) //30min
                .company(company)
                .menu(menu)
                .user(user)
                .build();
        currentOrderRepository.save(currentOrder);
    }

    public void confirmAnOrder(int idOrder) {
        changeStatus(idOrder, Status.ACCEPTED);
    }

    public void inPreparation(int idOrder) {
        changeStatus(idOrder, Status.IN_PREPARATION);
    }

    public void onRouteDelivery(int idOrder) {
        changeStatus(idOrder, Status.ON_ROUTE_DELIVERY);
    }

    public void delivered(int idOrder) {
        changeStatus(idOrder, Status.DELIVERED);
    }

    public void canceled(int idOrder) {
        changeStatus(idOrder, Status.CANCELED);
    }

    public void deleteOrder(int idOrder) {
        boolean exists = currentOrderRepository.existsById(idOrder);
        if(!exists) throw new NotFoundException("Order Not Found");
        currentOrderRepository.deleteById(idOrder);
    }

    public CurrentOrder orderExists(int idOrder) {
        return currentOrderRepository.findById(idOrder).orElseThrow(() -> new NotFoundException("Oder Not Found"));
    }

    public void changeStatus(int idOrder, Status statusEnum) {
        CurrentOrder currentOrder = orderExists(idOrder);
        currentOrder.setStatus(statusEnum);
        currentOrder.setExpirationTime(System.currentTimeMillis() + 1000 * 60 * 30); //30min
        currentOrderRepository.save(currentOrder);
    }
}
