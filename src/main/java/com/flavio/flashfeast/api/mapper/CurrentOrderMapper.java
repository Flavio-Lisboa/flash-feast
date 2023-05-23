package com.flavio.flashfeast.api.mapper;

import com.flavio.flashfeast.api.model.CurrentOrderModel;
import com.flavio.flashfeast.api.model.input.CurrentOrderInput;
import com.flavio.flashfeast.domain.entities.CurrentOrder;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class CurrentOrderMapper {

    private final ModelMapper modelMapper;

    public CurrentOrder toEntity(CurrentOrderInput currentOrderInput) {
        return modelMapper.map(currentOrderInput, CurrentOrder.class);
    }

    public CurrentOrderModel toModel(CurrentOrder currentOrder) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        return modelMapper.map(currentOrder, CurrentOrderModel.class);
    }
}

