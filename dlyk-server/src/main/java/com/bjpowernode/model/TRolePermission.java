package com.bjpowernode.model;

import java.io.Serializable;
import lombok.Data;

/**
 * t_role_permission
 * @author 
 */
@Data
public class TRolePermission implements Serializable {
    private Integer id;

    private Integer roleId;

    private Integer permissionId;

    private static final long serialVersionUID = 1L;
}