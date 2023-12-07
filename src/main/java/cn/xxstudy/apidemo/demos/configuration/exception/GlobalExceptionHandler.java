package cn.xxstudy.apidemo.demos.configuration.exception;

import cn.xxstudy.apidemo.demos.configuration.response.ResBody;
import cn.xxstudy.apidemo.demos.configuration.response.ResCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.buf.StringUtils;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @date: 2023/11/30 17:18
 * @author: TangRen
 * @remark:
 */
@Slf4j
@RestControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {


    /**
     * 自定义异常处理
     *
     * @param e
     * @return
     */
    @ExceptionHandler(ApiRuntimeException.class)
    public ResBody<String> apiRuntimeExceptionHandler(ApiRuntimeException e) {
        log.error("业务异常 code={}, BusinessException = {}", e.getCode(), e.getMessage(), e);
        return ResBody.failed(e.getCode(), e.getMsg());
    }

    /**
     * 空指针异常定义为前端传参错误，返回400
     *
     * @param e NullPointerException
     * @return
     */
    @ExceptionHandler(NullPointerException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResBody<String> nullPointerExceptionHandler(NullPointerException e) {
        log.error("空指针异常 NullPointerException ", e);
        return ResBody.failed(ResCode.FAILED_400.getCode(), ResCode.FAILED_400.getMsg());
    }

    /**
     * 校验错误异常处理 1
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResBody<String> handler(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        ObjectError error = bindingResult.getAllErrors().stream().findFirst().orElse(null);
        String messages = error == null ? "unknown" : error.getDefaultMessage();
        log.error("MethodArgumentNotValidException异常:-------------->{}", messages);
        return ResBody.failed(ResCode.FAILED_400.getCode(), messages);
    }

    /**
     * @Validated 校验错误异常处理 2
     */
    @ExceptionHandler(value = ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResBody<String> handler(ConstraintViolationException e) {
        List<String> msgList = new ArrayList<>();
        for (ConstraintViolation<?> constraintViolation : e.getConstraintViolations()) {
            msgList.add(constraintViolation.getMessage());
        }
        String messages = StringUtils.join(msgList, ';');
        log.error("entity格式校验异常:-------------->{}", messages);
        return ResBody.failed(ResCode.FAILED_400.getCode(), messages);
    }

    /**
     * @Validated 校验错误异常处理 3
     */
    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResBody<String> bindExceptionHandler(BindException e) {
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        List<String> msgList = fieldErrors.stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());
        String messages = StringUtils.join(msgList, ';');
        log.error("Validation格式校验异常:-------------->{}", messages);
        return ResBody.failed(ResCode.FAILED_400.getCode(), ResCode.FAILED_400.getMsg());
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResBody<String> methodNotAllowedHandler() {
        return ResBody.failed(ResCode.FAILED_405.getCode(), ResCode.FAILED_405.getMsg());
    }


    /**
     * 处理404异常
     *
     * @param e NoHandlerFoundException
     * @return
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResBody<String> noHandlerFoundException(HttpServletRequest req, Exception e) {
        log.error("404异常 NoHandlerFoundException, method = {}, path = {} ", req.getMethod(), req.getServletPath(), e);
        return ResBody.failed(ResCode.FAILED_404.getCode(), ResCode.FAILED_404.getMsg());
    }

    /**
     * 处理其他异常
     *
     * @param e otherException
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResBody<String> exception(Exception e) {
        log.error("未知异常 exception = {}", e.getMessage(), e);
        return ResBody.failed(ResCode.FAILED_500.getCode(), ResCode.FAILED_500.getMsg());
    }


}
