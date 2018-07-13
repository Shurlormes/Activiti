import com.voidforce.activiti.Application;
import org.activiti.engine.HistoryService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class HistoryTest {

	@Autowired
	private HistoryService historyService;

	@Test
	public void query() {

		List<HistoricProcessInstance> historicProcessInstances = historyService.createHistoricProcessInstanceQuery().startedBy("jone").list();
		for (HistoricProcessInstance historicProcessInstance : historicProcessInstances) {
			System.out.println(historicProcessInstance);
		}



		List<HistoricTaskInstance> list = historyService.createHistoricTaskInstanceQuery().processInstanceId("90001").list();

		for (HistoricTaskInstance historicTaskInstance : list) {
			System.out.println(historicTaskInstance.getName());
			System.out.println(historicTaskInstance.getAssignee());
			System.out.println(historicTaskInstance.getStartTime());
			System.out.println(historicTaskInstance.getEndTime());
		}
	}
}