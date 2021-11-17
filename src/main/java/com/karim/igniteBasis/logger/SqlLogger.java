package com.karim.igniteBasis.logger;

import ch.qos.logback.classic.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by sblim
 * Date : 2021-11-16
 * Time : 오후 6:30
 */
public class SqlLogger {

    private volatile static SqlLogger _instance = null;
    private Logger logger = (Logger) LoggerFactory.getLogger(SqlLogger.class);

    private SqlLogger() {
    }

    public static SqlLogger getInstance() {
        if( _instance == null ) {
            /* 제일 처음에만 동기화 하도록 함 */
            synchronized(SqlLogger.class) {
                if( _instance == null ) {
                    _instance = new SqlLogger();
                }
            }
        }
        return _instance;
    }

    public Logger getLogger() {
        return logger;
    }

    public static void main(String[] args) {
        SqlLogger.getInstance().getLogger();
    }

}
