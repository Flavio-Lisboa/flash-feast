package com.flavio.flashfeast.domain.service;

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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.Date;

@Service
public class CurrentOrderService {

    private final CurrentOrderRepository currentOrderRepository;
    private final CompanyRepository companyRepository;
    private final MenuRepository menuRepository;
    private final MenuService menuService;
    private final UserRepository userRepository;
    private final FinishedOrderService finishedOrderService;

    public CurrentOrderService(CurrentOrderRepository currentOrderRepository, CompanyRepository companyRepository,
                               MenuRepository menuRepository, MenuService menuService, UserRepository userRepository, FinishedOrderService finishedOrderService) {
        this.currentOrderRepository = currentOrderRepository;
        this.companyRepository = companyRepository;
        this.menuRepository = menuRepository;
        this.menuService = menuService;
        this.userRepository = userRepository;
        this.finishedOrderService = finishedOrderService;
    }

    public CurrentOrder getOrder(int idOrder) {
        return currentOrderRepository.findById(idOrder).orElseThrow(() -> new NotFoundException("Order Not Found"));
    }

    @Transactional
    public CurrentOrder createOrder(int idCompany, int idMenu, int idUser, CurrentOrder currentOrder) {
        Company company = companyRepository.findById(idCompany)
                .orElseThrow(() -> new NotFoundException("Company Not Found"));
        Menu menu = menuRepository.findById(idMenu).orElseThrow(() -> new NotFoundException("Menu Not Found"));
        User user = userRepository.findById(idUser).orElseThrow(() -> new NotFoundException("User Not Found"));

        if(menu.getAvailableQuantity() < currentOrder.getQuantity())
            throw new DomainException("Order an appropriate amount");

        int newAvailableQuantity = menu.getAvailableQuantity() - currentOrder.getQuantity();
        menu.setAvailableQuantity(newAvailableQuantity);

        BigDecimal totalPrice = menu.getPrice().multiply(BigDecimal.valueOf(currentOrder.getQuantity()));
        CurrentOrder currentOrderBuilder = CurrentOrder.builder()
                .name(menu.getName())
                .quantity(currentOrder.getQuantity())
                .totalPrice(totalPrice)
                .image(menu.getImage())
                .status(Status.WAITING_FOR_COMPANY)
                .dateTime(new Date())
                .expirationTime(System.currentTimeMillis() + 1000 * 60 * 30) //30min
                .company(company)
                .menu(menu)
                .user(user)
                .build();

        CurrentOrder currentOrderResponse = currentOrderRepository.save(currentOrderBuilder);
        menuService.updateMenu(idMenu, idCompany, menu);
        return currentOrderResponse;
    }

    public void deleteOrder(int idOrder) {
        boolean exists = currentOrderRepository.existsById(idOrder);
        if(!exists) throw new NotFoundException("Order Not Found");
        currentOrderRepository.deleteById(idOrder);
    }

    private CurrentOrder orderExists(int idOrder) {
        return currentOrderRepository.findById(idOrder).orElseThrow(() -> new NotFoundException("Oder Not Found"));
    }

    private CurrentOrder changeStatus(int idOrder, Status statusEnum, Status currentStatus) {
        CurrentOrder currentOrder = orderExists(idOrder);

        if(isExpired(currentOrder.getExpirationTime())) {
            deleteExpiredOrder(currentOrder);
        }

        if(currentOrder.getStatus() == statusEnum) throw new DomainException("This is already the current status");
        if(currentOrder.getStatus() != currentStatus) throw new DomainException("Change to correct status");

        currentOrder.setStatus(statusEnum);
        currentOrder.setExpirationTime(System.currentTimeMillis() + 1000 * 60 * 30); //30min
        return currentOrderRepository.save(currentOrder);
    }

    private void changeStatusToCanceled(int idOrder) {
        CurrentOrder currentOrder = orderExists(idOrder);

        if(isExpired(currentOrder.getExpirationTime())) {
            deleteExpiredOrder(currentOrder);
        }

        if(currentOrder.getStatus() == Status.CANCELED) throw new DomainException("This is already the current status");
        if(currentOrder.getStatus() == Status.ON_ROUTE_DELIVERY || currentOrder.getStatus() == Status.DELIVERED)
            throw new DomainException("You cannot cancel this order");

        returnQuantityToMenu(currentOrder.getMenu().getId(), currentOrder.getQuantity());
        currentOrder.setStatus(Status.CANCELED);
        finishedOrderService.createFinishedOrder(currentOrder);
        deleteOrder(idOrder);
    }

    private boolean isExpired(Long expirationTime) {
        return System.currentTimeMillis() > expirationTime;
    }

    private void returnQuantityToMenu(int idMenu, int quantity) {
        Menu menu = menuRepository.findById(idMenu).orElseThrow(() -> new NotFoundException("Menu Not Found"));
        menu.setAvailableQuantity(menu.getAvailableQuantity() + quantity);
        menuRepository.save(menu);
    }

    private void deleteExpiredOrder(CurrentOrder currentOrder) {
        returnQuantityToMenu(currentOrder.getMenu().getId(), currentOrder.getQuantity());
        currentOrder.setStatus(Status.EXPIRED);
        finishedOrderService.createFinishedOrder(currentOrder);
        deleteOrder(currentOrder.getId());
        throw new DomainException("This order has expired and will be deleted");
    }

    public void confirmAnOrder(int idOrder) {
        changeStatus(idOrder, Status.ACCEPTED, Status.WAITING_FOR_COMPANY);
    }

    public void inPreparation(int idOrder) {
        changeStatus(idOrder, Status.IN_PREPARATION, Status.ACCEPTED);
    }

    public void onRouteDelivery(int idOrder) {
        changeStatus(idOrder, Status.ON_ROUTE_DELIVERY, Status.IN_PREPARATION);
    }

    public void delivered(int idOrder) {
        CurrentOrder currentOrder = changeStatus(idOrder, Status.DELIVERED, Status.ON_ROUTE_DELIVERY);
        finishedOrderService.createFinishedOrder(currentOrder);
        deleteOrder(idOrder);
    }

    public void canceled(int idOrder) {
        changeStatusToCanceled(idOrder);
    }
}
