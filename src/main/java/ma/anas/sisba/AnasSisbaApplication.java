package ma.anas.sisba;

import com.opencsv.CSVReader;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import ma.anas.sisba.Entities.TestObject;
import ma.anas.sisba.Entities.User;
import ma.anas.sisba.Repositories.TestObjectRepo;
import ma.anas.sisba.Repositories.UserRepo;
import ma.anas.sisba.Services.FilesStorageService;
import ma.anas.sisba.Services.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.io.FileReader;
import java.util.*;

@OpenAPIDefinition(
		info = @Info(
				title = "INTERVIEW TEST API",
				version = "1.0.0",
				description = "services for INDATACORE interview test."
		)
)
	@SecurityScheme(
		name = "Bearer Authentication",
		type = SecuritySchemeType.HTTP,
		bearerFormat = "JWT",
		scheme = "bearer"
)
@CrossOrigin
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class AnasSisbaApplication implements CommandLineRunner {


	@Autowired
	private FilesStorageService filesStorageService;

	public static void main(String[] args) {
		SpringApplication.run(AnasSisbaApplication.class, args);
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}


	@Override
	public void run(String... args) throws Exception {
		try{
			uploadFile();
		}catch (Exception e){}
		try{
			filesStorageService.init();
		}catch (Exception e){}
	}

	@Autowired
	private TestObjectRepo testObjectRepo;

	public void uploadFile() throws Exception {

		// read from in local disk
		try (CSVReader csvReader = new CSVReader(new FileReader("D:\\BDD.csv"))) {
			String[] values = null;
			// read each line at a time
			while ((values = csvReader.readNext()) != null) {
				if(values.length > 2)
				{
					try
					{
						TestObject testObject = new TestObject(UUID.randomUUID().toString(),values[0], Double.parseDouble(values[1]),values[3],values[2],false);
						testObjectRepo.save(testObject);
					}
					catch(NumberFormatException e)
					{
					}
				}
			}
		}
	}

}
