package com.felix.basic_projects.mini_market.controller;

import com.felix.basic_projects.mini_market.service.ActivityLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1")
public class ActivityLogController {

  @Autowired
  ActivityLogService logService;

}
