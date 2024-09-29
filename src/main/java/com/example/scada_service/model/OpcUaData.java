package com.example.scada_service.model;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "opcua_data")
public class OpcUaData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tag_name")
    private String tagName;

    @Column(name = "value")
    private Long value;  // Изменяем тип на Long

    @Column(name = "timestamp")
    private LocalDateTime timestamp;

    public OpcUaData(String tagName, Long value, LocalDateTime timestamp) {
        this.tagName = tagName;
        this.value = value;
        this.timestamp = timestamp;
    }
}
