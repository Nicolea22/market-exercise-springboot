package com.nico.market.service.impl;

import com.nico.market.exception.BadRequestException;
import com.nico.market.exception.MarketNotFoundException;
import com.nico.market.model.dto.CustomerCreationDTO;
import com.nico.market.model.dto.CustomerDTO;
import com.nico.market.model.dto.MarketCreationDTO;
import com.nico.market.model.dto.MarketUpdateDTO;
import com.nico.market.model.entity.Market;
import com.nico.market.model.mapper.CustomerMapper;
import com.nico.market.model.mapper.MarketMapper;
import com.nico.market.repository.MarketRepository;
import com.nico.market.service.CustomerService;
import com.nico.market.service.MarketService;
import com.nico.market.validator.ValidationResult;
import com.nico.market.validator.market.MarketValidation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class MarketServiceImpl implements MarketService {

    private final MarketRepository marketRepository;
    private final CustomerService customerService;
    private final MarketMapper mapper;
    private final CustomerMapper customerMapper;
    @Autowired
    @Qualifier("Creation")
    private final MarketValidation marketValidationCreation;

    @Autowired
    @Qualifier("Update")
    private final MarketValidation marketValidationUpdate;


    @Override
    public MarketCreationDTO save(MarketCreationDTO market) {
        ValidationResult validations = marketValidationCreation.validate(market);
        if(validations.notValid()) {
            throw new BadRequestException(validations.getErrorMsg());
        }
        return mapper.toDto(marketRepository.save(mapper.toEntity(market)));
    }

    @Override
    public MarketCreationDTO getById(String id) {
        Optional<Market> market = marketRepository.findById(UUID.fromString(id));
        if(market.isEmpty()) {
            throw new MarketNotFoundException("Market with Id: " + id + " Not Found");
        }
        return mapper.toDto(market.get());
    }

    @Override
    public List<MarketCreationDTO> findAll() {
        ArrayList<MarketCreationDTO> result = new ArrayList<>();
        List<Market> markets = marketRepository.findAll();
        for(Market market : markets) {
            result.add(mapper.toDto(market));
        }
        return result;
    }

    @Override
    public void delete(String id) {
        Optional<Market> market = marketRepository.findById(UUID.fromString(id));
        if(market.isEmpty()) {
            throw new MarketNotFoundException("Market with Id: " + id + " Not Found");
        }
        marketRepository.delete(market.get());
        mapper.toDto(market.get());
    }

    @Override
    public MarketCreationDTO update(MarketUpdateDTO marketDto) {
        ValidationResult validations = marketValidationUpdate.validate(marketDto);
        if(validations.notValid()) {
            throw new MarketNotFoundException("Market or customer not found " + validations.getErrorMsg());
        }
        Optional<Market> result = marketRepository.findById(marketDto.getId());
        Market market = result.get();
        if (marketDto.getCustomers() != null) {
            for (CustomerCreationDTO cust : marketDto.getCustomers()) {
                CustomerCreationDTO customer = customerService.getById(cust.getId().toString());
                market.getCustomers().add(customerMapper.toEntity(customer));
            }
        }
        return mapper.toDto(marketRepository.save(market));
    }

}
