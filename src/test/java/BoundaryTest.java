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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class BoundaryTest {

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    @Test
    public void signalTest() {
        Deployment deployment = repositoryService.createDeployment().name("signalTest").addClasspathResource("processes/signalBoundaryTest.bpmn").deploy();

        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().deploymentId(deployment.getId()).singleResult();

        ProcessInstance processInstance = runtimeService.startProcessInstanceById(processDefinition.getId());

        Task task = taskService.createTaskQuery().processInstanceId(processInstance.getId()).singleResult();

        System.out.println("当前的任务是：" + task.getName());

        taskService.complete(task.getId());

        task = taskService.createTaskQuery().processInstanceId(processInstance.getId()).singleResult();

        System.out.println("当前的任务是：" + task.getName());


        runtimeService.signalEventReceived("auditSignalName ");

        task = taskService.createTaskQuery().processInstanceId(processInstance.getId()).singleResult();

        System.out.println("当前的任务是：" + task.getName());
    }

    @Test
    public void timerTest() throws Exception {
        Deployment deployment = repositoryService.createDeployment().name("timerTest").addClasspathResource("processes/timerBoundaryTest.bpmn").deploy();

        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().deploymentId(deployment.getId()).singleResult();

        ProcessInstance processInstance = runtimeService.startProcessInstanceById(processDefinition.getId());

        Task task = taskService.createTaskQuery().processInstanceId(processInstance.getId()).singleResult();
        System.out.println("当前的任务是：" + task.getName());

        Thread.sleep(15000);

        task = taskService.createTaskQuery().processInstanceId(processInstance.getId()).singleResult();
        System.out.println("当前的任务是：" + task.getName());
    }

}
