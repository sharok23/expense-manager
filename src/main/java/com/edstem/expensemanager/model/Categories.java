package com.edstem.expensemanager.model;

import com.edstem.expensemanager.constant.Type;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;

@Entity
@Getter
public class Categories {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;
    @Enumerated(EnumType.STRING)
    private Type type;
    private String color;

}
