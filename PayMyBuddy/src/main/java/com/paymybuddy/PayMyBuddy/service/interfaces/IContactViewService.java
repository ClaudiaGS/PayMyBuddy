package com.paymybuddy.PayMyBuddy.service.interfaces;

import com.paymybuddy.PayMyBuddy.model.ContactView;

import java.util.List;

public interface IContactViewService {
    public List<ContactView> getContactViewList(int userID);
}
