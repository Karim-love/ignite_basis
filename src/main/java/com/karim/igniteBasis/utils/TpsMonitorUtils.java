package com.karim.igniteBasis.utils;

import com.karim.igniteBasis.instance.CacheCommon;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by sblim
 * Date : 2021-12-10
 * Time : 오전 11:35
 */
public class TpsMonitorUtils {
    private static final Logger logger = LoggerFactory.getLogger(TpsMonitorUtils.class);

    public static void monitor() {
        logger.info("{} => {}" ,"[IGNITE_BASIS_TPS_MONITOR]", CacheCommon.getInstance().getTpsSize());
    }
}
