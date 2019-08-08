package com.zjialin.workflow.controller;

import com.zjialin.workflow.utils.RestMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.impl.util.CollectionUtil;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.runtime.ProcessInstanceQuery;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zjialin<br>
 * @version 1.0<br>
 * @createDate 2019/08/30 10:31 <br>
 * @Description <p> 启动流程实例 </p>
 */

@RestController
@Api(tags = "启动流程实例")
@Slf4j
public class ProcessStartController extends BaseController {


    @PostMapping(path = "start")
    @ApiOperation(value = "根据流程key启动流程", notes = "每一个流程有对应的一个key这个是某一个流程内固定的写在bpmn内的")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "processKey", value = "流程key", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "startUserKey", value = "启动流程的用户", dataType = "String", paramType = "query")
    })
    public RestMessage start(@RequestParam("processKey") String processKey, @RequestParam("startUserKey") String startUserKey) {
        Map<String, Object> variables = new HashMap<>();
        variables.put("startUserKey", startUserKey);
        RestMessage restMessage = new RestMessage();
        ProcessInstance instance = null;
        try {
            instance = runtimeService.startProcessInstanceByKey(processKey, variables);
        } catch (Exception e) {
            restMessage = RestMessage.fail("启动失败", e.getMessage());
            log.error("根据流程key启动流程,异常:{}", e);
        }

        if (instance != null) {
            Map<String, String> result = new HashMap<>();
            // 流程实例ID
            result.put("processId", instance.getId());
            // 流程定义ID
            result.put("processDefinitionKey", instance.getProcessDefinitionId());
            restMessage = RestMessage.success("启动成功", result);
        }
        return restMessage;
    }


    @PostMapping(path = "searchByKey")
    @ApiOperation(value = "根据流程key查询流程实例", notes = "查询流程实例")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "processDefinitionKey", value = "流程key", dataType = "String", paramType = "query"),
    })
    public RestMessage searchProcessInstance(@RequestParam("processDefinitionKey") String processDefinitionKey) {
        RestMessage restMessage = new RestMessage();
        List<ProcessInstance> runningList = new ArrayList<>();
        try {
            ProcessInstanceQuery processInstanceQuery = runtimeService.createProcessInstanceQuery();
            runningList = processInstanceQuery.processDefinitionKey(processDefinitionKey).list();
        } catch (Exception e) {
            restMessage = RestMessage.fail("查询失败", e.getMessage());
            log.error("根据流程key查询流程实例,异常:{}", e);
        }

        if (CollectionUtil.isNotEmpty(runningList)) {
            List<Map<String, String>> resultList = new ArrayList<>();
            runningList.forEach(s -> {
                Map<String, String> resultMap = new HashMap<>();
                // 流程实例ID
                resultMap.put("processId", s.getId());
                // 流程定义ID
                resultMap.put("processDefinitionKey", s.getProcessDefinitionId());
                resultList.add(resultMap);
            });
            restMessage = RestMessage.success("查询成功", resultList);
        }
        return restMessage;
    }


    @PostMapping(path = "searchById")
    @ApiOperation(value = "根据流程ID查询流程实例", notes = "查询流程实例")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "processId", value = "流程实例ID", dataType = "String", paramType = "query"),
    })
    public RestMessage searchByID(@RequestParam("processId") String processId) {
        RestMessage restMessage = new RestMessage();
        ProcessInstance pi = null;
        try {
            pi = runtimeService.createProcessInstanceQuery().processInstanceId(processId).singleResult();
        } catch (Exception e) {
            restMessage = RestMessage.fail("查询失败", e.getMessage());
            log.error("根据流程ID查询流程实例,异常:{}", e);
        }

        if (pi != null) {
            Map<String, String> resultMap = new HashMap<>(2);
            // 流程实例ID
            resultMap.put("processID", pi.getId());
            // 流程定义ID
            resultMap.put("processDefinitionKey", pi.getProcessDefinitionId());
            restMessage = RestMessage.success("查询成功", resultMap);
        }
        return restMessage;
    }


    @PostMapping(path = "deleteProcessInstanceByID")
    @ApiOperation(value = "根据流程实例ID删除流程实例", notes = "根据流程实例ID删除流程实例")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "processId", value = "流程实例ID", dataType = "String", paramType = "query"),
    })
    public RestMessage deleteProcessInstanceByID(@RequestParam("processId") String processId) {
        RestMessage restMessage = new RestMessage();
        try {
            runtimeService.deleteProcessInstance(processId, "删除" + processId);
            restMessage = RestMessage.success("删除成功", "");
        } catch (Exception e) {
            restMessage = RestMessage.fail("删除失败", e.getMessage());
            log.error("根据流程实例ID删除流程实例,异常:{}", e);
        }
        return restMessage;
    }


    @PostMapping(path = "deleteProcessInstanceByKey")
    @ApiOperation(value = "根据流程实例key删除流程实例", notes = "根据流程实例key删除流程实例")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "processDefinitionKey", value = "流程实例Key", dataType = "String", paramType = "query"),
    })
    public RestMessage deleteProcessInstanceByKey(@RequestParam("processDefinitionKey") String processDefinitionKey) {
        RestMessage restMessage = new RestMessage();
        List<ProcessInstance> runningList = new ArrayList<>();
        try {
            ProcessInstanceQuery processInstanceQuery = runtimeService.createProcessInstanceQuery();
            runningList = processInstanceQuery.processDefinitionKey(processDefinitionKey).list();
        } catch (Exception e) {
            restMessage = RestMessage.fail("删除失败", e.getMessage());
            log.error("根据流程实例key删除流程实例,异常:{}", e);
        }

        if (CollectionUtil.isNotEmpty(runningList)) {
            List<Map<String, String>> resultList = new ArrayList<>();
            runningList.forEach(s -> runtimeService.deleteProcessInstance(s.getId(), "删除"));
            restMessage = RestMessage.success("删除成功", resultList);
        }
        return restMessage;
    }

}