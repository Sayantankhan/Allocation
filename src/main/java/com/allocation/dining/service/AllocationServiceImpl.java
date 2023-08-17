package com.allocation.dining.service;

import com.allocation.dining.entity.ActiveWorkersEntity;
import com.allocation.dining.entity.OrderEntity;
import com.allocation.dining.entity.TaskEntity;
import com.allocation.dining.entity.WorkersEntity;
import com.allocation.dining.model.Task;
import com.allocation.dining.repository.OrderRepository;
import com.allocation.dining.repository.RobotsRepository;
import com.allocation.dining.repository.TasksRepository;
import com.allocation.dining.scheduler.TaskQueueScheduler;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Order;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AllocationServiceImpl implements AllocationService{

    @Value("${spring.kafka.topic}")
    private String kafkaTopic;
    private OrderRepository orderRepository;
    private RobotsRepository robotsRepository;
    private TasksRepository taskRepository;
    // private TaskQueueService taskQueueService;
    private ObjectMapper mapper;
    private KafkaTemplate<String, String> kafkaTemplate;

    AllocationServiceImpl(@Autowired OrderRepository orderRepository,
                          @Autowired RobotsRepository robotsRepository,
                          @Autowired TasksRepository tasksRepository,
                          // @Autowired TaskQueueService taskQueueService,
                          @Autowired KafkaTemplate<String, String> kafkaTemplate) {
        this.orderRepository = orderRepository;
        this.robotsRepository = robotsRepository;
        this.taskRepository = tasksRepository;
        // this.taskQueueService = taskQueueService;
        mapper = new ObjectMapper();
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public List<WorkersEntity> getActiveWorkers() {
        return robotsRepository.findAllActiveWorkers();
    }

    @Override
    public List getActiveWorkersByTask() {
        return taskRepository.findAllActiveTaskWorkers();
    }

    @Override
    public void createNewTask(TaskEntity task) {
        taskRepository.save(task);
    }

    private void createTask(OrderEntity entity,String timestamp) throws Exception {
        TaskEntity task = TaskEntity.builder()
                .id(String.valueOf(UUID.randomUUID()))
                .orderId(entity.getId())
                .isActive(false)
                .description("Task For ".concat(entity.getId()))
                .createdAt(timestamp)
                .build();
        // taskQueueService.addToTaskPool(Task.builder().msg(mapper.writeValueAsString(task)).build());
        kafkaTemplate.send("test-local-allocation-topic", mapper.writeValueAsString(task));
    }

    @Transactional
    @Override
    public void createOrder(OrderEntity order) throws Exception {
        orderRepository.save(order);
        // create Task
        String timestamp = String.valueOf(new Timestamp(System.currentTimeMillis()));
        createTask(order, timestamp);
    }

    @Transactional
    @Override
    public void checkoutOrderById(String orderId) {
        OrderEntity entity = orderRepository.findById(orderId).get();
        entity.setActive(false);
        entity.setUpdatedAt(null);
        orderRepository.save(entity);
    }

    @Override
    public OrderEntity getOrderById(String orderId) {
        return orderRepository.findById(orderId).get();
    }

    @Override
    public void assignWorkers() {

    }

    @Override
    public List<ActiveWorkersEntity> getAggWorkerList() {
        List<ActiveWorkersEntity> activeWorkersEntities = taskRepository.findAllActiveTaskWorkers();
        List<WorkersEntity> workersEntities = robotsRepository.findAllActiveWorkers();

        for (WorkersEntity workersEntity : workersEntities) {
            Optional workersEntityOptional = activeWorkersEntities.stream()
                    .filter(activeWorkersEntity -> activeWorkersEntity.getWorkers().getId().equalsIgnoreCase(workersEntity.getId()))
                    .findAny();

            if (!workersEntityOptional.isPresent()) {
                ActiveWorkersEntity entity = ActiveWorkersEntity.builder()
                        .workers(workersEntity)
                        .activeTaskCount("0")
                        .build();

                activeWorkersEntities.add(entity);
            }
        }

        return activeWorkersEntities;
    }

    @Transactional
    @Override
    public void updateOrderById(String orderId, OrderEntity orderEntity) throws Exception {
        String timestamp = String.valueOf(new Timestamp(System.currentTimeMillis()));
        OrderEntity entity = orderRepository.findById(orderId).get();
        entity.setUpdatedAt(timestamp);
        orderRepository.save(entity);
        createTask(entity, timestamp);
    }

    @Override
    public TaskEntity getTasksById(String taskId) {
        return taskRepository.findById(taskId).get();
    }

    @Override
    public List<TaskEntity> getTasksByOrderId(String orderId) {
        return taskRepository.findByOrderId(orderId);
    }

    @Override
    public List<TaskEntity> getActiveTask() {
        return taskRepository.findByIsActive(true);
    }

    @Override
    public void checkoutTaskByOrderId(String orderId) {
        List<TaskEntity> taskEntities = taskRepository.findByOrderId(orderId);
        for (TaskEntity taskEntity : taskEntities){
            taskEntity.setActive(false);
            taskEntity.setUpdatedAt(null);
        }
        taskRepository.saveAll(taskEntities);
    }
}
