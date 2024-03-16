package com.bjpowernode.model;

import java.io.Serializable;
import lombok.Data;

/**
 * t_permission
 * @author 
 */
@Data
public class TPermission implements Serializable {
    private Integer id;

    private String name;

    private String code;

    private String url;

    private String type;

    private Integer parentId;

    private Integer orderNo;

    private String icon;

    private static final long serialVersionUID = 1L;
}