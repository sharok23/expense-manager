package com.edstem.expensemanager.contract.Response;

import com.edstem.expensemanager.constant.Color;
import com.edstem.expensemanager.constant.Type;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class CategoriesResponse {

    private Long id;
    private Type type;
    private Color color;
}
