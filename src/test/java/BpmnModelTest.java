import com.voidforce.activiti.Application;
import org.activiti.bpmn.model.*;
import org.activiti.bpmn.model.Process;
import org.activiti.engine.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class BpmnModelTest {

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    @Test
    public void test() {
        repositoryService.createDeployment().name("Model From Bpmn").addBpmnModel("Model From Bpmn", this.generateBpmnModel()).deploy();
    }


    private BpmnModel generateBpmnModel() {
        BpmnModel bpmnModel = new BpmnModel();

        Process process = new Process();
        process.setId("BpmnModel timer");
        process.setName("First BpmnModel");

        StartEvent startEvent = new StartEvent();
        startEvent.setId("startEvent");
        process.addFlowElement(startEvent);

        UserTask userTask = new UserTask();
        userTask.setId("BpmnModel Task Id");
        userTask.setName("BpmnModel Task");

        process.addFlowElement(userTask);

        EndEvent endEvent = new EndEvent();
        endEvent.setId("endEvent");
        process.addFlowElement(endEvent);

        process.addFlowElement(new SequenceFlow("startEvent", "BpmnModel Task Id"));
        process.addFlowElement(new SequenceFlow("BpmnModel Task Id", "endEvent"));

        bpmnModel.addProcess(process);

        return bpmnModel;
    }
}
