package com.edstem.expensemanager;

import com.edstem.expensemanager.contract.Response.TransactionResponse;
import com.edstem.expensemanager.model.Transaction;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.config.Configuration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

// import static sun.awt.image.MultiResolutionCachedImage.map;

@SpringBootApplication
public class ExpenseManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExpenseManagerApplication.class, args);
    }

    //    @Bean
    //    public ModelMapper modelMapper() {
    //        ModelMapper mapper = new ModelMapper();
    //        mapper.getConfiguration()
    //                .setFieldMatchingEnabled(true)
    //                .setFieldAccessLevel(Configuration.AccessLevel.PRIVATE);
    //        return mapper;
    //    }
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration()
                .setFieldMatchingEnabled(true)
                .setFieldAccessLevel(Configuration.AccessLevel.PRIVATE);

        // Add custom property map for Transaction to TransactionResponse
        mapper.addMappings(
                new PropertyMap<Transaction, TransactionResponse>() {
                    @Override
                    protected void configure() {
                        map().setUser(source.getUser().getId());
                    }
                });

        return mapper;
    }
}
