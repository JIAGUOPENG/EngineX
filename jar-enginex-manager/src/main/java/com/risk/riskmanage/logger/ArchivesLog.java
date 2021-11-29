package com.risk.riskmanage.logger;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * ClassName:ArchivesLog <br/>
 * Description: 日志操作类型注解. <br/>
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})  
@Retention(RetentionPolicy.RUNTIME)  
@Documented  
public @interface ArchivesLog {

    public String operationType() default "";  

    public String operationName() default ""; 
    
}
