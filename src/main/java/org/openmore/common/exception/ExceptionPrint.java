package org.openmore.common.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ExceptionPrint {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    //@Value("${debug}")
    @Value("${env}")
    private String debug;

    public <T extends Exception> void print(Class<?>clazz, T t){
        String className ="ExceptionPrint";
        if(null != clazz){
            className = clazz.getName();
        }
        className = clazz.getName();
        if(!debug.equals("env")){
            t.printStackTrace();
        }else{
            if(t instanceof OpenmoreException){
            logger.error(("==>from ["+className+"]"+((OpenmoreException) t).getMsg()));
            }else{
                logger.error(t.getMessage());
            }
        }
    }
}
