package hh.taskmanager.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.security.access.prepost.PreAuthorize;

import hh.taskmanager.domain.AppUser;
import hh.taskmanager.domain.AppUserRepository;

@Controller
@RequestMapping("/admin")   // every admin endpoint starts with /admin. easier to manage in websecurityconfig
@PreAuthorize("hasAuthority('ADMIN')")  // secures all endpoints in this controller
public class AdminController {

    private final AppUserRepository appUserRepository;

    public AdminController(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

@GetMapping("/users")
public String listUsers(Model model) {
    Iterable<AppUser> users = appUserRepository.findAll();
    model.addAttribute("users", users);
    return "userlist";
}

@PostMapping("/users/delete/{id}")
public String deleteUser(@PathVariable Long id) {
    AppUser user = appUserRepository.findById(id).orElse(null);

// can't delete admins
// using this check instead of !user.getRole().equals("ADMIN"), because user.getRole() can be null. "ADMIN" can't be null. so no nullpointerexception
    if (user != null && !"ADMIN".equals(user.getRole())) { 
        appUserRepository.deleteById(id);
    }
    return "redirect:/admin/users";
}
}