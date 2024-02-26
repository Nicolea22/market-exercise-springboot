package com.nico.market.config;

import com.nico.market.model.dto.CustomerCreationDTO;
import com.nico.market.model.dto.CustomerDTO;
import com.nico.market.model.dto.MarketCreationDTO;
import com.nico.market.model.entity.Role;
import com.nico.market.model.entity.User;
import com.nico.market.repository.UserRepository;
import com.nico.market.service.CustomerService;
import com.nico.market.service.MarketService;
import com.nico.market.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class SeedDataConfig implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final MarketService marketService;
    private final CustomerService customerService;

    @Override
    public void run(String... args) throws Exception {

        if (userRepository.count() == 0) {

            User admin = User
                    .builder()
                    .firstName("admin")
                    .lastName("admin")
                    .email("admin@admin.com")
                    .password(passwordEncoder.encode("password"))
                    .role(Role.ROLE_ADMIN)
                    .build();

            userService.save(admin);
            log.debug("created ADMIN user - {}", admin);

            User user = User
                    .builder()
                    .firstName("user")
                    .lastName("user")
                    .email("user@user.com")
                    .password(passwordEncoder.encode("password"))
                    .role(Role.ROLE_USER)
                    .build();

            userService.save(user);
            log.debug("created USER user - {}", user);

            List<CustomerCreationDTO> customers1 = new ArrayList<>();
            List<CustomerCreationDTO> customers2 = new ArrayList<>();
            List<CustomerCreationDTO> customers3 = new ArrayList<>();

            customers1.add(customerService.save(new CustomerCreationDTO(null, "Customer 1")));
            customers1.add(customerService.save(new CustomerCreationDTO(null, "Customer 2")));
            customers1.add(customerService.save(new CustomerCreationDTO(null, "Customer 3")));
            customers1.add(customerService.save(new CustomerCreationDTO(null, "Customer 4")));
            customers2.add(customerService.save(new CustomerCreationDTO(null, "Customer 5")));
            customers2.add(customerService.save(new CustomerCreationDTO(null, "Customer 6")));
            customers3.add(customerService.save(new CustomerCreationDTO(null, "Customer 7")));
            customers3.add(customerService.save(new CustomerCreationDTO(null, "Customer 8")));
            customers3.add(customerService.save(new CustomerCreationDTO(null, "Customer 9")));
            customerService.save(new CustomerCreationDTO(null, "Customer 10"));
            customerService.save(new CustomerCreationDTO(null, "Customer 11"));
            customerService.save(new CustomerCreationDTO(null, "Customer 12"));
            customerService.save(new CustomerCreationDTO(null, "Customer 13"));

            marketService.save(new MarketCreationDTO(null, "MAE", "MARKET 1", "ARGENTINA", customers1));
            marketService.save(new MarketCreationDTO(null, "ROFEX", "MARKET 2", "ARGENTINA", customers2));
            marketService.save(new MarketCreationDTO(null, "UFEX", "MARKET 3", "URUGUAY", customers3));
        }
    }
}
