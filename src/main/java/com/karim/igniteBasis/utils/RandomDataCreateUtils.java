package com.karim.igniteBasis.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by sblim
 * Date : 2021-11-17
 * Time : 오전 11:11
 */
public class RandomDataCreateUtils {
    static List<String> name = new ArrayList<String>();
    static List<String> firstName = new ArrayList<String>();

    public static Object name() {
        name = Arrays.asList("지우", "민준", "서윤", "서연", "서준", "민서", "서현", "하윤", "도윤", "시우",
                "주원", "연우", "은우", "예준", "하은", "서은", "서진", "윤서", "지후", "세인",
                "소연", "수진", "동원" , "수빈", "유이","서은","서희","지은","지아","은우","진슬",
                "소유","지인","지안","가희","기훈","지훈","이안","이인","이진","진희","진후","지윤","대훈","도지","비토",
                "주희","지원","민후","민우","민주","민정","문희",
                "규인", "진하", "세슬" ,"경훈", "지효", "보아" ,"현지", "규리", "지우", "이슬"
        );
        Collections.shuffle(name);
        return name;
    }

    public static Object firstName() {
        firstName = Arrays.asList("김","이","박","최","정","강","조","윤","장","임","한",
                "오","서","신","권","황","안","송","전","홍","유","고","문",
                "양","손","배","조","백","허","심","주","전"
        );
        Collections.shuffle(firstName);
        return firstName;
    }
}
