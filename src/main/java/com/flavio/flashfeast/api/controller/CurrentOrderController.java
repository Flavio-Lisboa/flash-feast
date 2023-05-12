package com.flavio.flashfeast.api.controller;

import com.flavio.flashfeast.api.model.input.CurrentOrderInput;
import com.flavio.flashfeast.domain.entities.CurrentOrder;
import com.flavio.flashfeast.domain.service.CurrentOrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/order")
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
}
