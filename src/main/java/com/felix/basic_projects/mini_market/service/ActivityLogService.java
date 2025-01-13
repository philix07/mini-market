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

  @Autowired
  private ActivityLogRepository logRepository;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  ActivityLogMapper activityLogMapper;

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

  public List<ActivityLog> retrieveAllActivityLog() {
    List<ActivityLog> logs = logRepository.findAll();

    if (logs.isEmpty()) {
      throw new ActivityLogNotFoundException("There is no activity log in this application");
    }

    return logs;
  }

  public List<ActivityLog> retrieveAllActivityLogByUserId(Long id) {
    List<ActivityLog> logs = logRepository.findAllByUserId(id);

    if (logs.isEmpty()) {
      throw new ActivityLogNotFoundException("There is no activity log for user with id : " + id);
    }

    return logs;
  }

  public List<ActivityLog> retrieveAllActivityLogByDateRange(LocalDate startDate, LocalDate endDate) {
    LocalDateTime formattedStartDate = startDate.atStartOfDay();
    LocalDateTime formattedEndDate = endDate.atTime(23, 59, 59, 999_999_999);

    List<ActivityLog> logs = logRepository.findAllByDateRange(formattedStartDate, formattedEndDate);

    if (logs.isEmpty()) {
      throw new ActivityLogNotFoundException(
        "There is no activity log for date range between " + formattedStartDate + " and " + formattedEndDate
      );
    }

    return logs;
  }

  public List<ActivityLog> retrieveAllActivityLogByResource(String resource) {
    List<ActivityLog> logs = logRepository.findAllByResource(ActivityLogResource.valueOf(resource));


    if (logs.isEmpty()) {
      throw new ActivityLogNotFoundException(
        "There is no activity log for resource : " + resource
      );
    }

    return logs;
  }

  public ActivityLog findActivityLogById(Long id) {
    return logRepository.findById(id).orElseThrow(
      () -> new ActivityLogNotFoundException("There is no activity log with id : " + id)
    );
  }

}
