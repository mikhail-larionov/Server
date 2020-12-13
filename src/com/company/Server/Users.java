package com.company.Server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Users {
    private static final int NUMBER_OF_HANGARS = 5;
    private final Map<Integer, Connection> users = new HashMap<>();
    public void addNewUser(Connection connection) {
        int i = getFreeHangar();
        users.put(i, connection);
    }

    public Map<Integer, Connection> getUsers(){
        return users;
    }
    private int getFreeHangar(){
        Integer free = null;
        for (int i = 0; i < 5; i++) {
            if (!users.containsKey(i)) {
                free = i;
                break;
            }
        }
        if (free != null)
            return free;
        else
        throw new RuntimeException();
    }
}
