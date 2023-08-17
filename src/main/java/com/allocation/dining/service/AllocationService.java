package com.allocation.dining.service;

import com.allocation.dining.entity.ActiveWorkersEntity;
import com.allocation.dining.entity.OrderEntity;
import com.allocation.dining.entity.TaskEntity;
import com.allocation.dining.entity.WorkersEntity;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

public interface AllocationService {

    public List<WorkersEntity> getActiveWorkers();

    public List getActiveWorkersByTask();

    public void createNewTask(TaskEntity task);

    public void createOrder(OrderEntity order) throws Exception;

    public void checkoutOrderById(String orderId);

    public OrderEntity getOrderById(String orderId);

    public void assignWorkers();

    public TaskEntity getTasksById(String taskId);
    public List<TaskEntity> getTasksByOrderId(String orderId);

    public List<TaskEntity> getActiveTask();

    void checkoutTaskByOrderId(String orderId);

    public List<ActiveWorkersEntity> getAggWorkerList();

    void updateOrderById(String orderId, OrderEntity orders) throws Exception;
}
