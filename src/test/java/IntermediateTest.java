import com.voidforce.activiti.Application;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class IntermediateTest {

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;


    @Test
    public void timer() throws Exception {
        Deployment deployment = repositoryService.createDeployment().name("点外卖流程").addClasspathResource("processes/timerIntermediateTest.bpmn").deploy();

        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().deploymentId(deployment.getId()).singleResult();

        ProcessInstance processInstance = runtimeService.startProcessInstanceById(processDefinition.getId());

        Task task = taskService.createTaskQuery().processInstanceId(processInstance.getId()).singleResult();

        System.out.println("当前的任务名称是：" + task.getName());

        taskService.complete(task.getId());

        Thread.sleep(15000);

        task = taskService.createTaskQuery().processInstanceId(processInstance.getId()).singleResult();

        System.out.println("当前的任务名称是：" + task.getName());
    }

    @Test
    public void signal() throws Exception {
        Deployment deployment = repositoryService.createDeployment().name("用户支付流程").addClasspathResource("processes/signalIntermediateTest.bpmn").deploy();

        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().deploymentId(deployment.getId()).singleResult();

        ProcessInstance processInstance = runtimeService.startProcessInstanceById(processDefinition.getId());

        List<Task> taskList = taskService.createTaskQuery().processInstanceId(processInstance.getId()).orderByTaskId().desc().list();

        taskList.forEach(i -> {
            System.out.println("完成的任务是:" + i);
            taskService.complete(i.getId());
        });

        List<Execution> executionList = runtimeService.createExecutionQuery().processInstanceId(processInstance.getId()).list();
        executionList.forEach(i -> System.out.println(i));

        taskList = taskService.createTaskQuery().processInstanceId(processInstance.getId()).list();
        taskList.forEach(i -> System.out.println(i));
    }

    @Test
    public void compensation() {
        Deployment deployment = repositoryService.createDeployment().name("银行转账回退流程")
                .addClasspathResource("processes/compensationIntermediateTest.bpmn").deploy();

        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().deploymentId(deployment.getId()).singleResult();

        ProcessInstance processInstance = runtimeService.startProcessInstanceById(processDefinition.getId());
    }

}
