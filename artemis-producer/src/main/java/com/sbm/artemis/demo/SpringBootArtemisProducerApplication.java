package com.sbm.artemis.demo;

import com.github.javafaker.Faker;
import com.sbm.artemis.demo.jms.Person;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

@EnableScheduling
@SpringBootApplication
public class SpringBootArtemisProducerApplication {

	private static Environment environment;

	@Autowired
	public  void setEnvironment(Environment environment) {
		SpringBootArtemisProducerApplication.environment = environment;
	}

	private static final Logger log = LoggerFactory.getLogger(SpringApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(SpringBootArtemisProducerApplication.class, args);

	}

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

	@Bean
	public CommandLineRunner run() throws Exception {
		String activeProfile = SpringBootArtemisProducerApplication.environment.getActiveProfiles()[0];
		String artemisPort = SpringBootArtemisProducerApplication.environment.getProperty("spring.artemis.port");

		System.out.println(String.format("======================Starting Producer on Mode %s ======================", activeProfile));
		if (activeProfile.equals("cluster")){
		System.out.println(String.format("===============Cluster is connected to Node @ Port : %s ===================", artemisPort));
		}
		return args -> {
			personPublisher();
		};
	}

//		@Scheduled(fixedDelay = 5000L)
		private void personPublisher(){
			RestTemplate restTemplate = new RestTemplate();
			Faker faker = new Faker();
			Person p = new Person();
			for (int i = 0; i < 9; i++) {
			p.setId(String.valueOf(i) );
			p.setName( faker.name().fullName());
			final HttpEntity<Person> request = new HttpEntity<>(p);
			String response = restTemplate.postForObject(
					"http://localhost:8080/produce", request , String.class);

			log.info(response);
		}
		}


}
