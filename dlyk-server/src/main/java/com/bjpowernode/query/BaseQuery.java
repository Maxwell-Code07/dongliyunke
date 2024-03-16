package com.bjpowernode.query;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author hzz
 * @Date 2024-03-15 0:11
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class BaseQuery {

    private String token; // jwt

    private String filterSQL; // 数据权限的SQL过滤条件：tu.id = 2 或者 ta.owner_id = 2
}
