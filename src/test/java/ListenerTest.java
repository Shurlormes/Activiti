import com.voidforce.activiti.Application;
import com.voidforce.activiti.listener.UserTaskListener;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class ListenerTest {

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    @Test
    public void listener() {
        Deployment deployment = repositoryService.createDeployment().name("用户任务监听器测试").addClasspathResource("processes/listenerTest.bpmn").deploy();

        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().deploymentId(deployment.getId()).singleResult();

        Map<String, Object> variables = new HashMap<>();
        variables.put("myListener", new UserTaskListener());

        ProcessInstance processInstance = runtimeService.startProcessInstanceById(processDefinition.getId(), variables);

        Task task = taskService.createTaskQuery().processInstanceId(processInstance.getId()).singleResult();

        taskService.setAssignee(task.getId(), "zsyh");

        taskService.complete(task.getId());
        System.out.println(task.getProcessVariables().get("variableTest"));
        System.out.println(task.getTaskLocalVariables().get("variableTest"));


        task = taskService.createTaskQuery().processInstanceId(processInstance.getId()).singleResult();
        System.out.println(task.getProcessVariables().get("variableTest"));
        System.out.println(task.getTaskLocalVariables().get("variableTest"));
    }

    @Test
    public void classListenerTest() {
        Deployment deployment = repositoryService.createDeployment().name("Class监听器测试")
            .addClasspathResource("processes/listenerClassTest.bpmn").deploy();

        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
            .deploymentId(deployment.getId()).singleResult();

        Map<String, Object> variables = new HashMap<>();
        variables.put("dynamicTestValue", "动态数据测试");
        runtimeService.startProcessInstanceById(processDefinition.getId(), variables);

    }

}