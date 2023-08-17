package com.allocation.dining.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.io.Serializable;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "t_robots")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
public class WorkersEntity implements Serializable  {

    @Id
    private String id;
    @Column(name = "name")
    private String name;
    @Column(name = "is_active")
    private boolean isActive;
    @Column(name = "created_at")
    private String createdAt;
}
