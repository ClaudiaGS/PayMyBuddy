package com.paymybuddy.PayMyBuddy.service.interfaces;

import com.paymybuddy.PayMyBuddy.model.ContactView;

import java.util.List;

public interface IContactViewService {
    
    /**
     *
     * @param userID
     * @return List<ContactView>
     */
    public List<ContactView> getContactViewList(int userID);
}
