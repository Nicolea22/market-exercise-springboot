package com.nico.market.service.impl;

import com.nico.market.model.dto.response.StatsResponseDTO;
import com.nico.market.repository.CustomerRepository;
import com.nico.market.repository.MarketRepository;
import com.nico.market.service.StatisticsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class StatisticsServiceImpl implements StatisticsService {

    private final CustomerRepository customerRepository;

    @Override
    public List<StatsResponseDTO> makeStats() {
        List<StatsResponseDTO> response = new ArrayList<>();

        Integer amountOfCustomersWithoutMarket = customerRepository.amountWithoutMarket();

        Collection<Object> customersFromArgentina = customerRepository.findFrom("ARGENTINA");
        Collection<Object> customersFromUruguay = customerRepository.findFrom("URUGUAY");

        response.add(this.calculatePercentages(customersFromArgentina, amountOfCustomersWithoutMarket));
        response.add(this.calculatePercentages(customersFromUruguay, amountOfCustomersWithoutMarket));
        return response;
    }


    private StatsResponseDTO calculatePercentages(Collection<Object> countryData, Integer amountOfCustomersWithoutMarket) {
        StatsResponseDTO dto = new StatsResponseDTO();
        Map<String, Integer> auxMap = new HashMap<>();
        for (Iterator<Object> it = countryData.iterator(); it.hasNext();) {
            Object[] object = (Object[]) it.next();
            dto.setCountry((String) object[0]);
            auxMap.compute((String) object[1], (k, v) -> v == null ? 1 : v + 1);
        }
        HashMap<String, BigDecimal> finalMap = new HashMap<>();
        for(String key : auxMap.keySet()) {
            BigDecimal marketsAmount = new BigDecimal(auxMap.get(key));
            BigDecimal totalMarketsAmount = new BigDecimal(auxMap.values().stream().mapToInt(Integer::intValue).sum()
                    + amountOfCustomersWithoutMarket);
            BigDecimal percentage = marketsAmount.divide(totalMarketsAmount, 2,RoundingMode.HALF_UP);
            finalMap.put(key, percentage.multiply(new BigDecimal(100)));
        }
        dto.setMarket(finalMap);
        return dto;
    }

}
