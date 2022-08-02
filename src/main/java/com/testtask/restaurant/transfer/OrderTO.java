package com.testtask.restaurant.transfer;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@EqualsAndHashCode
public class OrderTO {

    private int id;

    private int dishId;

    private Date orderDate;

    private Date preparationDate;

    private Date serviceDate;

    private List<Integer> employeeIds;

}
