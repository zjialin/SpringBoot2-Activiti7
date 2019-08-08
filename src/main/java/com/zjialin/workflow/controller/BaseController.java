package com.zjialin.workflow.controller;

import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class BaseController {
    @Autowired
    public TaskService taskService;
    @Autowired
    public RuntimeService runtimeService;
    @Autowired
    public HistoryService historyService;
    @Autowired
    public RepositoryService repositoryService;

}
