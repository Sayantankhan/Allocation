package com.allocation.dining.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="t_orders")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderEntity {
    @Id
    private String id;
    @Column(name="table_id")
    private String tableId;
    @Column(name="is_active")
    private boolean isActive;
    @Column(name="created_at")
    private String createdAt;
    @Column(name="updated_at")
    private String updatedAt;
}
