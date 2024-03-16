package com.bjpowernode.aspect;

import com.bjpowernode.commons.DataScope;
import com.bjpowernode.constant.Constants;
import com.bjpowernode.model.TUser;
import com.bjpowernode.query.BaseQuery;
import com.bjpowernode.util.JWTUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.lang.reflect.Method;
import java.util.List;

/**
 * @Author hzz
 * @Date 2024-03-16 15:37
 */
@Aspect
@Component
public class DataScopeAspect {

    // aspectJ 实现aop

    // 切入点
    @Pointcut(value = "@annotation(com.bjpowernode.commons.DataScope)")
    public void pointCut(){

    }

    @Around(value = "pointCut()")
    public Object process(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature)joinPoint.getSignature();

        // 拿到方法上的注解
        DataScope dataScope = methodSignature.getMethod().getDeclaredAnnotation(DataScope.class);

        String tableAlias = dataScope.tableAlias();
        String tableField = dataScope.tableField();

        // 在spring容器中，可以拿到当前请求的request对象
        HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();

        String token = request.getHeader(Constants.TOKEN_NAME);
        // 从token解析出该用户是管理员还是普通用户
        TUser tUser = JWTUtils.parseUserFromJWT(token);

        // 拿到用户的角色
        List<String> roleList =  tUser.getRoleList();

        if(!roleList.contains("admin")){ // 不包含admin角色，则只查当前用户自己的数据，否则查所有数据
            // 只查用户自己的数据
            Object params = joinPoint.getArgs()[0];// 拿方法的第一个参数
            if(params instanceof BaseQuery){
                BaseQuery query = (BaseQuery)params;

                // select * from t_user tu where tu.id = 2 (普通用户：于嫣)
                query.setFilterSQL(" and " + tableAlias + "." + tableField + "=" + tUser.getId());
            }
        }

        System.out.println("目标方法执行之前");
        Object result = joinPoint.proceed();
        System.out.println("目标方法执行之后");
        return result;
    }

}
