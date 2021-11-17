package com.karim.igniteBasis.igniteSqlCommand.dmlCommand;

import ch.qos.logback.classic.Logger;
import com.karim.igniteBasis.logger.SqlLogger;
import com.karim.igniteBasis.utils.LoggerUtils;
import com.karim.igniteBasis.utils.RandomDataCreateUtils;
import org.apache.ignite.cache.query.SqlFieldsQuery;
import org.apache.ignite.client.ClientCache;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sblim
 * Date : 2021-11-11
 * Time : 오후 2:46
 */
public class IgniteSqlDmlCommand {
    private Logger sqlLogger = SqlLogger.getInstance().getLogger();
    static List<String> name = new ArrayList<String>();
    static List<String> firstName = new ArrayList<String>();

    Timestamp timestamp = new Timestamp(System.currentTimeMillis());

    public void getSelectAll(ClientCache cache, String table){
        List<?> resultList;

        //select 쿼리문 작성
        SqlFieldsQuery qry = new SqlFieldsQuery("SELECT * FROM " + table + " WHERE USERID='1'");

        System.out.println("시작 : " + timestamp);

        try {
            // 쿼리값 cache에 있는 데이터 전부 가져오기
            resultList = cache.query(qry).getAll();
            System.out.println(resultList.get(0));
        }catch (Exception e){
            sqlLogger.error("{} => {}", "ignite setInsertData exception", LoggerUtils.getStackTrace(e));
        }

        System.out.println("끝 : " + timestamp);

    }

    public void setInsertData(ClientCache cache, String table, int count){

        // insert 쿼리문 작성
        for (int i=0; i<count; i++){
            // 이름 랜덤 생성
            name = (List<String>) RandomDataCreateUtils.name();
            firstName = (List<String>) RandomDataCreateUtils.firstName();

            String dmlQry = "INSERT INTO " + table + " (USERID, USERNAME, USERINT, USERDOUBLE) VALUES ('"+ i +"','"+ firstName.get(0)+name.get(0) +"',"+i+","+ i +".1)";
            SqlFieldsQuery qry = new SqlFieldsQuery(dmlQry);

            try {
                cache.query(qry).getAll();
            }catch (Exception e){
                sqlLogger.error("{} => {}", "ignite setInsertData exception", LoggerUtils.getStackTrace(e));
            }
        }
        System.out.println("END");
    }

    public void setUpdate(ClientCache cache, String table){

        // insert 쿼리문 작성
        String dmlQry = "UPDATE " + table + " SET USERNAME = '임수빈', USERINT = 1, USERDOUBLE = 0.0 WHERE USERID = '1' ";
        SqlFieldsQuery qry = new SqlFieldsQuery(dmlQry);

        try {
            cache.query(qry).getAll();
        }catch (Exception e){
            sqlLogger.error("{} => {}", "ignite setUpdate exception", LoggerUtils.getStackTrace(e));
        }
    }
}
