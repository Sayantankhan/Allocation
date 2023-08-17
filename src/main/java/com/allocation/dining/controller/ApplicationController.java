package com.allocation.dining.controller;

import com.allocation.dining.entity.OrderEntity;
import com.allocation.dining.entity.TaskEntity;
import com.allocation.dining.entity.WorkersEntity;
import com.allocation.dining.model.Order;
import com.allocation.dining.service.AllocationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("dining")
@Slf4j
public class ApplicationController {

    @Autowired
    private AllocationService allocationService;

    @RequestMapping(value="/robots/available", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity getWorkers() {
        List<WorkersEntity> activeWorkerList =  allocationService.getActiveWorkers();
        // List activeWorkerList = allocationService.getAggWorkerList();
        return new ResponseEntity<>(activeWorkerList, HttpStatus.OK);
    }

    @RequestMapping(value="/workers/available", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity getAggWorkers() {
        List activeWorkerList = allocationService.getAggWorkerList();
        return new ResponseEntity<>(activeWorkerList.stream().sorted().collect(Collectors.toList()), HttpStatus.OK);
    }

    @RequestMapping(value="/order", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public ResponseEntity createOrder(@RequestBody OrderEntity order) throws Exception {
        String timestamp = String.valueOf(new Timestamp(System.currentTimeMillis()));
        order.setCreatedAt(timestamp);
        order.setUpdatedAt(timestamp);
        allocationService.createOrder(order);
        return new ResponseEntity<>("{\"message\" : \"Order Created\"}", HttpStatus.OK);
    }

    @RequestMapping(value="/order/{orderId}", method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
    public ResponseEntity checkoutOrder(@PathVariable String orderId, @RequestBody OrderEntity order) throws Exception {
        allocationService.updateOrderById(orderId, order);
        return new ResponseEntity<>("{\"message\" : \"Order Updated\"}", HttpStatus.OK);
    }

    @RequestMapping(value="/order/{orderId}/checkout", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity checkoutOrder(@PathVariable String orderId) {
        allocationService.checkoutOrderById(orderId);
        allocationService.checkoutTaskByOrderId(orderId);
        return new ResponseEntity<>("{\"message\" : \"Order Check Out Completed\"}", HttpStatus.OK);
    }

    @RequestMapping(value="/order/{orderId}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity getOrderByID(@PathVariable String orderId) {
        Order order = Order.builder()
                .order(allocationService.getOrderById(orderId))
                .tasks(allocationService.getTasksByOrderId(orderId))
                .build();
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @RequestMapping(value="/tasks/{taskId}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity getTaskById(@PathVariable String taskId) {
        return new ResponseEntity<>(allocationService.getTasksById(taskId), HttpStatus.OK);
    }

    @RequestMapping(value="/tasks/active", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity getActiveTasks() {
        return new ResponseEntity<>(allocationService.getActiveTask(), HttpStatus.OK);
    }

    @RequestMapping(value="/task", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public ResponseEntity createTask(@RequestBody TaskEntity task) {
        String timestamp = String.valueOf(new Timestamp(System.currentTimeMillis()));
        task.setCreatedAt(timestamp);
        task.setUpdatedAt(timestamp);
        allocationService.createNewTask(task);
        return new ResponseEntity<>("{\"message\" : \"Task Created\"}", HttpStatus.OK);
    }

}
