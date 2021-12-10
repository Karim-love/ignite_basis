package com.karim.igniteBasis.clientMode;

import ch.qos.logback.classic.Logger;
import com.karim.igniteBasis.clientMode.instance.IgniteThickClientInstance;
import com.karim.igniteBasis.logger.SqlLogger;

/**
 * Created by sblim
 * Date : 2021-12-07
 * Time : 오후 5:02
 */
public class MainProcess {

    private static Logger sqlLogger = SqlLogger.getInstance().getLogger();

    public static void main(String[] args) {

        IgniteThickClientInstance.getInstance();
        IgniteThickClientInstance.getInstance().selectTableAll();
    }
}
