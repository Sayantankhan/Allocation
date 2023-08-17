package com.allocation.dining.repository;

import com.allocation.dining.entity.TaskEntity;
import com.allocation.dining.entity.WorkersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface RobotsRepository extends JpaRepository<WorkersEntity, String> {

    @Query("SELECT workers FROM WorkersEntity workers WHERE workers.isActive = true")
    List<WorkersEntity> findAllActiveWorkers();
}
