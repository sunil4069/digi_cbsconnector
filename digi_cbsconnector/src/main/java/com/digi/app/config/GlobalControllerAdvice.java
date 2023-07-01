package com.digi.app.config;

import com.digi.app.user.Roles;
import com.digi.app.user.Users;
import com.digi.app.user.repository.UsersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.ArrayList;
import java.util.Base64;
import java.util.Collection;

@ControllerAdvice
public class GlobalControllerAdvice {
    private UsersRepo usersRepo;

    @Autowired
    public void setUsersRepo(UsersRepo usersRepo) {
        this.usersRepo = usersRepo;
    }

    @ModelAttribute(name = "profileimage")
    public String profilepic(Authentication authentication) {
        String profileimage = "";
        if (authentication != null) {
            Users current_user = getCurrentUser(authentication);
            if (current_user.getStaffs() != null && current_user.getStaffs().getPic() != null) {
                byte[] image_byte = current_user.getStaffs().getPic();
                if (image_byte != null) {
                    profileimage = Base64.getEncoder().encodeToString(image_byte);
                }
            }
        }
        return profileimage;
    }

    @ModelAttribute(name = "username")
    public String username(Authentication authentication) {
        String username = "";
        try {
            Users current_user = getCurrentUser(authentication);
            if (current_user != null) {
                username = current_user.getUsername();
            }
        } catch (Exception e) {

        }

        return username;
    }

    //returns the username of the current user
    public Users getCurrentUser(Authentication authentication) {
        String current_username = authentication.getName();
        Users current_user = usersRepo.findByUsername(current_username);
        return current_user;
    }

    //getting user role
    @ModelAttribute(name = "role")
    public Collection<Roles> roleOfUser(Authentication authentication) {
        Collection<Roles> user_role = new ArrayList<>();
        try {
            Users current_user = getCurrentUser(authentication);
            if (current_user != null) {
                user_role = current_user.getRoles();
            }
        } catch (Exception e) {
        }
        return user_role;
    }

    //getting user post
    @ModelAttribute(name = "post")
    public String getuserPost(Authentication authentication) {
        String post = "";
        try {
            Users current_user = getCurrentUser(authentication);
            if (current_user != null) {
                post = current_user.getStaffs().getPost();
            }
        } catch (Exception e) {
        }
        return post;
    }
}
