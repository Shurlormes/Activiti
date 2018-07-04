import com.voidforce.activiti.Application;
import org.activiti.bpmn.model.*;
import org.activiti.bpmn.model.Process;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
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
        Deployment deployment = repositoryService.createDeployment()
            .key("Model From Bpmn Key").name("Model From Bpmn")
            .addBpmnModel("dynamic-model.bpmn", this.generateBpmnModel()).deploy();

        System.out.println(deployment.getId());

        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
            .deploymentId(deployment.getId()).singleResult();

        System.out.println(processDefinition);
        //runtimeService.startProcessInstanceById(processDefinition.getId());
    }

    @Test
    public void test2() {
        runtimeService.startProcessInstanceById("BpmnModelId:1:67503");
    }


    private BpmnModel generateBpmnModel() {
        BpmnModel bpmnModel = new BpmnModel();

        Process process = new Process();
        process.setId("BpmnModelId");
        process.setName("First BpmnModel");

        StartEvent startEvent = new StartEvent();
        startEvent.setId("startEvent");
        process.addFlowElement(startEvent);

        UserTask userTask = new UserTask();
        userTask.setId("BpmnModelTaskId");
        userTask.setName("BpmnModel Task");

        process.addFlowElement(userTask);

        EndEvent endEvent = new EndEvent();
        endEvent.setId("endEvent");
        process.addFlowElement(endEvent);

        process.addFlowElement(new SequenceFlow("startEvent", "BpmnModelTaskId"));
        process.addFlowElement(new SequenceFlow("BpmnModelTaskId", "endEvent"));


        bpmnModel.addProcess(process);


        return bpmnModel;
    }


    @Test
    public void testDynamicDeploy() throws Exception {
        // 1. Build up the model from scratch
        BpmnModel model = new BpmnModel();
        Process process = new Process();
        model.addProcess(process);
        process.setId("my-process");

        process.addFlowElement(createStartEvent());
        process.addFlowElement(createUserTask("task1", "First task", "fred"));
        process.addFlowElement(createUserTask("task2", "Second task", "john"));
        process.addFlowElement(createEndEvent());

        process.addFlowElement(createSequenceFlow("start", "task1"));
        process.addFlowElement(createSequenceFlow("task1", "task2"));
        process.addFlowElement(createSequenceFlow("task2", "end"));

        // 2. Generate graphical information
        //new BpmnAutoLayout(model).execute();

        // 3. Deploy the process to the engine
        Deployment deployment = repositoryService.createDeployment()
            .addBpmnModel("dynamic-model.bpmn", model).name("Dynamic process deployment")
            .deploy();

        // 4. Start a process instance
        //ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("my-process");

        // 5. Check if task is available
//        List<Task> tasks = taskService.createTaskQuery()
//            .processInstanceId(processInstance.getId()).list();
//
//        Assert.assertEquals(1, tasks.size());
//        Assert.assertEquals("First task", tasks.get(0).getName());
//        Assert.assertEquals("fred", tasks.get(0).getAssignee());

        // 6. Save process diagram to a file
//        InputStream processDiagram = repositoryService.getProcessDiagram(processInstance.getProcessDefinitionId());
//        FileUtils.copyInputStreamToFile(processDiagram, new File("target/diagram.png"));

        // 7. Save resulting BPMN xml to a file
//        InputStream processBpmn = activitiRule.getRepositoryService()
//            .getResourceAsStream(deployment.getId(), "dynamic-model.bpmn");
//        FileUtils.copyInputStreamToFile(processBpmn,
//            new File("target/process.bpmn20.xml"));
    }

    public UserTask createUserTask(String id, String name, String assignee) {
        UserTask userTask = new UserTask();
        userTask.setName(name);
        userTask.setId(id);
        userTask.setAssignee(assignee);
        return userTask;
    }

    public SequenceFlow createSequenceFlow(String from, String to) {
        SequenceFlow flow = new SequenceFlow();
        flow.setSourceRef(from);
        flow.setTargetRef(to);
        return flow;
    }

    public StartEvent createStartEvent() {
        StartEvent startEvent = new StartEvent();
        startEvent.setId("start");
        return startEvent;
    }

    public EndEvent createEndEvent() {
        EndEvent endEvent = new EndEvent();
        endEvent.setId("end");
        return endEvent;
    }
}
