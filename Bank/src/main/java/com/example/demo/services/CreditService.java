package com.example.demo.services;


import com.example.demo.models.Credit;
import com.example.demo.models.User;
import com.example.demo.repositories.CreditRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class CreditService {
    private final CreditRepository creditRepo;

    CreditService(CreditRepository creditRepo){
        this.creditRepo=creditRepo;
    }

    public void takeCredit(String creditName, int money, User user){
        Credit newCredit= new Credit(creditName,money, user);
        user.setMoney(user.getMoney()+money);
        creditRepo.save(newCredit);
    }
    public List<Credit> findByUser(User user){
        List<Credit> userCredits=creditRepo.findByUser(user);
        return userCredits;
    }
}
