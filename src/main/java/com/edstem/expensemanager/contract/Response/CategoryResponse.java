package com.edstem.expensemanager.contract.Response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryResponse {

    private Long id;
    private String type;
    private String color;
}
