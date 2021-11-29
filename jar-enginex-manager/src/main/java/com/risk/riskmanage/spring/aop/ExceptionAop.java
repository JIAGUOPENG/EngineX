package com.risk.riskmanage.spring.aop;

import com.alibaba.fastjson.JSONObject;
import com.risk.riskmanage.common.enums.ErrorCodeEnum;
import com.risk.riskmanage.common.exception.ApiException;
import com.risk.riskmanage.common.utils.ResponseEntityBuilder;
import com.risk.riskmanage.util.AccountSessionWrap;
import com.risk.riskmanage.util.RequestUtil;
import com.risk.riskmanage.util.SessionManager;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 异常处理
 */
@Order(1)
@Aspect
@Component
public class ExceptionAop {
    private static final Logger logger = LoggerFactory.getLogger(ExceptionAop.class);
    public static final String EDP = "execution(* com.risk.riskmanage.*.controller..*.*(..))";

    /**
     * 处理运行异常的切面
     */
    @Around(EDP)
    public Object deal(ProceedingJoinPoint pjp) throws Throwable {
        Object returnMessage = null;
        Long beginTimeMills = System.currentTimeMillis();
        String className = pjp.getTarget().getClass().getName();
        String methodName = pjp.getSignature().getName();
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes) ra;
        HttpServletRequest request = sra.getRequest();
        String addIp = RequestUtil.getClientIP(request);
        String requestMethod = request.getMethod();
        StringBuffer requestURL = request.getRequestURL();
        MethodSignature methodSignature = (MethodSignature) pjp.getSignature();

        AccountSessionWrap session = SessionManager.getSession();
        Long userId = session.getUser() != null && session.getUser().getUserId() != null ? session.getUser().getUserId() : 0;
        String reqUuid = null;
        String argsWrap = null;

        try {
            reqUuid = session.getTraceId();
            argsWrap = getParam(pjp.getArgs(), methodSignature.getParameterNames());
            logger.info("===>> 切面BEGIN: {} - {} {} enter {}.{} method, ### traceId:{} ###, request args: {}, userId:{} ===>>",
                    addIp, requestMethod, requestURL, className, methodName, reqUuid, argsWrap, userId);
            returnMessage = pjp.proceed();
        } catch (ApiException e1) {
            logger.info("方法[" + pjp.getSignature().getName() + "]发生业务异常Exception-{}", e1);
            returnMessage = ResponseEntityBuilder.buildErrorResponse(e1.errCode, e1.message);
        } catch (NullPointerException e2) {
            logger.error("方法[" + pjp.getSignature().getName() + "]发生运行时异常NullPointerException-{}", e2);
            returnMessage = ResponseEntityBuilder.buildErrorResponse(ErrorCodeEnum.NULL_POINT_EREXCEPTION.getCode(), ErrorCodeEnum.NULL_POINT_EREXCEPTION.getMessage());
        } catch (ClassCastException e2) {
            logger.error("方法[" + pjp.getSignature().getName() + "]发生运行时异常ClassCastException-{}", e2);
            returnMessage = ResponseEntityBuilder.buildErrorResponse(ErrorCodeEnum.CLASS_CAST_EXCEPTION.getCode(), ErrorCodeEnum.CLASS_CAST_EXCEPTION.getMessage());
        } catch (Exception e2) {
            logger.error("方法[" + pjp.getSignature().getName() + "]发生运行时异常Exception-{}", e2);
            returnMessage = ResponseEntityBuilder.buildErrorResponse(ErrorCodeEnum.SERVER_ERROR.getCode(), ErrorCodeEnum.SERVER_ERROR.getMessage());
        } catch (Throwable e) {
            logger.error("方法[" + pjp.getSignature().getName() + "]发生运行时异常Throwable-{}", e);
            returnMessage = ResponseEntityBuilder.buildErrorResponse(ErrorCodeEnum.SERVER_ERROR.getCode(), ErrorCodeEnum.SERVER_ERROR.getMessage());
        }
        logger.info("<<==== 切面END: exit {}.{} method,### traceId:{}, userId:{}, cost time: {} ms ###, returnResult:{} <<====",
                className, methodName, reqUuid, userId, (System.currentTimeMillis() - beginTimeMills), JSONObject.toJSONString(returnMessage));
        return returnMessage;

    }

    private String getParam(Object[] fieldValues, String[] filedNames) {
        if (fieldValues == null
                || fieldValues.length == 0
                || filedNames == null
                || filedNames.length == 0
                || fieldValues.length != filedNames.length) {
            return null;
        }
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < fieldValues.length; i++) {
            if (fieldValues[i] instanceof HttpServletRequest
                    || fieldValues[i] instanceof MultipartRequest
                    || fieldValues[i] instanceof HttpServletResponse
                    || fieldValues[i] instanceof HttpSession) {
                continue;
            }
            stringBuffer.append("{");
            if (i < filedNames.length) {
                stringBuffer.append(filedNames[i]);
                stringBuffer.append(":");
            }
            stringBuffer.append(JSONObject.toJSONString(fieldValues[i]));
            stringBuffer.append("},");
        }
        return stringBuffer.toString();
    }
}
