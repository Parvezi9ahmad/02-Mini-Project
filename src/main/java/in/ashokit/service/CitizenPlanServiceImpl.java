package in.ashokit.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import in.ashokit.binding.SearchCriteria;
import in.ashokit.entity.CitizenPlan;
import in.ashokit.repo.CitizenPlanRepo;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class CitizenPlanServiceImpl implements CitizenPlanService {

	@Autowired
	private CitizenPlanRepo repo;

	@Override
	public List<String> getPlanNames() {
		return repo.getPlanNames();
	}

	@Override
	public List<String> getPlanStatus() {
		return repo.getPlanStatus();
	}

	@Override
	public List<CitizenPlan> searchCitizens(SearchCriteria criteria) {
		CitizenPlan entity = new CitizenPlan();
		if (StringUtils.isNotBlank(criteria.getPlanName())) {
			entity.setPlanName(criteria.getPlanName());
		}
		if (StringUtils.isNotBlank(criteria.getPlanStatus())) {
			entity.setPlanStatus(criteria.getPlanStatus());
		}
		if (StringUtils.isNotBlank(criteria.getGender())) {
			entity.setGender(criteria.getGender());
		}
		
		Example<CitizenPlan> of = Example.of(entity);

		return repo.findAll(of);
	}

	@Override
	public void generateExcel(HttpServletResponse response) {
		// TODO Auto-generated method stub

	}

	@Override
	public void generatePdf(HttpServletResponse response) {
		// TODO Auto-generated method stub

	}

}
