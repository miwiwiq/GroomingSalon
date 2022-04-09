package com.znvks.salon.web.controller;


import com.znvks.salon.model.dto.AccountDTO;
import com.znvks.salon.model.dto.FormDTO;
import com.znvks.salon.model.dto.PetDTO;
import com.znvks.salon.model.dto.ReservationDTO;
import com.znvks.salon.model.dto.ServiceDTO;
import com.znvks.salon.model.entity.Condition;
import com.znvks.salon.model.service.FormService;
import com.znvks.salon.model.service.PetService;
import com.znvks.salon.model.service.ReservationService;
import com.znvks.salon.model.service.ServiceService;
import com.znvks.salon.web.util.PageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Controller
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class FormController {


    private final FormService formService;
    private final PetService petService;
    private final ServiceService serviceService;

    @GetMapping("/user/showUserForms")
    public String showAllForms(@SessionAttribute("account") AccountDTO account, Model model) {
        model.addAttribute("userForms", formService.getFormsByAcc(account));
        if (Objects.equals("user", account.getRole())) {
            return PageUtil.USER_FORMS;
        } else {
            return PageUtil.REDIRECT_ERROR_PAGE;
        }
    }

    @GetMapping("/admin/showForms")
    public String showForms(Model model) {
        model.addAttribute("forms", formService.getFormsByCondition(Condition.WAITING));
        return PageUtil.ADMIN_FORMS;
    }

    @ModelAttribute("userForms")
    public List<FormDTO> userForms(@SessionAttribute("account") AccountDTO account) {
        return formService.getFormsByAcc(account);
    }

    @PostMapping("/addForm")
    public String add(@RequestParam("serviceId") String[] serviceIds,
                      @RequestParam("petId") String petId,
                      FormDTO form) {
        if (Objects.nonNull(serviceIds) && Objects.nonNull(petId)) {
            Optional<PetDTO> pet = petService.getById(Long.valueOf(petId));
            pet.ifPresent(petDTO -> form.setPet(pet.get()));
            for (String serviceId : serviceIds) {
                Optional<ServiceDTO> service = serviceService.getById(Long.parseLong(serviceId));
                service.ifPresent(serviceDTO -> form.getServices().add(serviceDTO));
            }
            form.setCondition(Condition.WAITING);
            formService.save(form);
            return PageUtil.REDIRECT_USER_SHOW_USER_FORMS;
        }
        return PageUtil.REDIRECT_ERROR_PAGE;
    }

    @GetMapping("/admin/acceptForm/{id}")
    public String acceptForm(@PathVariable("id") String id, Model model) {
        Optional<FormDTO> form = formService.getById(Long.parseLong(id));
        form.ifPresent(formDTO -> model.addAttribute("currentOrder", ReservationDTO
                .builder()
                .form(formDTO)
                .build()));
        return PageUtil.ADMIN_RESERVATION;
    }

    @GetMapping("/admin/declineForm/{id}")
    public String declineForm(@PathVariable("id") String id) {
        Optional<FormDTO> form = formService.getById(Long.parseLong(id));
        form.ifPresent(f -> f.setCondition(Condition.DECLINED));
        form.ifPresent(formService::update);
        return PageUtil.REDIRECT_ADMIN_SHOW_FORMS;
    }

    @GetMapping("/user/deleteForm/{id}")
    public String deleteForm(@PathVariable("id") String id) {
        Optional<FormDTO> form = formService.getById(Long.parseLong(id));
        form.ifPresent(formService::delete);
        return PageUtil.REDIRECT_USER_SHOW_USER_FORMS;
    }
}
