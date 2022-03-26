package com.znvks.salon.web.controller;

import com.znvks.salon.dto.AccountDTO;
import com.znvks.salon.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Controller
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
//@SessionAttributes({"account"})
public class AccountController {

    private final AccountService accountService;

    @ModelAttribute("allUsers")
    public List<AccountDTO> allUsers() {
        return accountService.getAllUsers();
    }

    @ModelAttribute("allAdmins")
    public List<AccountDTO> allAdmins() {
        return accountService.getAllAdmins();
    }

    @GetMapping("/")
    public String startPage() {
        return "index";
    }

    @PostMapping("/signIn")
    public String login(AccountDTO accountDto, Model model) {
        Optional<AccountDTO> accountByUsername = accountService.getAccByUsername(accountDto.getUsername());
        if (accountByUsername.isPresent()) {
            AccountDTO account = accountByUsername.get();
            if (Objects.equals(account.getPassword(), accountDto.getPassword())) {
                model.addAttribute("account", account);
                return "redirect:/userPage";
//                if (Objects.equals("user", account.getRole())) {
//                    return "redirect:/userPage";
//                } else if (Objects.equals("librarian", account.getRole())) {
//                    return "redirect:/librarianPage";
//                } else if (Objects.equals("admin", account.getRole())) {
//                    return "redirect:/adminPage";
//                } else {
//                    return "";
//                }
            } else {
                return "redirect:/";
            }
        } else {
            return "redirect:/";
        }
    }


}
