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

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class SubProcessTest {

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;


    @Test
    public void error() {
        Deployment deployment = repositoryService.createDeployment().name("错误子流程测试")
                .addClasspathResource("processes/subProcessTest.bpmn").deploy();


        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                .deploymentId(deployment.getId()).singleResult();

        ProcessInstance processInstance = runtimeService.startProcessInstanceById(processDefinition.getId());

        Task task = taskService.createTaskQuery().processInstanceId(processInstance.getId()).singleResult();

        System.out.println(task);
    }

    @Test
    public void call() {
        Deployment deployment = repositoryService.createDeployment().name("调用式子流程")
                .addClasspathResource("processes/callActivitiTest.bpmn").deploy();


        Deployment deployment2 = repositoryService.createDeployment().name("审批流程")
                .addClasspathResource("processes/approvalFlowTest.bpmn").deploy();

        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                .deploymentId(deployment.getId()).singleResult();

        ProcessInstance processInstance = runtimeService.startProcessInstanceById(processDefinition.getId());

        // 填写申请
        Task task = taskService.createTaskQuery().processInstanceId(processInstance.getId()).singleResult();

        System.out.println(task);

        taskService.complete(task.getId());

        // 查询子执行流
        ProcessInstance subProcessInstance = runtimeService.createProcessInstanceQuery()
                .superProcessInstanceId(processInstance.getId()).singleResult();
        // 调用审批流程，人事审批
        task = taskService.createTaskQuery().processInstanceId(subProcessInstance.getId()).singleResult();

        System.out.println(task);

        taskService.complete(task.getId());

        // 调用审批流程，总监审批
        task = taskService.createTaskQuery().processInstanceId(subProcessInstance.getId()).singleResult();

        System.out.println(task);
    }

    @Test
    public void event() {
        Deployment deployment = repositoryService.createDeployment().name("事件子流程测试")
                .addClasspathResource("processes/eventSubProcessTest.bpmn").deploy();

        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                .deploymentId(deployment.getId()).singleResult();

        ProcessInstance processInstance = runtimeService.startProcessInstanceById(processDefinition.getId());

        Task task = taskService.createTaskQuery().processInstanceId(processInstance.getId()).singleResult();


        System.out.println(task);
    }

    @Test
    public void cancel() {
        Deployment deployment = repositoryService.createDeployment().name("取消子流程测试")
                .addClasspathResource("processes/cancelSubTest.bpmn").deploy();


        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                .deploymentId(deployment.getId()).singleResult();

        ProcessInstance processInstance = runtimeService.startProcessInstanceById(processDefinition.getId());


        Task task = taskService.createTaskQuery().processInstanceId(processInstance.getId()).singleResult();

        System.out.println(task);

        taskService.complete(task.getId());

        task = taskService.createTaskQuery().processInstanceId(processInstance.getId()).singleResult();

        System.out.println(task);
    }
}
