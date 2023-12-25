package com.tobi.employeeplsqlpractice.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Employee {
    private Integer employeeId;
    private Float salary;
    private Integer orderId;
    private Date orderDate;
    private String shipName;
}
