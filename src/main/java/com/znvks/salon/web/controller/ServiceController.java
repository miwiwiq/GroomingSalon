package com.znvks.salon.web.controller;


import com.znvks.salon.model.dto.AccountDTO;
import com.znvks.salon.model.dto.FormDTO;
import com.znvks.salon.model.dto.PetDTO;
import com.znvks.salon.model.dto.ServiceDTO;
import com.znvks.salon.model.entity.Condition;
import com.znvks.salon.model.service.PetService;
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
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Controller
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ServiceController {


    private final ServiceService serviceService;
    private final PetService petService;

    @GetMapping({"/user/showServices", "/admin/showServices"})
    public String showAllServices(@SessionAttribute("account") AccountDTO account, Model model) {
        model.addAttribute("services", serviceService.getAll());
        if (Objects.nonNull(account)) {
            switch (account.getRole()) {
                case "admin":
                    return PageUtil.ADMIN_SERVICES;
                case "user": {
                    model.addAttribute("userPets", petService.getPetsByAcc(account));
                    model.addAttribute("form", FormDTO.builder()
                            .pet(PetDTO.builder().build())
                            .services(new ArrayList<>())
                            .condition(Condition.WAITING)
                            .build()
                    );
                    return PageUtil.USER_SERVICES;
                }
                default:
                    return PageUtil.SERVICES;
            }
        } else {
            return PageUtil.SERVICES;
        }
    }


    @GetMapping("/services")
    public String showServices(Model model) {
        model.addAttribute("services", serviceService.getAll());
        return PageUtil.SERVICES;
    }

    @ModelAttribute("services")
    public List<ServiceDTO> services() {
        return serviceService.getAll();
    }

    @PostMapping("/addService")
    public String addService(ServiceDTO service) {
        if (Objects.nonNull(service)) {
            serviceService.save(service);
            return PageUtil.REDIRECT_ADMIN_SHOW_SERVICES;
        }
        return PageUtil.REDIRECT_ERROR_PAGE;
    }

    @GetMapping("/admin/addServicePage")
    public String toAddPage(Model model) {
        model.addAttribute("service", ServiceDTO.builder().build());
        return PageUtil.ADMIN_ADD_SERVICE;
    }

    @GetMapping("/admin/editServicePage/{id}")
    public String toEditPage(@PathVariable("id") String id, Model model) {
        Optional<ServiceDTO> serv = serviceService.getById(Long.parseLong(id));
        serv.ifPresent(s -> model.addAttribute("service", s));
        return PageUtil.ADMIN_EDIT_SERVICE;
    }

    @PostMapping("/editService")
    public String editService(ServiceDTO service) {
        if (Objects.nonNull(service)) {
            serviceService.update(service);
            return PageUtil.REDIRECT_ADMIN_SHOW_SERVICES;
        } else {
            return PageUtil.REDIRECT_ERROR_PAGE;
        }
    }

    @GetMapping("/admin/deleteService/{id}")
    public String deleteService(@PathVariable("id") String id) {
        Optional<ServiceDTO> service = serviceService.getById(Long.parseLong(id));
        service.ifPresent(serviceService::delete);
        return PageUtil.REDIRECT_ADMIN_SHOW_SERVICES;
    }
}
