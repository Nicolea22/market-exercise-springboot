package com.nico.market.model.mapper;

import com.nico.market.model.dto.MarketCreationDTO;
import com.nico.market.model.entity.Market;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MarketMapper {
    MarketCreationDTO toDto(Market Market);
    Market toEntity(MarketCreationDTO market);

}
