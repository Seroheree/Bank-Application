package com.example.demo.models;

import javax.persistence.*;

@Entity
@Table(name="credits")
public class Credit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String creditName;
    private int money;

    @ManyToOne
    @JoinColumn(name="userID")
    private User user;
    public Credit(){}
    public Credit(String creditName, int money, User user){
        this.creditName=creditName;
        this.money=money;
        this.user=user;
    }
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCreditName() {
        return creditName;
    }

    public void setCreditName(String creditName) {
        this.creditName = creditName;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }
}
