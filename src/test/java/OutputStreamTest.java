import com.voidforce.activiti.Application;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.repository.ProcessDefinition;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class OutputStreamTest {

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private HistoryService historyService;

    @Test
    public void outputQuery() throws IOException {
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                .deploymentId("72501").singleResult();


        InputStream is = repositoryService.getProcessDiagram(processDefinition.getId());
        readStreamPng(is, "process.png");

        is = repositoryService.getProcessModel(processDefinition.getId());
        readStreamXml(is, "process.bpmn");
    }

    private void readStreamXml(InputStream is, String fileName) throws IOException {
        int count = is.available();
        byte[] contents = new byte[count];
        is.read(contents);
        String result = new String(contents);
        System.out.println(result);

        File file = new File("D:\\Downloads", fileName);
        FileWriter fileWriter = new FileWriter(file);
        fileWriter.write(result);
        fileWriter.flush();
        fileWriter.close();
        is.close();
    }

    private void readStreamPng(InputStream is, String fileName) throws IOException {
        BufferedImage bufferedImage = ImageIO.read(is);

        File file = new File("D:\\Downloads", fileName);
        if(!file.exists()) {
            file.createNewFile();
        }
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        ImageIO.write(bufferedImage, "png", fileOutputStream);

        fileOutputStream.close();
        is.close();
    }
}
