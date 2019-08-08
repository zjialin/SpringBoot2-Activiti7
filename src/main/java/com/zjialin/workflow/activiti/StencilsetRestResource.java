package com.zjialin.workflow.activiti;

import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.ActivitiException;
import org.apache.commons.io.IOUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.InputStream;

@RestController
@Slf4j
public class StencilsetRestResource {
    @GetMapping(value = {"/service/editor/stencilset"}, produces = {"application/json;charset=utf-8"})
    @ResponseBody
    public String getStencilset() {
        log.info("StencilsetRestResource.getStencilset-----------");
        // 文件位置需要跟stencilset.json文件放置的路径匹配,否则进入到在线编辑器页面会是一片空白,没有菜单等显示信息
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("activiti/stencilset.json");
        try {
            return IOUtils.toString(inputStream, "utf-8");
        } catch (Exception e) {
            throw new ActivitiException("Error while loading stencil set", e);
        }
    }
}
