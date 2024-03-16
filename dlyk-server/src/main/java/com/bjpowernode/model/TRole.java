package com.bjpowernode.model;

import java.io.Serializable;
import lombok.Data;

/**
 * t_role
 * @author 
 */
@Data
public class TRole implements Serializable {
    private Integer id;

    private String role;

    private String roleName;

    private static final long serialVersionUID = 1L;
}