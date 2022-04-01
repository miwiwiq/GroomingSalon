package com.znvks.salon.web.controller;


import com.znvks.salon.model.dto.AccountDTO;
import com.znvks.salon.model.dto.FormDTO;
import com.znvks.salon.model.dto.ReservationDTO;
import com.znvks.salon.model.dto.ServiceDTO;
import com.znvks.salon.model.entity.Condition;
import com.znvks.salon.model.service.FormService;
import com.znvks.salon.model.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.Objects;
import java.util.Optional;

@Controller
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ReservationController {

    private final ReservationService reservationService;
    private final FormService formService;

    @GetMapping("/user/showUserOrders")
    public String showAllOrders(@SessionAttribute("account") AccountDTO account, Model model) {
        model.addAttribute("userOrders", reservationService.getOrdersByAcc(account));
        if (Objects.equals("user", account.getRole())) {
            return "/user/orders";
        } else {
            return "redirect:/errorPage";
        }
    }

    @GetMapping("/admin/showOrders")
    public String showOrders(Model model) {
        model.addAttribute("orders", reservationService.getAll());
        return "/admin/orders";
    }

    @PostMapping("/reservation")
    public String reservation(@RequestParam("formId") String formId, ReservationDTO reservation) {
        Optional<FormDTO> form = formService.getById(Long.valueOf(formId));
        if (form.isPresent()) {
            FormDTO f = form.get();
            f.setCondition(Condition.ACCEPTED);
            formService.update(f);
            reservation.setForm(f);
            reservationService.save(reservation);
            return "redirect:/admin/showOrders";

        } else {
            return "redirect:/errorPage";
        }
    }

    @GetMapping("/admin/editOrderPage/{id}")
    public String toEditPage(@PathVariable("id") String id, Model model) {
        Optional<ReservationDTO> reservationDTO = reservationService.getById(Long.parseLong(id));
        reservationDTO.ifPresent(r -> model.addAttribute("editingOrder", r));
        return "/admin/editOrder";
    }

    @PostMapping("/editOrder")
    public String editService(@SessionAttribute("account") AccountDTO account, @RequestParam("formId") String formId, ReservationDTO reservation) {
        Optional<FormDTO> form = formService.getById(Long.valueOf(formId));
        if (form.isPresent()) {
            System.out.println(reservation);
            reservation.setForm(form.get());
            reservationService.update(reservation);
            if (account.getRole().equals("user")) {
                return "redirect:/user/showUserOrders";
            } else if (account.getRole().equals("admin")) {
                return "redirect:/admin/showOrders";
            } else return "redirect:/errorPage";
        } else {
            return "redirect:/errorPage";
        }
    }

    @GetMapping("/user/ratingPage/{id}")
    public String toRatePage(@PathVariable("id") String id, Model model) {
        Optional<ReservationDTO> reservationDTO = reservationService.getById(Long.parseLong(id));
        reservationDTO.ifPresent(r -> model.addAttribute("ratingOrder", r));
        return "/user/rateOrder";
    }

    @GetMapping("/admin/deleteOrder/{id}")
    public String deleteForm(@PathVariable("id") String id) {
        Optional<ReservationDTO> order = reservationService.getById(Long.parseLong(id));
        if (order.isPresent()) {
            ReservationDTO reservation = order.get();
            FormDTO form = reservation.getForm();
            reservationService.delete(reservation);
            if (reservationService.getById(reservation.getId()).isEmpty()) {
                form.setCondition(Condition.WAITING);
            }
            return "redirect:/admin/showOrders";
        } else {
            return "redirect:/errorPage";
        }
    }
}
