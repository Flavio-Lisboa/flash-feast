package com.flavio.flashfeast.api.controller;

import com.flavio.flashfeast.api.mapper.CurrentOrderMapper;
import com.flavio.flashfeast.api.model.CurrentOrderModel;
import com.flavio.flashfeast.api.model.input.CurrentOrderInput;
import com.flavio.flashfeast.domain.entities.CurrentOrder;
import com.flavio.flashfeast.domain.service.CurrentOrderService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/orders")
public class CurrentOrderController {

    private final CurrentOrderService currentOrderService;
    private final CurrentOrderMapper currentOrderMapper;

    public CurrentOrderController(CurrentOrderService currentOrderService, CurrentOrderMapper currentOrderMapper) {
        this.currentOrderService = currentOrderService;
        this.currentOrderMapper = currentOrderMapper;
    }

    @GetMapping("/{idOder}")
    public ResponseEntity<CurrentOrderModel> getOrder(@PathVariable int idOder) {
        CurrentOrder currentOrder = currentOrderService.getOrder(idOder);

        CurrentOrderModel currentOrderModel = currentOrderMapper.toModel(currentOrder);
        return ResponseEntity.ok(currentOrderModel);
    }

    @PostMapping("/companies/{idCompany}/menus/{idMenu}/users/{idUser}")
    public ResponseEntity<CurrentOrderModel> createOrder(@PathVariable int idCompany, @PathVariable int idMenu, @PathVariable int idUser, @Valid @RequestBody CurrentOrderInput currentOrderInput) {
        CurrentOrder currentOrder = currentOrderMapper.toEntity(currentOrderInput);
        CurrentOrder currentOrderResponse = currentOrderService.createOrder(idCompany, idMenu, idUser, currentOrder);

        CurrentOrderModel currentOrderModel = currentOrderMapper.toModel(currentOrderResponse);
        return ResponseEntity.ok(currentOrderModel);
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
