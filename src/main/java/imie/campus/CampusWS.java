package imie.campus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;


/**
 * The main entrypoint of the WebService application.
 */
@SpringBootApplication
@EnableScheduling
@ComponentScan(basePackages = "imie.campus")
public class CampusWS {

	/**
	 * Launch the application
	 * @param args The arguments
	 */
	public static void main(String[] args) {
		SpringApplication.run(CampusWS.class, args);
	}
}


