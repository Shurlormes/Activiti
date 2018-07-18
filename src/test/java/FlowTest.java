import com.voidforce.activiti.Application;
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
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class FlowTest {

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    @Test
    public void exclusive() {
        Deployment deployment = repositoryService.createDeployment().name("单一网关测试")
                .addClasspathResource("processes/gateweyTest.bpmn").deploy();

        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                .deploymentId(deployment.getId()).singleResult();

        ProcessInstance processInstance = runtimeService.startProcessInstanceById(processDefinition.getId());

        Task task = taskService.createTaskQuery().processInstanceId(processInstance.getId()).singleResult();

        System.out.println("当前任务是" + task);

        Map<String, Object> variables = new HashMap<>();
        //variables.put("day", 5);
        variables.put("day", 2);
        taskService.complete(task.getId(), variables);


        task = taskService.createTaskQuery().processInstanceId(processInstance.getId()).singleResult();

        System.out.println("当前任务是" + task);
    }

    @Test
    public void exclusive2() {
        Deployment deployment = repositoryService.createDeployment().name("exclusive test")
            .addClasspathResource("processes/exclusiveTest.bpmn").deploy();

        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
            .deploymentId(deployment.getId()).singleResult();

        ProcessInstance processInstance = runtimeService.startProcessInstanceById(processDefinition.getId());

        Task task = taskService.createTaskQuery().processInstanceId(processInstance.getId()).singleResult();

        System.out.println("当前任务是" + task);

        Map<String, Object> variables = new HashMap<>();
        //variables.put("day", 5);
        variables.put("condition", false);
        taskService.complete(task.getId(), variables);


        task = taskService.createTaskQuery().processInstanceId(processInstance.getId()).singleResult();

        System.out.println("当前任务是" + task);
    }

    @Test
    public void exclusive3() {
        Deployment deployment = repositoryService.createDeployment().name("process test")
            .addClasspathResource("processes/process.bpmn").deploy();

        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
            .deploymentId(deployment.getId()).singleResult();

        ProcessInstance processInstance = runtimeService.startProcessInstanceById(processDefinition.getId());

        Task task = taskService.createTaskQuery().processInstanceId(processInstance.getId()).singleResult();

        System.out.println("当前任务是" + task);

        Map<String, Object> variables = new HashMap<>();
        taskService.addComment(task.getId(), processInstance.getId(), "备注备备注");
        taskService.complete(task.getId(), variables);


        task = taskService.createTaskQuery().processInstanceId(processInstance.getId()).singleResult();

        System.out.println("当前任务是" + task);

        /*taskService.complete(task.getId(), variables);

        task = taskService.createTaskQuery().processInstanceId(processInstance.getId()).singleResult();

        System.out.println("当前任务是" + task);*/
    }

    @Test
    public void parallel() {
        Deployment deployment = repositoryService.createDeployment().name("并行网关测试")
                .addClasspathResource("processes/parallelTest.bpmn").deploy();

        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                .deploymentId(deployment.getId()).singleResult();

        ProcessInstance processInstance = runtimeService.startProcessInstanceById(processDefinition.getId());


        Task task = taskService.createTaskQuery().processInstanceId(processInstance.getId()).singleResult();

        System.out.println("当前任务是" + task);
        taskService.complete(task.getId());

        List<Task> taskList = taskService.createTaskQuery().processInstanceId(processInstance.getId()).list();


        for (Task a : taskList) {
            System.out.println(a);
            taskService.complete(a.getId());
        }


        taskList = taskService.createTaskQuery().processInstanceId(processInstance.getId()).list();

        for (Task a : taskList) {
            System.out.println(a);
        }
    }

    @Test
    public void event() throws Exception {
        Deployment deployment = repositoryService.createDeployment().name("事件网关测试")
                .addClasspathResource("processes/eventGatewayTest.bpmn").deploy();

        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                .deploymentId(deployment.getId()).singleResult();

        ProcessInstance processInstance = runtimeService.startProcessInstanceById(processDefinition.getId());

        Task task = taskService.createTaskQuery().processInstanceId(processInstance.getId()).singleResult();

        System.out.println(task);

        runtimeService.signalEventReceived("goSignal");

        Thread.sleep(10000L);

        task = taskService.createTaskQuery().processInstanceId(processInstance.getId()).singleResult();

        System.out.println(task);

    }
}
