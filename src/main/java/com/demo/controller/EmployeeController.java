package com.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.demo.dto.Employee;

@RestController
public class EmployeeController {

	@Autowired
	private RestTemplate restTemplate;
	
	List<Employee> empList = new ArrayList<>();

	@GetMapping("/rest-call-to-provider")
	public String callNow() {
		String url = "http://localhost:8081/testNow";
		return restTemplate.getForObject(url, String.class);
	}
	
	@GetMapping("/add-employee")
	public ModelAndView getNext(Model m) {
		m.addAttribute("employee", new Employee());
		return new ModelAndView("add-employee");
	}
	
	@PostMapping("/save-employee")
	public ModelAndView addEmployee(@ModelAttribute Employee employee) {
		String url = "http://localhost:8081/save-employee";
		restTemplate.postForObject(url, employee, Employee.class);
		return new ModelAndView("redirect:/get-all-employees");
	}
	
	@GetMapping("/get-all-employees")
	public ModelAndView getAllEmployees(ModelAndView mv) {
		String url = "http://localhost:8081/get-employees";
		empList = restTemplate.getForObject(url, List.class);
		mv.addObject("employees", empList);
		mv.setViewName("view-employees");
		return mv;
	}
	
	@GetMapping("/findEmp/{empId}")
	public Employee findEmployeeById(@RequestBody int empId) {
		String url = "http://localhost:8081/findEmp/{empId}";
		return restTemplate.getForObject(url, Employee.class, empId);
	}
	
	@GetMapping("/edit-employee/{empId}")
	public ModelAndView editEmp(@PathVariable int empId, ModelAndView mv) {
		mv.addObject("editingEmp", this.findEmployeeById(empId));
		String url = "http://localhost:8081/get-employees";
		mv.addObject("employees", restTemplate.getForObject(url, List.class));
		mv.setViewName("edit-employee");
		return mv;
	}
	
	@PostMapping("/update-employee/{empId}")
	public ModelAndView updateEmployee(@PathVariable int empId, @ModelAttribute Employee employee) {
		//employee.setEmpId(empId);
		System.out.println("emp details---"+employee.toString());
		String url = "http://localhost:8081/update/{empId}";
		restTemplate.postForObject(url, employee, Integer.class, empId);
		return new ModelAndView("redirect:/get-all-employees");
	}
	
	@GetMapping("/update-cancel")
	public ModelAndView cancelUpdate() {
		return new ModelAndView("redirect:/get-all-employees");
	}
	
	@GetMapping("/delete/{empId}")
	public ModelAndView deleteEmployee(@PathVariable int empId) {
		String url = "http://localhost:8081/delete/{empId}";
		restTemplate.getForObject(url, Employee.class, empId);
		return new ModelAndView("redirect:/get-all-employees");
	}
	
}
