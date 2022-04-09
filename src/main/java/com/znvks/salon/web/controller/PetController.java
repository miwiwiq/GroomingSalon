package com.znvks.salon.web.controller;


import com.znvks.salon.model.dto.AccountDTO;
import com.znvks.salon.model.dto.PetDTO;
import com.znvks.salon.model.dto.ServiceDTO;
import com.znvks.salon.model.service.PetService;
import com.znvks.salon.web.util.PageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Controller
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PetController {


    private final PetService petService;

    @GetMapping("/user/showUserPets")
    public String showUserPets(@SessionAttribute("account") AccountDTO account, Model model) {
        model.addAttribute("userPets", petService.getPetsByAcc(account));
        if (Objects.equals("user", account.getRole())) {
            return PageUtil.USER_PETS;
        } else {
            return PageUtil.REDIRECT_ERROR_PAGE;
        }
    }

    @ModelAttribute("userPets")
    public List<PetDTO> userPets(@SessionAttribute("account") AccountDTO account) {
        return petService.getPetsByAcc(account);
    }

    @GetMapping("/user/addPetPage")
    public String toAddPage(Model model){
        model.addAttribute("pet", PetDTO.builder().build());
        return PageUtil.USER_ADD_PET;
    }

    @PostMapping("/addPet")
    public String addPet(@SessionAttribute("account") AccountDTO account, PetDTO pet) {
        if (Objects.nonNull(pet)) {
            pet.setUser(account);
            petService.save(pet);
            return PageUtil.REDIRECT_USER_SHOW_USER_PETS;
        }
        return PageUtil.REDIRECT_ERROR_PAGE;
    }

    @GetMapping("/user/editPetPage/{id}")
    public String toEditPage(@PathVariable("id") String id, Model model){
        Optional<PetDTO> pet = petService.getById(Long.parseLong(id));
        pet.ifPresent(p -> model.addAttribute("pet", p));
        return PageUtil.USER_EDIT_PET;
    }

    @PostMapping("/editPet")
    public String editPet(@SessionAttribute("account") AccountDTO account, PetDTO pet) {
        if (Objects.nonNull(pet)) {
            pet.setUser(account);
            petService.update(pet);
            return PageUtil.REDIRECT_USER_SHOW_USER_PETS;
        } else {
            return PageUtil.REDIRECT_ERROR_PAGE;
        }
    }

    @GetMapping("/user/deletePet/{id}")
    public String deletePet(@PathVariable("id") String id) {
        Optional<PetDTO> pet = petService.getById(Long.parseLong(id));
        pet.ifPresent(petService::delete);
        return PageUtil.REDIRECT_USER_SHOW_USER_PETS;
    }
}
