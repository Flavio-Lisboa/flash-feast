package com.flavio.flashfeast.api.controller;

import com.flavio.flashfeast.domain.entities.FinishedOrder;
import com.flavio.flashfeast.domain.service.FinishedOrderService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/finished-order")
public class FinishedOrderController {

    private final FinishedOrderService finishedOrderService;

    public FinishedOrderController(FinishedOrderService finishedOrderService) {
        this.finishedOrderService = finishedOrderService;
    }

    @GetMapping("/{idFinishedOrder}")
    public FinishedOrder getFinishedOrder(@PathVariable int idFinishedOrder) {
        return finishedOrderService.getFinishedOrder(idFinishedOrder);
    }

    @GetMapping("/companies/{idCompany}")
    public List<FinishedOrder> getAllByCompanyId(@PathVariable int idCompany) {
        return finishedOrderService.getAllByCompanyId(idCompany);
    }
}
