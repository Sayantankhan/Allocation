package com.allocation.dining.repository;

import com.allocation.dining.entity.ActiveWorkersEntity;
import com.allocation.dining.entity.TaskEntity;
import com.allocation.dining.entity.WorkersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TasksRepository extends JpaRepository<TaskEntity, String> {

    List<TaskEntity> findByIsActive(boolean isactive);

    List<TaskEntity> findByOrderId(String orderId);

    @Query("SELECT new com.allocation.dining.entity.ActiveWorkersEntity(workers, COUNT(workers) AS activeTaskCount) FROM TaskEntity tasks RIGHT JOIN tasks.workers workers" +
            " WHERE tasks.isActive = true and workers.isActive = true GROUP BY workers ORDER BY activeTaskCount")
    List findAllActiveTaskWorkers();
}
