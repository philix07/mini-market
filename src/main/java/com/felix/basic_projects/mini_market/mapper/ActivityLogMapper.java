package com.felix.basic_projects.mini_market.mapper;

import com.felix.basic_projects.mini_market.model.dto.response.ActivityLogResponseDTO;
import com.felix.basic_projects.mini_market.model.entity.ActivityLog;
import org.springframework.stereotype.Service;

@Service
public class ActivityLogMapper {

  public ActivityLogResponseDTO mapEntityToResponseDTO(ActivityLog log) {
    return ActivityLogResponseDTO.builder()
      .id(log.getId())
      .userId(log.getUser().getId())
      .action(log.getAction())
      .createdAt(log.getCreatedAt())
      .resource(log.getResource().getValue())
      .detailsAfter(log.getDetailsAfter())
      .detailsBefore(log.getDetailsBefore())
      .build();
  }

}
