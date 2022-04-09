package com.znvks.salon.web.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PageUtil {

    public static final String INDEX = "/index";
    public static final String REDIRECT_USER_MAIN = "redirect: /user/userMain";
    public static final String REDIRECT_ADMIN_MAIN = "redirect:/admin/adminMain";
    public static final String REDIRECT_ERROR_PAGE = "redirect:/errorPage";
    public static final String REDIRECT_SUCCESS = "redirect:/success";
    public static final String REDIRECT_HOME = "redirect:/";
    public static final String USER_MAIN = "/user/userMain";
    public static final String ADMIN_MAIN = "/admin/adminMain";
    public static final String LOGOUT_PAGE = "/logoutPage";
    public static final String ERROR = "/error";
    public static final String USER_FORMS = "/user/forms";
    public static final String ADMIN_FORMS = "/admin/forms";
    public static final String REDIRECT_USER_SHOW_USER_FORMS = "redirect:/user/showUserForms";
    public static final String ADMIN_RESERVATION = "/admin/reservation";
    public static final String REDIRECT_ADMIN_SHOW_FORMS = "redirect:/admin/showForms";
    public static final String USER_PETS = "/user/pets";
    public static final String USER_ADD_PET = "/user/addPet";
    public static final String REDIRECT_USER_SHOW_USER_PETS = "redirect:/user/showUserPets";
    public static final String USER_EDIT_PET = "/user/editPet";
    public static final String USER_ORDERS = "/user/orders";
    public static final String ADMIN_ORDERS = "/admin/orders";
    public static final String REDIRECT_ADMIN_SHOW_ORDERS = "redirect:/admin/showOrders";
    public static final String ADMIN_EDIT_ORDER = "/admin/editOrder";
    public static final String REDIRECT_USER_SHOW_USER_ORDERS = "redirect:/user/showUserOrders";
    public static final String USER_RATE_ORDER = "/user/rateOrder";
    public static final String ADMIN_SERVICES = "/admin/services";
    public static final String USER_SERVICES = "/user/services";
    public static final String SERVICES = "/services";
    public static final String REDIRECT_ADMIN_SHOW_SERVICES = "redirect:/admin/showServices";
    public static final String ADMIN_ADD_SERVICE = "/admin/addService";
    public static final String ADMIN_EDIT_SERVICE = "/admin/editService";
}
