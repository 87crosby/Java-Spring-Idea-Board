package com.howe.beltexam.services;

import java.util.Optional;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import com.howe.beltexam.models.LoginUser;
import com.howe.beltexam.models.User;
import com.howe.beltexam.repositories.UserRepository;



@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepo;
    
    // Service method
    public User register(User newUser, BindingResult result) {
    	
    	// Validation to ensure the email of the new user trying to register is not already taken
        if(userRepo.findByEmail(newUser.getEmail()).isPresent()) {
            result.rejectValue("email", "Unique", "This email is already in use!");
        }
        
        // Validation to ensure password and confirm password match 
        if(!newUser.getPassword().equals(newUser.getConfirm())) {
            result.rejectValue("confirm", "Matches", "The Confirm Password must match Password!");
        }
        
        // Validation to ensure fields are filled in a manner consistent with parameters set in model
        if(result.hasErrors()) {
            return null;
        } else {
            String hashed = BCrypt.hashpw(newUser.getPassword(), BCrypt.gensalt());
            newUser.setPassword(hashed);
            return userRepo.save(newUser);
        }
    }
    
    public User login(LoginUser newLogin, BindingResult result) {
        if(result.hasErrors()) {
            return null;
        }
        
        // Check to see if there is a match between email entered in form and one in the database
        Optional<User> potentialUser = userRepo.findByEmail(newLogin.getEmail());
        
        // If user not present display message
        if(!potentialUser.isPresent()) {
            result.rejectValue("email", "Unique", "Unknown email!");
            return null;
        }
        
        // Get user from db with matching email
        User user = potentialUser.get();
        
        // Use Bcrypt to check the password against the one in the db the see if they match
        if(!BCrypt.checkpw(newLogin.getPassword(), user.getPassword())) {
            result.rejectValue("password", "Matches", "Invalid Password!");
        }
        if(result.hasErrors()) {
            return null;
        } else {
            return user;
        }
    }
    
    // Method to get user by id from database
    public User findUser(Long id) {
    	Optional<User> user = userRepo.findById(id);
    	if(user.isPresent()) {
			return user.get();
		} else {
			return null;
		}
    }
    
}
