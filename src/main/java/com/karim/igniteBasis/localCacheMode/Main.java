package com.karim.igniteBasis.localCacheMode;


import com.karim.igniteBasis.localCacheMode.server.IgniteServer;

public class Main {

    public static void main(String[] args) throws InterruptedException {

            IgniteServer igniteServer = IgniteServer.getInstance();

            boolean first = true;

            if (first){

                igniteServer.putDate("key-1", "value-1");
                igniteServer.putDate("key-2", "value-2");

                Thread.sleep(10000);

                igniteServer.putDate("key-3", "value-3");
                igniteServer.putDate("key-4", "value-4");
                igniteServer.putDate("key-5", "value-5");

            }else {
                igniteServer.getAllData();
                igniteServer.getSize();
            }
        }
}