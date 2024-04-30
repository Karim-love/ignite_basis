package com.karim.igniteBasis.localCacheMode;


import com.karim.igniteBasis.localCacheMode.server.IgniteServer;

public class Main {

    public static void main(String[] args) {

        IgniteServer.getInstance();
        IgniteServer.getInstance().putDate();
        IgniteServer.getInstance().getDate();
    }
}