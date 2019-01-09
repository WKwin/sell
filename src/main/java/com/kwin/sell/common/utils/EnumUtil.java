package com.kwin.sell.common.utils;

import com.kwin.sell.common.enums.CodeEnum;

/**
 * 
 * @author Kwin
 *
 */
public class EnumUtil {

    public static <T extends CodeEnum> T getByCode(Integer code, Class<T> enumClass) {
        for (T each: enumClass.getEnumConstants()) {
            if (code.equals(each.getCode())) {
                return each;
            }
        }
        return null;
    }
}
