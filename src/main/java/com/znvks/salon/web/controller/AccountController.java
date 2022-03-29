package com.znvks.salon.web.controller;

import com.znvks.salon.model.dto.AccountDTO;
import com.znvks.salon.model.entity.account.Level;
import com.znvks.salon.model.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;


import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Controller
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@SessionAttributes({"account"})
public class AccountController {

    private final AccountService accountService;


    @PostMapping("/addAdmin")
    public String add(Model model) {
        AccountDTO account = AccountDTO.builder()
                .username("admin")
                .password("adminpass")
                .role("admin")
                .level(Level.FIRST)
                .build();
        accountService.save(account);
        model.addAttribute("account", account);
        return "/index";
    }

    @GetMapping("/")
    public String startPage(Model model) {
        model.addAttribute("account", AccountDTO.builder().build());
        return "/index";
    }

    @PostMapping("/signIn")
    public String login(Model model, AccountDTO accountDTO) {
        if (accountService.isAuthenticate(accountDTO)) {
            Optional<AccountDTO> optional = accountService.getAccByUsername(accountDTO.getUsername());
            if (optional.isPresent()) {
                AccountDTO account = optional.get();
                model.addAttribute("account", account);
                if (Objects.equals("user", account.getRole())) {
                    return "redirect:/userMain";
                } else if (Objects.equals("admin", account.getRole())) {
                    return "redirect:/adminMain";
                } else {
                    return "redirect:/";
                }
            } else {
                return "redirect:/";
            }
        } else {
            return "redirect:/";
        }
    }


    @PostMapping("/signUp")
    public String register(Model model, AccountDTO account) {
        if (Objects.nonNull(account.getUsername()) && Objects.nonNull(account.getPassword())) {
            Optional<AccountDTO> accountByUsername = accountService.getAccByUsername(account.getUsername());
            if (accountByUsername.isEmpty()) {
                accountService.save(account);
                model.addAttribute("account", account);
                return "redirect:/userMain";
            }
            return "redirect:/";
        }
        return "redirect:/";
    }

    @GetMapping("/userMain")
    public String userMain() {
        return "/user/userMain";
    }

    @GetMapping("/adminMain")
    public String adminPage() {
        return "/admin/adminMain";
    }

    @GetMapping("/logout")
    public String logout(SessionStatus sessionStatus) {
        sessionStatus.setComplete();
        return "redirect:/";
    }

    @GetMapping("/errorPage")
    public String errorPage() {
        return "/error";
    }

    @ModelAttribute("allUsers")
    public List<AccountDTO> allUsers() {
        return accountService.getAllUsers();
    }

    @ModelAttribute("allAdmins")
    public List<AccountDTO> allAdmins() {
        return accountService.getAllAdmins();
    }

    @PostMapping("/editUser")
    public String editUser(Model model, @SessionAttribute("account") AccountDTO a, AccountDTO account) {
        account.setId(a.getId());
        account.setUsername(a.getUsername());
        account.setRole(a.getRole());
        if (Objects.nonNull(account.getUsername()) && Objects.nonNull(account.getPassword())) {
            accountService.update(account);
            model.addAttribute("account", account);
            return "redirect:/userMain";
        } else {
            return "redirect:/errorPage";
        }
    }

    @GetMapping("/deleteUser/{id}")
    public String deleteUser(@PathVariable("id") String id) {
        Optional<AccountDTO> userById = accountService.getById(Long.parseLong(id));
        userById.ifPresent(accountService::delete);
        return "redirect:/adminMain";
    }


}
