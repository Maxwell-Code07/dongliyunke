package com.bjpowernode.result;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author hzz
 * @Date 2024-04-04 16:31
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class NameValue {

    private String name;
    private Integer value;
}
