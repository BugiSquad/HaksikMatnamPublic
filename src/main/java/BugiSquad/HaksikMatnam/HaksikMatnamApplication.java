package BugiSquad.HaksikMatnam;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class HaksikMatnamApplication {

	public static void main(String[] args) {
		SpringApplication.run(HaksikMatnamApplication.class, args);
	}

}
