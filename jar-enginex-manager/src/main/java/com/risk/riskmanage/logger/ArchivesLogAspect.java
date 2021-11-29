package com.risk.riskmanage.logger;

import com.risk.riskmanage.common.constants.OpTypeEnum;
import com.risk.riskmanage.logger.mapper.LoggerMapper;
import com.risk.riskmanage.logger.model.Logger;
import com.risk.riskmanage.system.model.User;
import com.risk.riskmanage.util.RequestUtil;
import com.risk.riskmanage.util.SessionManager;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;

@Aspect
@Component
public class ArchivesLogAspect {

	   @Resource
       private LoggerMapper loggerMapper;
	
	    private Date startTime; // 开始时间  
	    private Date  endTime; // 结束时间  
	    private HttpServletRequest request = null;
      
       @Pointcut("@annotation(com.risk.riskmanage.logger.ArchivesLog))")
       public void controllerAspect() {  
       }   

       @Before("controllerAspect()")  
       public void doBefore() { 
    	   request = getHttpServletRequest();
   		   startTime = new Date(); 
       }  
         
       @AfterReturning(pointcut="controllerAspect()", returning="returnValue")  
       public  void doAfter(JoinPoint joinPoint,Object returnValue) {  
           handleLog(joinPoint,null,returnValue);  
       }  
         
       @AfterThrowing(value="controllerAspect()",throwing="e")  
       public void doAfter(JoinPoint joinPoint, Exception e) {  
           handleLog(joinPoint, e,null);  
       }  
       

	 /** 
     * @Description: 方法调用后触发   记录结束时间  
     *
     * @param joinPoint 
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public  void handleLog(JoinPoint joinPoint,Exception e,Object retValue) { 
    	try {
			User user = SessionManager.getLoginAccount();
			String targetName = joinPoint.getTarget().getClass().getName();  
			String methodName = joinPoint.getSignature().getName();  
			Object[] arguments = joinPoint.getArgs();  
			Class targetClass  = Class.forName(targetName);
			Method[] methods = targetClass.getMethods();
			String operationName = "";
			for (Method method : methods) {  
			    if (method.getName().equals(methodName)) {  
					Class[] clazzs = method.getParameterTypes();  
			        if (clazzs!=null&&clazzs.length == arguments.length&&method.getAnnotation(ArchivesLog.class)!=null) {  
			        	operationName = method.getAnnotation(ArchivesLog.class).operationType();
			            break;  
			        }  
			    }  
			}
			
			endTime = new Date();
			Logger log = new Logger();
			log.setStartTime(startTime);
			log.setEndTime(endTime);
			if(user!=null){
				log.setOpUserId(user.getUserId());
				log.setOrganId(user.getOrganId());
			}
			if (request.getScheme()!=null){
				log.setRequestPath(request.getRequestURL().toString());
			}else {
				log.setRequestPath("npe");
			}
			log.setMethod(methodName);
			
			//获取请求参数
			if(!operationName.equals("登入")){
				Map<String,String[]> map = request.getParameterMap();
				StringBuffer sb = new StringBuffer();
				sb.append("{");
				for(String name : map.keySet()){ 
					String[] values=map.get(name); 
					String val = Arrays.toString(values);
					sb.append(name+"="+val.substring(1,val.length()-1)).append(",");
				}
				sb = sb.deleteCharAt(sb.length()-1);
				sb.append("}");
				log.setRequestParam(sb.toString());
			}

			//获取响应参数
			if(e !=null){
				 log.setResponseParam(e.getMessage());
			}else{
				 if(retValue!=null){
					 log.setResponseParam(retValue.toString());
				 }
			}
			
			for (OpTypeEnum examType : OpTypeEnum.values()) {
			    if (operationName.equals(examType.getType()) ) {
			    	log.setOpType(examType.getValue());
			    }
			}
			log.setOpName(operationName);
			log.setIp(RequestUtil.getClientIP(request));
			loggerMapper.insertSelective(log);
			
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SecurityException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    }
	    
	/**
	 * @Description: 获取request
	 *
	 * @param
	 * @return HttpServletRequest
	 */
	public HttpServletRequest getHttpServletRequest(){
		RequestAttributes ra = RequestContextHolder.getRequestAttributes();
		ServletRequestAttributes sra = (ServletRequestAttributes)ra;
		HttpServletRequest request = sra.getRequest();
		return request;
	}
	    
}
