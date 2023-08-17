package com.allocation.dining.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="t_tasks")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TaskEntity implements Serializable {
    @Id
    private String id;
    @Column(name="robot_id")
    private String robotId;
    @Column(name="order_id")
    private String orderId;
    @Column(name="description")
    private String description;
    @Column(name="is_active")
    private boolean isActive;
    @Column(name="created_at")
    private String createdAt;
    @Column(name="updated_at")
    private String updatedAt;
    @OneToOne
    @JoinColumn(name="robot_id", insertable=false, updatable=false)
    private WorkersEntity workers;
}
