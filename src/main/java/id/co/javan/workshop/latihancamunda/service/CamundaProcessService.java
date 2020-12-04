package id.co.javan.workshop.latihancamunda.service;

import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.ProcessEngines;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.runtime.ProcessInstanceWithVariables;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class CamundaProcessService {
    public ProcessInstance startProcess(String processDefinitionKey, String businessKey, Map<String, Object> variables) {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        RuntimeService runtimeService = processEngine.getRuntimeService();

        ProcessInstanceWithVariables instance = runtimeService.createProcessInstanceByKey(processDefinitionKey)
                .setVariables(variables)
                .businessKey(businessKey)
                .executeWithVariablesInReturn();

        return instance;
    }
}
