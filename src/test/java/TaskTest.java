import com.voidforce.activiti.Application;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.Task;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class TaskTest {

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private IdentityService identityService;

    private final String MANAGEMENT_GROUP_ID = "management_group_id";

    private final String DEVELOPER_GROUP_ID = "developer_group_id";

    private final String ADMIN_USER_ID = "admin_user_id";

    private final String MICHAEL_USER_ID = "michael_user_id";

    @Test
    public void identity() {
        Group management =  identityService.newGroup(MANAGEMENT_GROUP_ID);
        management.setName("management");

        Group developer =  identityService.newGroup(DEVELOPER_GROUP_ID);
        developer.setName("developer");

        identityService.saveGroup(management);
        identityService.saveGroup(developer);

        User admin = identityService.newUser(ADMIN_USER_ID);
        admin.setFirstName("admin");

        User michael = identityService.newUser(MICHAEL_USER_ID);
        michael.setFirstName("michael");

        identityService.saveUser(admin);
        identityService.saveUser(michael);
    }

    @Test
    public void deploy() {
        Deployment deployment = repositoryService.createDeployment().name("候选人流程测试")
                .addClasspathResource("processes/candidateTest.bpmn").deploy();

        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                .deploymentId(deployment.getId()).singleResult();

        runtimeService.startProcessInstanceById(processDefinition.getId());
    }


    @Test
    public void candidate() {
        List<Task> taskList = taskService.createTaskQuery().taskCandidateUser(ADMIN_USER_ID).list();
        System.out.println("admin用户的任务数：" + taskList.size());
        System.out.println(taskList.toString());
        System.out.println("===========================");

        taskList = taskService.createTaskQuery().taskCandidateUser(MICHAEL_USER_ID).list();
        System.out.println("michael用户的任务数：" + taskList.size());
        System.out.println(taskList.toString());
        System.out.println("===========================");

        taskList = taskService.createTaskQuery().taskCandidateGroup(MANAGEMENT_GROUP_ID).list();
        System.out.println("management组的任务数：" + taskList.size());
        System.out.println(taskList.toString());
        System.out.println("===========================");

        taskList = taskService.createTaskQuery().taskCandidateGroup(DEVELOPER_GROUP_ID).list();
        System.out.println("developer组的任务数：" + taskList.size());
        System.out.println(taskList.toString());
    }

    @Test
    public void query() {
        List<Task> taskList = taskService.createTaskQuery().taskOwner("25").list();
        for (Task task : taskList) {
            System.out.println(task.getProcessInstanceId());
        }
    }

    @Test
    public void start() {
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId("id-eeb0e5ad-0bde-4bf4-9844-64568a112f64:1:122504").singleResult();
        System.out.println(processDefinition);
        runtimeService.startProcessInstanceById("id-eeb0e5ad-0bde-4bf4-9844-64568a112f64:1:122504");
    }

}
