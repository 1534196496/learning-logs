package person.ll.demo.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

@SpringBootApplication
@Component
@MapperScan
public class Application {

	@Autowired
	private MapperTest mapperTest;

	public static void main(String[] args) {
		ConfigurableApplicationContext applicationContext = SpringApplication.run(Application.class, args);
		MapperTest bean = applicationContext.getBean(MapperTest.class);
		String java = bean.select("java");
		System.out.println(java);

		MapperTest2 bean2 = applicationContext.getBean(MapperTest2.class);
		bean2.select();
	}

}
