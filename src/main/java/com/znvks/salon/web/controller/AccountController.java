package com.znvks.salon.web.controller;

import com.znvks.salon.model.dto.AccountDTO;
import com.znvks.salon.model.entity.account.Level;
import com.znvks.salon.model.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;


import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Controller
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@SessionAttributes({"account"})
public class AccountController {

    private final AccountService accountService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;


    @GetMapping("/addAdmin")
    public String add(Model model) {
        AccountDTO account = AccountDTO.builder()
                .username("admin")
                .password("adminpass")
                .role("admin")
                .level(Level.FIRST)
                .build();
        String rawPassword = account.getPassword();
        account.setPassword(passwordEncoder.encode(rawPassword));
        accountService.save(account);
        model.addAttribute("account", account);
        return "redirect:/";
    }

    @GetMapping("/")
    public String startPage(Model model) {
            model.addAttribute("account", AccountDTO.builder().build());
            return "/index";
    }

    @GetMapping("/home")
    public String homePage(@SessionAttribute("account") AccountDTO a) {
        return switch (a.getRole()) {
            case "user" -> "redirect: /user/userMain";
            case "admin" -> "redirect:/admin/adminMain";
            default -> "redirect:/errorPage";
        };
    }

//    @PostMapping("/signIn")
//    public String login(Model model, AccountDTO accountDTO) {
//        if (accountService.isAuthenticate(accountDTO)) {
//            Optional<AccountDTO> optional = accountService.getAccByUsername(accountDTO.getUsername());
//            if (optional.isPresent()) {
//                AccountDTO account = optional.get();
//                model.addAttribute("account", account);
//                if (Objects.equals("user", account.getRole())) {
//                    return "redirect:/user/userMain";
//                } else if (Objects.equals("admin", account.getRole())) {
//                    return "redirect:/admin/adminMain";
//                } else {
//                    return "redirect:/";
//                }
//            } else {
//                return "redirect:/";
//            }
//        } else {
//            return "redirect:/";
//        }
//    }


    @PostMapping("/signUp")
    public String register(Model model, AccountDTO account, HttpServletRequest request) {
        if (Objects.nonNull(account.getUsername()) && Objects.nonNull(account.getPassword())) {
            Optional<AccountDTO> accountByUsername = accountService.getAccByUsername(account.getUsername());
            if (accountByUsername.isEmpty()) {
                String rawPassword = account.getPassword();
                account.setPassword(passwordEncoder.encode(rawPassword));
                accountService.save(account);

                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(account.getUsername(), rawPassword);
                authenticationToken.setDetails(new WebAuthenticationDetails(request));
                Authentication authentication = authenticationManager.authenticate(authenticationToken);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                return "redirect:/success";
            }
            return "redirect:/";
        }
        return "redirect:/";
    }

    @GetMapping("/success")
    public String success(
            @AuthenticationPrincipal org.springframework.security.core.userdetails.User account,
            Model model) {
        Optional<AccountDTO> accountByUsername = accountService.getAccByUsername(account.getUsername());
        if (accountByUsername.isPresent()) {
            AccountDTO accountDto = accountByUsername.get();
            model.addAttribute("account", accountDto);
            return switch (accountDto.getRole()) {
                case "user" -> "redirect: /user/userMain";
                case "admin" -> "redirect:/admin/adminMain";
                default -> "redirect:/errorPage";
            };
        }
        return "redirect:/errorPage";
    }

    @GetMapping("/user/userMain")
    public String userMain() {
        return "/user/userMain";
    }

    @GetMapping("/admin/adminMain")
    public String adminPage() {
        return "/admin/adminMain";
    }

    @GetMapping("/logout")
    public String logout() {
        return "/logoutPage";
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

    @PostMapping("/user/editUser")
    public String editUser(Model model, @SessionAttribute("account") AccountDTO a, AccountDTO account) {
        account.setId(a.getId());
        account.setUsername(a.getUsername());
        account.setRole(a.getRole());
        String rawPassword = account.getPassword();
        account.setPassword(passwordEncoder.encode(rawPassword));
        if (Objects.nonNull(account.getUsername()) && Objects.nonNull(account.getPassword())) {
            accountService.update(account);
            model.addAttribute("account", account);
            return "redirect:/user/userMain";
        } else {
            return "redirect:/errorPage";
        }
    }

    @GetMapping("/admin/deleteUser/{id}")
    public String deleteUser(@PathVariable("id") String id) {
        Optional<AccountDTO> userById = accountService.getById(Long.parseLong(id));
        userById.ifPresent(accountService::delete);
        return "redirect:/admin/adminMain";
    }


}
