package com.karim.igniteBasis.igniteSqlCommand.ddlCommand;

import ch.qos.logback.classic.Logger;
import com.karim.igniteBasis.logger.SqlLogger;
import com.karim.igniteBasis.utils.LoggerUtils;
import org.apache.ignite.cache.query.SqlFieldsQuery;
import org.apache.ignite.client.ClientCache;

/**
 * Created by sblim
 * Date : 2021-11-16
 * Time : 오후 6:03
 */
public class IgniteSqlDdlCommand {
    private Logger sqlLogger = SqlLogger.getInstance().getLogger();

    public void setCreateTable(ClientCache cache, String table){

        //select 쿼리문 작성
        String ddlQry = "CREATE TABLE " + table + "(" +
                "USERID VARCHAR," +
                "USERNAME VARCHAR," +
                "USERINT INTEGER," +
                "USERDOUBLE DOUBLE," +
                "CONSTRAINT USERID PRIMARY KEY (USERID)" +
                ");";

        SqlFieldsQuery qry = new SqlFieldsQuery(ddlQry);
        qry.setSchema("PUBLIC");

        try {
            cache.query(qry).getAll();
        }catch (Exception e){
            sqlLogger.error("{} => {}", "ignite setCreateTable exception", LoggerUtils.getStackTrace(e));
        }
    }
}
