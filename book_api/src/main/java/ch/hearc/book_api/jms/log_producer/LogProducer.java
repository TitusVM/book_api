package ch.hearc.book_api.jms.log_producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import ch.hearc.book_api.service.CatalogService;

@Component
public class LogProducer implements CommandLineRunner {
	@Autowired
	JmsTemplate jmsTemplate;

	@Autowired
	CatalogService catalogService;

	@Override
	public void run(String... args) throws Exception {
		while (true) {
			Log log = catalogService.getLogs().take();
			System.out.println(log);
			jmsTemplate.convertAndSend("json-q", log);
		}
	}
}
