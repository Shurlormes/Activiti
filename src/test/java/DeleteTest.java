import com.voidforce.activiti.Application;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class})
public class DeleteTest {

    @Autowired
    private RepositoryService repositoryService;

    @Test
    public void delete () {
        List<Deployment> deploymentList = repositoryService.createDeploymentQuery().list();

        for (Deployment deployment : deploymentList) {
            repositoryService.deleteDeployment(deployment.getId(), true);
        }

    }
}
