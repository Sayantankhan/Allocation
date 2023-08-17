package com.allocation.dining.scheduler;

import com.allocation.dining.entity.ActiveWorkersEntity;
import com.allocation.dining.entity.TaskEntity;
import com.allocation.dining.entity.WorkersEntity;
import com.allocation.dining.model.Task;
import com.allocation.dining.service.AllocationService;
import com.allocation.dining.service.TaskQueueService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;
import java.util.stream.Collectors;

@Configuration
// @EnableScheduling
public class TaskQueueScheduler<T> {
    private ObjectMapper objectMapper;
    private AllocationService allocationService;


    TaskQueueScheduler(@Autowired AllocationService allocationService){
        objectMapper = new ObjectMapper();
        this.allocationService = allocationService;
    }

//    @Scheduled(fixedDelay = 1000)
//    public void pollFromTasKQueue() throws JsonProcessingException {
//        int pendingTask = taskQueueService.getPendingTaskCount();
//        while (pendingTask > 0) {
//            Task task = taskQueueService.getNextTask();
//            TaskEntity object = objectMapper.readValue(task.getMsg(), TaskEntity.class);
//            // allocate optimal worker : logic : weightage : less number of task
//            List<ActiveWorkersEntity> list = allocationService.getAggWorkerList();
//            List<ActiveWorkersEntity> slist = list.stream().sorted().collect(Collectors.toList());
//            if(list.size() > 0) {
//                object.setWorkers(slist.get(0).getWorkers());
//                object.setRobotId(slist.get(0).getWorkers().getId());
//                object.setActive(true);
//            }
//            allocationService.createNewTask(object);
//            pendingTask = taskQueueService.getPendingTaskCount();
//        }
//    }

    @KafkaListener(topics = "test-local-allocation-topic", groupId = "com.allocation.dining:group")
    public void listenTasks(String message) throws Exception {
        System.out.println("Received Message in group: " + message);
        TaskEntity object = objectMapper.readValue(message, TaskEntity.class);
        List<ActiveWorkersEntity> list = allocationService.getAggWorkerList();
        List<ActiveWorkersEntity> slist = list.stream().sorted().collect(Collectors.toList());
        if(list.size() > 0) {
            object.setWorkers(slist.get(0).getWorkers());
            object.setRobotId(slist.get(0).getWorkers().getId());
            object.setActive(true);
        }
        allocationService.createNewTask(object);
    }
}
