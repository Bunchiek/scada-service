package com.example.scada_service.repository;

import com.example.scada_service.model.OpcUaData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OpcUaDataRepository extends JpaRepository<OpcUaData, Long> {
}
