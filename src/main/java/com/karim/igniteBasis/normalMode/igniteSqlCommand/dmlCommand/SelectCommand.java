package com.karim.igniteBasis.normalMode.igniteSqlCommand.dmlCommand;

import com.karim.igniteBasis.define.TableDefine;
import com.karim.igniteBasis.normalMode.igniteConnectCfg.IgniteLifeCycle;
import org.apache.ignite.client.ClientCache;

/**
 * Created by sblim
 * Date : 2021-11-23
 * Time : 오후 1:46
 */
public class SelectCommand extends Thread{
    IgniteSqlDmlCommand igniteSqlDmlCommand = new IgniteSqlDmlCommand();
    IgniteLifeCycle igniteConnect = new IgniteLifeCycle();

    ClientCache<Object, Object> cache = igniteConnect.IgniteConnect();

    public void run(){
        while (true){
            try {
                Thread.sleep(5);
                igniteSqlDmlCommand.getRandomSelect(cache, TableDefine.TABLE_NAME);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
