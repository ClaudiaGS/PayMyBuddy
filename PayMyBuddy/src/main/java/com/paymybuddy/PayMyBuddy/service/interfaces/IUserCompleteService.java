package com.paymybuddy.PayMyBuddy.service.interfaces;

import com.paymybuddy.PayMyBuddy.model.UserComplete;

import java.util.List;

public interface IUserCompleteService {
    public UserComplete login(String email, String password);
    public List<UserComplete> readUserCompleteList();
}
