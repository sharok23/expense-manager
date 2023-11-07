package com.edstem.expensemanager.contract.Response;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class CategoryResponse {

    private Long id;
    private String type;
    private String color;
}
