package com.felix.basic_projects.mini_market.service;

import com.felix.basic_projects.mini_market.exception.activity_log.ActivityLogNotFoundException;
import com.felix.basic_projects.mini_market.exception.user.UserNotFoundException;
import com.felix.basic_projects.mini_market.mapper.ActivityLogMapper;
import com.felix.basic_projects.mini_market.model.dto.request.CreateActivityLogRequestDTO;
import com.felix.basic_projects.mini_market.model.dto.response.ActivityLogResponseDTO;
import com.felix.basic_projects.mini_market.model.entity.ActivityLog;
import com.felix.basic_projects.mini_market.model.entity.User;
import com.felix.basic_projects.mini_market.model.entity.enums.ActivityLogResource;
import com.felix.basic_projects.mini_market.repository.ActivityLogRepository;
import com.felix.basic_projects.mini_market.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ActivityLogService {

  private final ActivityLogRepository logRepository;
  private final UserRepository userRepository;

  private final ActivityLogMapper activityLogMapper;

  // Constructor for dependency injection
  public ActivityLogService(
    ActivityLogRepository logRepository,
    UserRepository userRepository,
    ActivityLogMapper activityLogMapper
  ) {
    this.logRepository = logRepository;
    this.userRepository = userRepository;
    this.activityLogMapper = activityLogMapper;
  }

  public ActivityLogResponseDTO saveActivityLog(CreateActivityLogRequestDTO request) {
    User user = userRepository.findById(request.getUserId()).orElseThrow(
      () -> new UserNotFoundException("There is no user with id : " + request.getUserId())
    );

    ActivityLog log = ActivityLog.builder()
      .createdAt(LocalDateTime.now())
      .user(user)
      .action(request.getAction())
      .resource(ActivityLogResource.fromString(request.getResource()))
      .detailsBefore(request.getDetailsBefore())
      .detailsAfter(request.getDetailsAfter())
      .build();
    logRepository.save(log);

    return activityLogMapper.mapEntityToResponseDTO(log);
  }

  public List<ActivityLogResponseDTO> retrieveAllActivityLog() {
    List<ActivityLog> logs = logRepository.findAll();

    if (logs.isEmpty()) {
      throw new ActivityLogNotFoundException("There is no activity log in this application");
    }

    return logs.stream().map(activityLogMapper::mapEntityToResponseDTO).toList();
  }

  public List<ActivityLogResponseDTO> retrieveAllActivityLogByUserId(Long id) {
    List<ActivityLog> logs = logRepository.findAllByUserId(id);

    if (logs.isEmpty()) {
      throw new ActivityLogNotFoundException("There is no activity log for user with id : " + id);
    }

    return logs.stream().map(activityLogMapper::mapEntityToResponseDTO).toList();
  }

  public List<ActivityLogResponseDTO> retrieveAllActivityLogByDateRange(LocalDate startDate, LocalDate endDate) {
    LocalDateTime formattedStartDate = startDate.atStartOfDay();
    LocalDateTime formattedEndDate = endDate.atTime(23, 59, 59, 999_999_999);

    List<ActivityLog> logs = logRepository.findAllByDateRange(formattedStartDate, formattedEndDate);

    if (logs.isEmpty()) {
      throw new ActivityLogNotFoundException(
        "There is no activity log for date range between " + formattedStartDate + " and " + formattedEndDate
      );
    }

    return logs.stream().map(activityLogMapper::mapEntityToResponseDTO).toList();
  }

  public List<ActivityLogResponseDTO> retrieveAllActivityLogByResource(String resource) {
    List<ActivityLog> logs = logRepository.findAllByResource(ActivityLogResource.fromString(resource));

    if (logs.isEmpty()) {
      throw new ActivityLogNotFoundException(
        "There is no activity log for resource : " + resource
      );
    }

    return logs.stream().map(activityLogMapper::mapEntityToResponseDTO).toList();
  }

  public ActivityLogResponseDTO findActivityLogById(Long id) {
    return logRepository.findById(id).map(activityLogMapper::mapEntityToResponseDTO)
      .orElseThrow(
        () -> new ActivityLogNotFoundException("There is no activity log with id : " + id)
      );
  }

}
