package com.company;

import com.company.Server.Server;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Server server = new Server();
        server.start();
        server.accept();
    }



}

