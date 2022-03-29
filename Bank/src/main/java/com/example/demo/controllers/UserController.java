package com.example.demo.controllers;

import com.example.demo.WebSecurity.CustomUserDetails;
import com.example.demo.models.Credit;
import com.example.demo.models.Transfer;
import com.example.demo.models.User;
import com.example.demo.services.CreditService;
import com.example.demo.services.DetailsService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;


@Controller
public class UserController {
    private final DetailsService detailsService;
    private final CreditService creditService;

    UserController(DetailsService detailsService, CreditService creditService){
        this.detailsService=detailsService;
        this.creditService=creditService;
    }

    @GetMapping("/")
    String home(){
        return "home";
    }

    @GetMapping("/login")
    String loginPage(){
        return "login";
    }

    @GetMapping("/register")
    String register(Model model){
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    String register(User user){
        if(detailsService.userExist(user.getLogin())){
            return "register_fail";
        }
        detailsService.encodePassword(user);
        return "register_success";
    }

    @GetMapping("/logged")
    String loggedPage
            (@AuthenticationPrincipal CustomUserDetails userDetails ,
              Model model){
        User loggedUser=userDetails.getUser();
        List<Credit> userCredits=creditService.findByUser(loggedUser);
        model.addAttribute("AuthUser", loggedUser);
        model.addAttribute("credits",userCredits);
        return "logged";
    }

    @GetMapping("/credit")
    String creditPage(Model model){
        model.addAttribute("credit", new Credit());
        return "credit";
    }

    @PostMapping("/credit")
    String creditPage(Credit credit,
    @AuthenticationPrincipal CustomUserDetails userDetails){
        creditService.takeCredit
                (credit.getCreditName(),
                credit.getMoney(), userDetails.getUser());
        return "credit_success";
    }

    @GetMapping("/transfer")
    String transfer(Model model){
        model.addAttribute("transfer", new Transfer());
        return "transfer";
    }
    @PostMapping("/transfer")
    String transfer(@AuthenticationPrincipal CustomUserDetails userDetails, Transfer transfer){
        User user=userDetails.getUser();

        if(detailsService.moneyTransfer(user,transfer.getMoney(),transfer.getAccountNumber())){
            return "transfer_fail";
        }

        return "transfer_done";
    }
}
