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
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
public class ActiveWorkersEntity implements Serializable, Comparable  {

    @Id
    private String id;
    private WorkersEntity workers;
    private String activeTaskCount;

    ActiveWorkersEntity(Object workers, Object activeTaskCount) {
        this.workers = (WorkersEntity)workers;
        this.activeTaskCount = String.valueOf(activeTaskCount);
    }

    @Override
    public int compareTo(Object o) {
        ActiveWorkersEntity obj = (ActiveWorkersEntity)o;
        if (Integer.parseInt(this.activeTaskCount) > Integer.parseInt(obj.getActiveTaskCount())) {
            return 1;
        }
        return -1;
    }
}
