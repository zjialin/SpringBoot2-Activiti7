package com.zjialin.workflow.activiti;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Model;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@Scope("prototype")
@RequestMapping("/activiti")
@Slf4j
public class ActivitiCreateRestResource {
    /**
     * 创建模型
     */
    @RequestMapping("create")
    public void create(HttpServletRequest request, HttpServletResponse response) {
        try {
            ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
            RepositoryService repositoryService = processEngine.getRepositoryService();
            ObjectMapper objectMapper = new ObjectMapper();
            ObjectNode editorNode = objectMapper.createObjectNode();
            editorNode.put("id", "canvas");
            editorNode.put("resourceId", "canvas");
            ObjectNode stencilSetNode = objectMapper.createObjectNode();
            stencilSetNode.put("namespace", "http://b3mn.org/stencilset/bpmn2.0#");
            editorNode.set("stencilset", stencilSetNode);
            Model modelData = repositoryService.newModel();
            ObjectNode modelObjectNode = objectMapper.createObjectNode();
            modelObjectNode.put(ModelDataJsonConstants.MODEL_NAME, "新建流程");
            modelObjectNode.put(ModelDataJsonConstants.MODEL_REVISION, 1);
            String description = "请输入流程描述信息~";
            modelObjectNode.put(ModelDataJsonConstants.MODEL_DESCRIPTION, description);
            modelData.setMetaInfo(modelObjectNode.toString());
            //保存模型
            repositoryService.saveModel(modelData);
            repositoryService.addModelEditorSource(modelData.getId(), editorNode.toString().getBytes("utf-8"));
            // 编辑流程模型时,只需要直接跳转此url并传递上modelId即可
            response.sendRedirect(request.getContextPath() + "/modeler.html?modelId=" + modelData.getId());
        } catch (Exception e) {
            log.error("创建模型失败：");
        }
    }
}
