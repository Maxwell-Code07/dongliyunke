package com.bjpowernode.result;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @Author hzz
 * @Date 2024-03-23 18:58
 */
@AllArgsConstructor
@NoArgsConstructor
public enum DicEnum {

    APPELLATION("appellation"),

    SOURCE("source"),

    STATE("clueState"),

    INTENTIONSTATE("intentionState"),
    NEEDLOAN("needLoan"),

    PRODUCT("product")

    ;

    @Setter
    @Getter
    private String code;
}
