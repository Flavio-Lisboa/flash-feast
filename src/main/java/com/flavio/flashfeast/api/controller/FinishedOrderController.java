package com.flavio.flashfeast.api.controller;

import com.flavio.flashfeast.api.mapper.FinishedOrderMapper;
import com.flavio.flashfeast.api.model.FinishedOrderModel;
import com.flavio.flashfeast.domain.entities.FinishedOrder;
import com.flavio.flashfeast.domain.service.FinishedOrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/v1/finished-order")
public class FinishedOrderController {

    private final FinishedOrderService finishedOrderService;
    private final FinishedOrderMapper finishedOrderMapper;

    public FinishedOrderController(FinishedOrderService finishedOrderService, FinishedOrderMapper finishedOrderMapper) {
        this.finishedOrderService = finishedOrderService;
        this.finishedOrderMapper = finishedOrderMapper;
    }

    @GetMapping("/{idFinishedOrder}")
    public ResponseEntity<FinishedOrderModel> getFinishedOrder(@PathVariable int idFinishedOrder) {
        FinishedOrder finishedOrder = finishedOrderService.getFinishedOrder(idFinishedOrder);

        FinishedOrderModel finishedOrderModel = finishedOrderMapper.toModel(finishedOrder);
        return ResponseEntity.ok(finishedOrderModel);
    }

    @GetMapping("/companies/{idCompany}")
    public ResponseEntity<List<FinishedOrderModel>> getAllByCompanyId(@PathVariable int idCompany) {
        List<FinishedOrder> finishedOrderList = finishedOrderService.getAllByCompanyId(idCompany);

        List<FinishedOrderModel> finishedOrderModelList = finishedOrderMapper.toCollectionList(finishedOrderList);
        return ResponseEntity.ok(finishedOrderModelList);
    }
}
