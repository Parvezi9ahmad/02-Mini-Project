package in.ashokit.runner;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import in.ashokit.entity.CitizenPlan;
import in.ashokit.repo.CitizenPlanRepo;

@Component
public class DataLoader implements ApplicationRunner {

	@Autowired
	private CitizenPlanRepo repo;

	@Override
	public void run(ApplicationArguments args) throws Exception {

		CitizenPlan p1 = new CitizenPlan("John", "john@in.com", 12341L, 987651L, "Male", "Cash", "Approved",
				LocalDate.now(), LocalDate.now().plusMonths(6));

		CitizenPlan p2 = new CitizenPlan("Smith", "smith@in.com", 22341L, 687651L, "Male", "Cash", "Denied", null,
				null);

		CitizenPlan p3 = new CitizenPlan("Cathy", "cathy@in.com", 32341L, 787651L, "Fe-Male", "Food", "Approved",
				LocalDate.now(), LocalDate.now().plusMonths(6));

		CitizenPlan p4 = new CitizenPlan("Johny", "johny@in.com", 42341L, 387651L, "Fe-Male", "Food", "Denied", null,
				null);

		CitizenPlan p5 = new CitizenPlan("Robert", "robert@in.com", 72341L, 487651L, "Male", "Medical", "Approved",
				LocalDate.now(), LocalDate.now().plusMonths(6));

		CitizenPlan p6 = new CitizenPlan("any", "any@in.com", 32341L, 387651L, "Fe-Male", "Food", "Denied", null, null);

		List<CitizenPlan> records = Arrays.asList(p1, p2, p3, p4, p5, p6);
		repo.saveAll(records);

	}

}
