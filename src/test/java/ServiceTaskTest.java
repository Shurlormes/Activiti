import com.voidforce.activiti.Application;
import com.voidforce.activiti.service.userInfo.UserInfoService;
import com.voidforce.activiti.task.ServiceTask;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class ServiceTaskTest {

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private RuntimeService runtimeService;

    @Test
    public void service() {

        Map<String, Object> variables = new HashMap<>();
        variables.put("serviceTask", new ServiceTask());

        Deployment deployment = repositoryService.createDeployment().name("任务服务测试")
                .addClasspathResource("processes/serviceTaskTest.bpmn").deploy();

        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().deploymentId(deployment.getId()).singleResult();

        ProcessInstance processInstance = runtimeService.startProcessInstanceById(processDefinition.getId(), variables);


        System.out.println("参数是:" + runtimeService.getVariable(processInstance.getId(), "name"));
    }

}
