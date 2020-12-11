package id.co.javan.workshop.latihancamunda.service;

import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.ProcessEngines;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.runtime.ProcessInstanceWithVariables;
import org.camunda.bpm.engine.task.Task;
import org.springframework.stereotype.Service;

import java.util.List;
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

    public List<Task> getActiveTasks(String processDefKey, String taskDefKey) {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        List<Task> tasks = processEngine.getTaskService().createTaskQuery()
                .processDefinitionKey(processDefKey)
                .taskDefinitionKey(taskDefKey)
                .active().list();

        return tasks;
    }

    public void completeTask(String taskId, Map<String, Object> variable) {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        if(variable != null) {
            processEngine.getTaskService().complete(taskId, variable);
        } else {
            processEngine.getTaskService().complete(taskId);
        }
    }
}
