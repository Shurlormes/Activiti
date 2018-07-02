import com.voidforce.activiti.Application;
import org.activiti.engine.IdentityService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class UserTest {

	@Autowired
	private IdentityService identityService;


	@Test
	public void user() {


		/*User dbUser = identityService.createUserQuery().userId("1").singleResult();
		System.out.println(dbUser.getFirstName());

		dbUser.setFirstName("李四");
		identityService.saveUser(dbUser);

		dbUser = identityService.createUserQuery().userId("1").singleResult();
		System.out.println(dbUser.getFirstName());*/


		identityService.deleteUser("1");

		//identityService.deleteMembership("1", "3");
		//identityService.createMembership("1", "1");
		//identityService.createMembership("1", "2");

	}

}