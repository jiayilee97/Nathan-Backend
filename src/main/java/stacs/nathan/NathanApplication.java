package stacs.nathan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class NathanApplication {

  public static void main(String[] args) {
    SpringApplication.run(NathanApplication.class, args);
  }

}
