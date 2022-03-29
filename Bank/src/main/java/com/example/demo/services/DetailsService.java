package com.example.demo.services;

import com.example.demo.WebSecurity.CustomUserDetails;
import com.example.demo.models.User;
import com.example.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class DetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepo;

    public DetailsService(){}

    public void encodePassword(User user){
        BCryptPasswordEncoder passwordEncoder=new BCryptPasswordEncoder();
        String encodedPassword=passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        userRepo.save(user);
    }
    public boolean userExist(String login){
        Optional<User> checkUser=userRepo.findByLogin(login);
        return checkUser.isPresent() ? true : false;
    }

    public UserDetails loadUserByUsername (String login) throws UsernameNotFoundException {
        Optional<User> user=userRepo.findByLogin(login);
        if(user.isEmpty()){
            throw new UsernameNotFoundException("User not found");
        }
        return new CustomUserDetails(user.get());
    }

    public boolean moneyTransfer(User loggedUser,int senderMoney , String accountNumber) {
        Optional<User> ReciveUser = userRepo.findByAccountNumber(accountNumber);

        if(ReciveUser.isEmpty()){
            return true;
        }

        loggedUser.setMoney(loggedUser.getMoney()-senderMoney);
        ReciveUser.get().setMoney(ReciveUser.get().getMoney()+senderMoney);
        userRepo.save(loggedUser);
        userRepo.save(ReciveUser.get());
        return  false;
    }

}
