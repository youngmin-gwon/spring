package youngmin.spring.boot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "youngmin")
@MapperScan(basePackages = "youngmin")
public class UserMain {
  
  public static void main(String[] args) {
    SpringApplication.run(UserMain.class, args);
  }
  
}
