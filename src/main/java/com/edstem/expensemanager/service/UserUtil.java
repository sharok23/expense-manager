//package com.edstem.expensemanager.service;
//
//import com.edstem.expensemanager.model.User;
//import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Component;
//
//@Component
//public class UserUtil {
//
//    public Long getCurrentUserId() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (authentication != null && authentication.getPrincipal() instanceof User) {
//            User userDetails = (User) authentication.getPrincipal();
//            return userDetails.getId();
//        }
//        throw new IllegalStateException("User ID not found in authentication context");
//    }
//}
