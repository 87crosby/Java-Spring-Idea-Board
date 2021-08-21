package com.howe.beltexam.controllers;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.howe.beltexam.models.Idea;
import com.howe.beltexam.models.LoginUser;
import com.howe.beltexam.models.User;
import com.howe.beltexam.services.IdeaService;
import com.howe.beltexam.services.UserService;


@Controller
public class HomeController {
	
	@Autowired
	private UserService userServ;
	
	@Autowired
	private IdeaService ideaServ;
	
	
	@GetMapping("/")
	public String index(Model model) {
		model.addAttribute("newUser", new User());
		model.addAttribute("newLogin", new LoginUser());
		return "index.jsp";
	}
	
	@PostMapping("/register")
	public String register(@Valid @ModelAttribute("newUser") User newUser, BindingResult result, Model model, HttpSession session) {
		userServ.register(newUser, result);
		if(result.hasErrors()) {
            model.addAttribute("newLogin", new LoginUser());
            return "index.jsp";
        }
        session.setAttribute("user_id", newUser.getId());
        return "redirect:/home";
	}
	
	@PostMapping("/login")
    public String login(@Valid @ModelAttribute("newLogin") LoginUser newLogin, 
            BindingResult result, Model model, HttpSession session) {
        
		// Use login method to find 
		User user = userServ.login(newLogin, result);
        if(result.hasErrors()) {
            model.addAttribute("newUser", new User());
            return "index.jsp";
        }
        session.setAttribute("user_id", user.getId());
        return "redirect:/home";
    }
	
	@GetMapping("/home")
	public String home(HttpSession session, Model model, RedirectAttributes redirectAttributes) {
		Long loggedInUserId = (Long)session.getAttribute("user_id");
		
		if(loggedInUserId == null) {
			redirectAttributes.addFlashAttribute("notAllowed", "You must login first!");
			return "redirect:/";
		}
		
		// Use id from session to retrieve matching id from database
		User user = userServ.findUser(loggedInUserId);
		model.addAttribute("user", user);
		
		// Retrieve all idea's from meals service
		List<Idea> allIdeas = this.ideaServ.findAllIdeas();
		
		// Pass to template
		model.addAttribute("allIdeas",allIdeas);
		
		return "home.jsp";
	}
	
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.removeAttribute("user_id");
		return "redirect:/";
	}
	
	@GetMapping("/ideas/new")
	public String newIdea(@ModelAttribute("idea") Idea idea) {
		return "newMeal.jsp";
	}
	
	@PostMapping("/ideas/create")
	public String createIdea(@Valid @ModelAttribute("idea") Idea idea, BindingResult result, HttpSession session, RedirectAttributes redirectAttributes) {
		if(result.hasErrors()) {
			redirectAttributes.addFlashAttribute("notAllowed", "Content must be between 3 and 100 characters");
			return "redirect:/ideas/new";
		}
		// Get the id of the logged in user
		Long idOfUser = (Long)session.getAttribute("user_id");
		
		// Get the user using the id
		User loggedUser = this.userServ.findUser(idOfUser);
		
		// Set the idea's creator to be logged in user
		idea.setUploader(loggedUser);
		
		
		this.ideaServ.createIdea(idea);
		
		
		return "redirect:/home";
	}
	
	@GetMapping("/idea/info/{id}")
	public String showIdeaInfo(@PathVariable("id") Long id, Model model) {
		// Get idea from database
		Idea ideaobj = this.ideaServ.getIdea(id);
		
		// Pass idea object to template
		model.addAttribute("idea", ideaobj);
		return "mealInfo.jsp";
	}
	
	@GetMapping("/idea/{id}/edit")
	public String editIdea(@PathVariable("id") Long id, Model model) {
		// Get idea with id from database
		Idea ideaobj = this.ideaServ.getIdea(id);
		
		// Pass idea object to template
		model.addAttribute("idea", ideaobj);
		
		return "mealEdit.jsp";
	}
	
	@PostMapping("/idea/update/{id}")
	public String updateIdea(@Valid @ModelAttribute("idea") Idea idea, BindingResult result, HttpSession session, @PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
		if(result.hasErrors()) {
			redirectAttributes.addFlashAttribute("notAllowed", "Content must be between 3 and 100 characters");
			return "redirect:/idea/{id}/edit";
		}
		// Get the id of the logged in user
		Long idOfUser = (Long)session.getAttribute("user_id");
				
		// Get the user using the id
		User loggedUser = this.userServ.findUser(idOfUser);
		
		// Set the idea's creator to be logged in user
		idea.setUploader(loggedUser);
		
		this.ideaServ.updateIdea(idea);
		
		return "redirect:/home";
	}
	
	@GetMapping("/delete/{id}")
	public String deleteIdea(@PathVariable("id") Long id) {
		this.ideaServ.deleteIdea(id);
		return "redirect:/home";
	}
	
	

}
