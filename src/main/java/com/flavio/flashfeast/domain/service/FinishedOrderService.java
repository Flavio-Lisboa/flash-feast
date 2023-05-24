package com.flavio.flashfeast.domain.service;

import com.flavio.flashfeast.domain.entities.FinishedOrder;
import com.flavio.flashfeast.domain.exception.NotFoundException;
import com.flavio.flashfeast.domain.repository.FinishedOrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FinishedOrderService {

    private final FinishedOrderRepository finishedOrderRepository;

    public FinishedOrderService(FinishedOrderRepository finishedOrderRepository) {
        this.finishedOrderRepository = finishedOrderRepository;
    }

    public FinishedOrder getFinishedOrder(int idCompany) {
        return finishedOrderRepository.findById(idCompany).orElseThrow(() -> new NotFoundException("Finished Order Not Found"));
    }
    public List<FinishedOrder> getAllByCompanyId(int idCompany) {
        return finishedOrderRepository.getAllByCompanyId(idCompany);
    }
}
