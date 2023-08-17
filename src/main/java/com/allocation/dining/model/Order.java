package com.allocation.dining.model;

import com.allocation.dining.entity.OrderEntity;
import com.allocation.dining.entity.TaskEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {

    OrderEntity order;
    List<TaskEntity> tasks;
}
