package in.ashokit.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import in.ashokit.binding.SearchCriteria;
import in.ashokit.service.CitizenPlanService;

@Controller
public class CitizenPlanController {

	@Autowired
	private CitizenPlanService service;

	@GetMapping("/")
	public String index(Model model) {

		List<String> planNames = service.getPlanNames();
		List<String> planStatus = service.getPlanStatus();

		model.addAttribute("plan-names-list", planNames);
		model.addAttribute("plan-status-list", planStatus);
		model.addAttribute("search", new SearchCriteria());
		return "index";
	}
}
