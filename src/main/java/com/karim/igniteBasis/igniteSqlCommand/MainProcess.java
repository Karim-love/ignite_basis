package com.karim.igniteBasis.igniteSqlCommand;

import com.karim.igniteBasis.igniteConnectCfg.IgniteLifeCycle;
import com.karim.igniteBasis.igniteSqlCommand.ddlCommand.IgniteSqlDdlCommand;
import com.karim.igniteBasis.igniteSqlCommand.dmlCommand.IgniteSqlDmlCommand;
import org.apache.ignite.client.ClientCache;

/**
 * Created by sblim
 * Date : 2021-11-16
 * Time : 오후 6:00
 */
public class MainProcess {

    private static final String TABLE_NAME = "Mapping_Data";
    private static final int INSERT_COUNT = 100000000;

    public static void main(String[] args) {

        IgniteSqlDmlCommand igniteSqlDmlCommand = new IgniteSqlDmlCommand();
        IgniteSqlDdlCommand igniteSqlDdlCommand = new IgniteSqlDdlCommand();
        IgniteLifeCycle igniteConnect = new IgniteLifeCycle();

        ClientCache<Object, Object> cache;

        // 접속정보
        cache = igniteConnect.IgniteConnect();

        // table create
        igniteSqlDdlCommand.setCreateTable(cache, TABLE_NAME);

        // data insert
        igniteSqlDmlCommand.setInsertData(cache, TABLE_NAME, INSERT_COUNT);

        // data update
        igniteSqlDmlCommand.setUpdate(cache, TABLE_NAME);

        // data select
        igniteSqlDmlCommand.getSelectAll(cache, TABLE_NAME);

        igniteConnect.IgniteClose();
    }
}
