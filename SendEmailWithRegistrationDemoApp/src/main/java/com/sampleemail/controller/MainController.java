package com.sampleemail.controller;

import javax.mail.internet.MimeMessage;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.sampleemail.models.Employee;
import com.sampleemail.repository.EmployeeRepository;

@Controller
public class MainController {

	@Autowired
	EmployeeRepository employeeRepository;

	@Autowired
	private JavaMailSender mailSenderObj;

	@GetMapping("/")
	public String welcome() {

		return "welcome";
	}

	@GetMapping("/add")
	public String addEmployee() {

		return "addEmployeeForm";
	}

	@RequestMapping(value = "/saveEmployee", method = RequestMethod.POST)
	public String addEmployee(ModelMap model, @Valid Employee employee) {
		employeeRepository.save(employee);

		// send mail method call
		sendmail(employee);
		return "redirect:/success";

	}
	
	@GetMapping("/success")
	public String success(){
		
		return "success";
	}
	
	
	//java send mail code
	private void sendmail(@Valid Employee employee) {
		final String emailToRecipient = employee.getEmp_email();

		final String emailSubject = "Suceesfully Registration with EmailWithRegistrationDemoApp";
		final String emailMessage1 = "<html> <body> <p>Dear Sir/Madam,</p><p>You have succesfully Registered with our Services"
				+ "<br><br>"
				+ "<table border='1' width='300px' style='text-align:center;font-size:20px;'><tr> <td colspan='2'>"
				+ "</td></tr><tr><td>Name</td><td>" + employee.getEmp_name() + "</td></tr><tr><td>Address</td><td>"
				+ employee.getEmp_address() + "</td></tr><tr><td>Email</td><td>" + employee.getEmp_email()
				+ "</td></tr></table> </body></html>";

		mailSenderObj.send(new MimeMessagePreparator() {
			public void prepare(MimeMessage mimeMessage) throws Exception {

				MimeMessageHelper mimeMsgHelperObj = new MimeMessageHelper(mimeMessage, true, "UTF-8");
				mimeMsgHelperObj.setTo(emailToRecipient);

				mimeMsgHelperObj.setText(emailMessage1, true);

				mimeMsgHelperObj.setSubject(emailSubject);
			}
		});

	}

}
