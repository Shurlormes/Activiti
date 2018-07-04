import com.voidforce.activiti.Application;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class SimpleTest {

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    @Test
    public void simple() {

        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("simpleTest");

        Task task = taskService.createTaskQuery().processInstanceId(processInstance.getId()).singleResult();

        System.out.println(task);

    }

}
