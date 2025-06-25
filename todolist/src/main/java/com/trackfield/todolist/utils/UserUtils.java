package com.trackfield.todolist.utils;

import com.trackfield.todolist.models.user.User;
import com.trackfield.todolist.models.user.UserType;

public class UserUtils {

    public static boolean isSeller(User user){
        return user.getUserType() == UserType.SELLER;
    }

    public static boolean isOwner(User user){
        return user.getUserType() == UserType.OWNER;
    }

    public static boolean isShopKeeper(User user){
        return user.getUserType() == UserType.SHOPKEEPER;
    }
}
