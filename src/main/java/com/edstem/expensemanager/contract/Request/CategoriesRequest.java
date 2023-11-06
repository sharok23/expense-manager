package com.edstem.expensemanager.contract.Request;

import com.edstem.expensemanager.constant.Color;
import com.edstem.expensemanager.constant.Type;
import lombok.Getter;

@Getter
public class CategoriesRequest {

    private Type type;
    private Color color;
}
