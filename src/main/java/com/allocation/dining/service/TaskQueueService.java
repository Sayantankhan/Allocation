package com.allocation.dining.service;

import com.allocation.dining.model.Task;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.Queue;

// @Service
public class TaskQueueService {

    private Queue<Task> taskQueue;

    TaskQueueService(){
        taskQueue =  new LinkedList<Task>();
    }

    @Transactional
    public void addToTaskPool(Task task) {
        taskQueue.add(task);
    }

    @Transactional
    public Task getNextTask() {
        return taskQueue.poll();
    }

    public int getPendingTaskCount() {return taskQueue.size();}

}
