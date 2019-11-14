package in.vnl.controller;

//import org.springframework.messaging.handler.annotation.MessageMapping;
//import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Controller
public class SchedulController {

	@Autowired
	private Environment env;
	
	//@Value("${app.statusCheckPeriod}")
	//private String statusCheckPeriod = env.getProperty("app.statusCheckPeriod");
	@Value("${app.statusCheckPeriod}")
	private String statusCheckPeriod;
	private static final Logger log = LoggerFactory.getLogger(SchedulController.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    
    public void statusUpdate() 
    {
    	log.info("period : "+statusCheckPeriod);
    	log.info("The time is now {}", dateFormat.format(new Date()));
    	new StatusController().updateStatusOfNodes();
    }
}
