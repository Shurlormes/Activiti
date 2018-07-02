import com.voidforce.activiti.Application;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.identity.User;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class CandidateByJUEL implements Serializable {

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private IdentityService identityService;


    public List<String> findNames() {
        List<String> names = new ArrayList<>();
        names.add("zhangsan");
        names.add("lisi");
        return  names;
    }

    @Test
    public void identity() {
        User zhangsan = identityService.newUser("zhangsan");
        zhangsan.setFirstName("zhangsan");

        User lisi = identityService.newUser("lisi");
        lisi.setFirstName("lisi");

        identityService.saveUser(zhangsan);
        identityService.saveUser(lisi);
    }

    @Test
    public void deploy() {
        Deployment deployment = repositoryService.createDeployment().name("候选人流程测试")
                .addClasspathResource("processes/JUELAuthorityTest.bpmn").deploy();

        Map<String, Object> variables = new HashMap<>();
        variables.put("CandidateByJUEL", new CandidateByJUEL());

        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                .deploymentId(deployment.getId()).singleResult();

        runtimeService.startProcessInstanceById(processDefinition.getId(), variables);
    }

}
