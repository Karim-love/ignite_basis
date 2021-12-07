package com.karim.igniteBasis.normalMode.igniteSqlCommand;

import com.karim.igniteBasis.define.TableDefine;
import com.karim.igniteBasis.normalMode.igniteConnectCfg.IgniteLifeCycle;
import com.karim.igniteBasis.normalMode.igniteSqlCommand.ddlCommand.IgniteSqlDdlCommand;
import com.karim.igniteBasis.normalMode.igniteSqlCommand.dmlCommand.IgniteSqlDmlCommand;
import com.karim.igniteBasis.normalMode.igniteSqlCommand.dmlCommand.InsertCommand;
import com.karim.igniteBasis.normalMode.igniteSqlCommand.dmlCommand.SelectCommand;
import org.apache.ignite.client.ClientCache;

/**
 * Created by sblim
 * Date : 2021-11-16
 * Time : 오후 6:00
 */
public class MainProcess {
    private static String IGNITE_COMMAND_TEST_MODE = "thread";

    public static void main(String[] args) {

        IgniteSqlDmlCommand igniteSqlDmlCommand = new IgniteSqlDmlCommand();
        IgniteSqlDdlCommand igniteSqlDdlCommand = new IgniteSqlDdlCommand();
        IgniteLifeCycle igniteConnect = new IgniteLifeCycle();

        ClientCache<Object, Object> cache;

        if (IGNITE_COMMAND_TEST_MODE.equals("thread")){
            /**
             * 2021-11-23
             * 1. 1억건 insert
             * 2. 1초마다 1건 랜덤 select
             * */

            InsertCommand insertCommand = new InsertCommand();
            SelectCommand selectCommand = new SelectCommand();

            insertCommand.start();
            selectCommand.start();
        }else {
            // 접속정보
            cache = igniteConnect.IgniteConnect();

            // table create
            igniteSqlDdlCommand.setCreateTable(cache, TableDefine.TABLE_NAME);

            // data insert
            igniteSqlDmlCommand.setInsertData(cache, TableDefine.TABLE_NAME, TableDefine.INSERT_COUNT);

            // data update
            igniteSqlDmlCommand.setUpdate(cache, TableDefine.TABLE_NAME);

            // data select
            igniteSqlDmlCommand.getSelectAll(cache, TableDefine.TABLE_NAME);
        }

        // ignite 종료
        igniteConnect.IgniteClose();
    }
}
