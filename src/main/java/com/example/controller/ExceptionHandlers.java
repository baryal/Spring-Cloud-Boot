package com.example.controller;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javassist.NotFoundException;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.ModelAndView;

import javax.jms.JMSException;
import javax.persistence.EntityNotFoundException;
import javax.security.auth.login.AccountLockedException;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;

@ControllerAdvice(basePackages = {"com.example.controller"} )
@Slf4j
public class ExceptionHandlers implements MessageSourceAware {


    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Throwable.class)
    @ResponseBody
    public ErrorResponse handleThrowable(final Throwable ex) {
        log.error("Unexpected error", ex);
        return new ErrorResponse("INTERNAL_SERVER_ERROR. An unexpected internal server error occured. This is custom message.", ex);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    @ResponseBody
    public ErrorResponse handleNotFoundThrowable(final Throwable ex) {
        log.error("Unexpected error", ex);
        return new ErrorResponse("NOT FOUND. An unexpected internal server error occured. This is custom message.", ex);
    }

    @Data
    public class ErrorResponse {
        private String message;

        @JsonIgnore
        private Throwable exception;

        public ErrorResponse(String message, Throwable exception) {
            this.message = message;
            this.exception = exception;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public Throwable getException() {
            return exception;
        }

        public void setException(Throwable exception) {
            this.exception = exception;
        }
    }

    /**
     * Spring automatically binds simple data (Strings, int, float, etc.) into properties of your command bean. However, what happens when the data is more complex for example,
     * what happens when you want to capture a String in “Jan 20, 1990” format and have Spring create a Date object from it as part of the binding operation. For this work,
     * you need to inform Spring Web MVC to use PropertyEditor instances as part of the binding process :
     *
     *Before, you had to resort to manually parsing the date:
     *  public void webmethod(@RequestParam("date") String strDate) {
     *  Date date = ... // manually parse the date
     *  }
     *
     * Now you can get the parsed date directly:
     * public void webmethod(@RequestParam("date") Date date) {
     *
     *  }
     * @param binder
     */
    @InitBinder
    public void dataBinding(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, "dob", new CustomDateEditor(dateFormat, true));
    }
    @ModelAttribute
    public void globalAttributes(Model model) {
        model.addAttribute("msg", "Welcome to My World!");
    }

    @ExceptionHandler(FileNotFoundException.class)
    public ModelAndView myError(Exception exception) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("exception", exception);
        mav.setViewName("error");
        return mav;
    }


    //private MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();

    //private static final Logger logger = LogManager.getLogger();
    private static final String DEFAULT_BIND_EXCPTION_MESSAGE = "Input parameter format issue, unable to determine which field.\u0020";

    /*@Autowired
    BeanValidationHelper beanValidationHelper;*/

   /* @ResponseStatus(value=HttpStatus.NOT_FOUND)
    @ExceptionHandler(EntityNotFoundException.class )
    @ResponseBody
    public ErrorResponse handleEntityNotFoundException(Throwable error){
        return new ErrorResponse(error.getMessage(), error);
    }*/

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(AccountLockedException.class)
    @ResponseBody
    public ErrorResponse handleAccountLockedException(Throwable error) {
        return new ErrorResponse(error.getMessage(), error);
    }

    //@ResponseStatus(HttpStatus.UNAUTHORIZED)
    //@ExceptionHandler(LockedException.class)
    @ResponseBody
    public ErrorResponse handleLockedException(Throwable error) {
        return new ErrorResponse(error.getMessage(), error);
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(SecurityException.class)
    @ResponseBody
    public ErrorResponse handleSecurityException(Throwable error) {
        return new ErrorResponse(error.getMessage(), error);
    }

  /*  @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(InvalidCredentialsException.class)
    @ResponseBody
    public ErrorResponse handleInvalidCredentialsException(Throwable error) {
        return new ErrorResponse(error.getMessage(), error);
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(PasswordExpiredException.class)
    @ResponseBody
    public ErrorResponse handlePasswordExpiredException(Throwable error) {
        return new ErrorResponse(error.getMessage(), error);
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(ResetPasswordException.class)
    @ResponseBody
    public ErrorResponse handleResetPasswordException(Throwable error) {
        return new ErrorResponse(error.getMessage(), error);
    }

    @ResponseStatus(HttpStatus.UPGRADE_REQUIRED)
    @ExceptionHandler(MinimumVersionException.class)
    @ResponseBody
    public ErrorResponse handleMinimumVersionException(Throwable error) {
        return new ErrorResponse(error.getMessage(), error);
    }*/

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({IllegalArgumentException.class})
    @ResponseBody
    public ErrorResponse handleIllegalArgumentException(Throwable error) {
        return new ErrorResponse(error.getMessage(), error);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({HttpMessageNotReadableException.class})
    @ResponseBody
    public ErrorResponse handleHttpMessageNotReadableException(Throwable error) {
        return new ErrorResponse(error.getMessage(), error);
    }

    /*
        Need to dynamically get the statusCode and message out of the HttpClientErrorException in order to set the correct ResponseStatus and message
     */
    @ExceptionHandler({HttpClientErrorException.class})
    @ResponseBody
    public ResponseEntity<String> handleHttpClientErrorException(Throwable error) {
        String messageToReturn = "{\"message\":\"" + error.getMessage() + "\"}";
        MultiValueMap<String,String> headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_TYPE, "application/json;charset=UTF-8");
        return new ResponseEntity<>(messageToReturn, headers, ((HttpClientErrorException) error).getStatusCode());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({MissingServletRequestParameterException.class})
    @ResponseBody
    public ErrorResponse handleMissingServletRequestParameterException(Throwable error) {
        return new ErrorResponse(error.getMessage(), error);
    }

    //@ResponseStatus(HttpStatus.UNAUTHORIZED)
    //@ExceptionHandler(AccessDeniedException.class)
    @ResponseBody
    public ErrorResponse handleAccessDeniedException(Throwable error) {
        return new ErrorResponse(error.getMessage(), error);
    }

    /*@ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({ConstraintViolationException.class})
    @ResponseBody
    public ErrorResponse handleConstrianViolationException(Throwable error) {
        ConstraintViolationException constraintViolationException = (ConstraintViolationException) error;
        return new ErrorResponse(createConstraintViolationMessage(constraintViolationException), error);
    }*/

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({ValidationException.class})
    @ResponseBody
    public ErrorResponse handleValidationException(Throwable error) {
        return new ErrorResponse(error.getMessage(), error);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseBody
    public ErrorResponse handleMethodArgumentNotValidException(Throwable error) {
        return new ErrorResponse(error.getMessage(), error);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BindException.class)
    @ResponseBody
    public ErrorResponse handleBindException(BindException error) {
        List<ObjectError> errors = error.getAllErrors();
        StringBuilder errorMessage = new StringBuilder();
        for(org.springframework.validation.ObjectError objectError : errors) {
            try {
                if (!StringUtils.isEmpty(objectError.getDefaultMessage())) {
                    errorMessage.append(objectError.getDefaultMessage());
                } else {
                    //errorMessage.append(this.messages.getMessage(objectError.getCodes()[0]));
                }
            } catch (NoSuchMessageException e) {
                //We do not want to throw because unable to find message
                log.error("Missing message in message properties", e);
                errorMessage.append(DEFAULT_BIND_EXCPTION_MESSAGE);
            }
        }
        return new ErrorResponse(errorMessage.toString(), error);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({NumberFormatException.class})
    @ResponseBody
    public ErrorResponse handleNumberFormatException(Throwable error) {
        return new ErrorResponse("Invalid number", error);
    }

   /* protected String createConstraintViolationMessage(ConstraintViolationException constraintViolationException) {
        Set<ConstraintViolation<Object>> constraintViolations = (Set<ConstraintViolation<Object>>)(Set<?>) constraintViolationException.getConstraintViolations();
        return beanValidationHelper.buildMessageFromConstraintViolations(constraintViolations);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(EntityLoadException.class)
    @ResponseBody
    public ErrorResponse handleEntityLoadException(Throwable error) {
        return new ErrorResponse(error.getMessage(), error);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(EntityRuntimeException.class)
    @ResponseBody
    public ErrorResponse handleEntityRuntimeException(Throwable error) {
        return new ErrorResponse(error.getMessage(), error);
    }*/

    /*@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(EntityDeactivateException.class)
    @ResponseBody
    public ErrorResponse handleEntityDeactivateException(Throwable error) {
        return new ErrorResponse(error.getMessage(), error);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(EntityUpdateException.class)
    @ResponseBody
    public ErrorResponse handleEntityUpdateException(Throwable error) {
        return new ErrorResponse(error.getMessage(), error);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(ClassNotFoundException.class)
    @ResponseBody
    public ErrorResponse handleClassNotFoundException(Throwable error) {
        return new ErrorResponse(error.getMessage(), error);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(EntitySaveException.class)
    @ResponseBody
    public ErrorResponse handleEntitySaveException(Throwable error) {
        return new ErrorResponse(error.getMessage(), error);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(PrimaryProviderException.class)
    @ResponseBody
    public ErrorResponse handlePrimaryProviderException(Throwable error) {
        return new ErrorResponse(error.getMessage(), error);
    }*/

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(TypeMismatchException.class)
    @ResponseBody
    public ErrorResponse handleTypeMismatchException(Throwable error) {
        return new ErrorResponse(error.getMessage(), error);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(IllegalStateException.class)
    @ResponseBody
    public ErrorResponse handleIllegalStateException(Throwable error) {
        return new ErrorResponse(error.getMessage(), error);
    }

    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseBody
    public ErrorResponse handleHttpRequestMethodNotSupportedException(Throwable error) {
        return new ErrorResponse(error.getMessage(), error);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(JMSException.class)
    @ResponseBody
    public ErrorResponse handleJmsException(Throwable error) {
        log.error("JMS messaging issue ", error);
        return new ErrorResponse(error.getMessage(), error);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(NullPointerException.class)
    @ResponseBody
    public ErrorResponse handleNullPointerException(Throwable error) {
        log.error("NullPointer Exception DO NOT IGNORE", error);
        return new ErrorResponse(error.getMessage(), error);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(DataRetrievalFailureException.class)
    @ResponseBody
    public ErrorResponse handleDataRetrievalFailureException(Throwable error) {
        return new ErrorResponse(error.getMessage(), error);
    }

   /* @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Throwable.class)
    @ResponseBody
    public ErrorResponse defaultErrorHandler(Throwable error) {
        return new ErrorResponse(error.getMessage(), error);
    }*/

    public void setMessageSource(MessageSource messageSource) {
        //this.messages = new MessageSourceAccessor(messageSource);
    }
}
