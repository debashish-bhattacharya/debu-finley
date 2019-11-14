package in.vnl;

import java.util.TimeZone;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

import in.vnl.repository.EventService;

@EnableAutoConfiguration
@SpringBootApplication(scanBasePackages={"in.vnl.controller","in.vnl.model","in.vnl.repository","in.vnl.security","in.vnl.common"})
@ComponentScan({"in.vnl.controller","in.vnl.model","in.vnl.repository","in.vnl.security","in.vnl.common"})
@EntityScan(basePackages = {"in.vnl.model","in.vnl.common" })
@EnableJpaRepositories("in.vnl.repository")
@EnableScheduling
public class BootApplication {

	@Autowired
	private EventService es;
	
	private static final Logger logger = LoggerFactory.getLogger(BootApplication.class);
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		logger.info("Booting application..........");
		SpringApplication.run(BootApplication.class, args);
		
	}
	
	@PreDestroy
    public void onDestroy() throws Exception 
	{
		logger.info("Clearing events..........");
		es.trucateEvets();
		logger.info("done");
	}

}
