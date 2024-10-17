package jpa.mvc;

import com.fasterxml.jackson.datatype.hibernate5.jakarta.Hibernate5JakartaModule;
import jakarta.persistence.Persistence;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.prefs.Preferences;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MvcApplication {

	public static void main(String[] args) {
		SpringApplication.run(MvcApplication.class, args);
	}


	@Bean
	Hibernate5JakartaModule hibernate5JakartaModule(){
		return new Hibernate5JakartaModule();
	}

}
