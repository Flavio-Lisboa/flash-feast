package com.flavio.flashfeast.api.controller;

import com.flavio.flashfeast.api.model.input.CurrentOrderInput;
import com.flavio.flashfeast.domain.entities.CurrentOrder;
import com.flavio.flashfeast.domain.service.CurrentOrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/orders")
public class CurrentOrderController {

    private final CurrentOrderService currentOrderService;

    public CurrentOrderController(CurrentOrderService currentOrderService) {
        this.currentOrderService = currentOrderService;
    }

    @PostMapping("/companies/{idCompany}/menus/{idMenu}/users/{idUser}")
    public ResponseEntity<CurrentOrder> createOrder(@PathVariable int idCompany, @PathVariable int idMenu, @PathVariable int idUser, @RequestBody CurrentOrderInput currentOrderInput) {
        currentOrderService.createOrder(idCompany, idMenu, idUser, currentOrderInput);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{idOrder}/accepted")
    public ResponseEntity<Void> confirmAnOrder(@PathVariable int idOrder) {
        currentOrderService.confirmAnOrder(idOrder);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{idOrder}/inPreparation")
    public ResponseEntity<Void> inPreparation(@PathVariable int idOrder) {
        currentOrderService.inPreparation(idOrder);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{idOrder}/onRouteDelivery")
    public ResponseEntity<Void> onRouteDelivery(@PathVariable int idOrder) {
        currentOrderService.onRouteDelivery(idOrder);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{idOrder}/delivered")
    public ResponseEntity<Void> delivered(@PathVariable int idOrder) {
        currentOrderService.delivered(idOrder);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{idOrder}/canceled")
    public ResponseEntity<Void> canceled(@PathVariable int idOrder) {
        currentOrderService.canceled(idOrder);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{idOrder}")
    public ResponseEntity<?> deleteOrder(@PathVariable int idOrder) {
        currentOrderService.deleteOrder(idOrder);
        return ResponseEntity.ok().build();
    }
}
