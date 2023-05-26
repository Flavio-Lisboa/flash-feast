package com.flavio.flashfeast.api.mapper;

import com.flavio.flashfeast.api.model.FinishedOrderModel;
import com.flavio.flashfeast.domain.entities.FinishedOrder;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Component
public class FinishedOrderMapper {

    private final ModelMapper modelMapper;

    public FinishedOrderModel toModel(FinishedOrder finishedOrder) {
        return modelMapper.map(finishedOrder, FinishedOrderModel.class);
    }

    public List<FinishedOrderModel> toCollectionList(List<FinishedOrder> finishedOrderList) {
        return finishedOrderList.stream().map(this::toModel).collect(Collectors.toList());
    }
}
