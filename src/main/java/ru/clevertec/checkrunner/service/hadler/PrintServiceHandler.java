package ru.clevertec.checkrunner.service.hadler;


import com.google.gson.Gson;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.clevertec.checkrunner.annotation.Log;
import ru.clevertec.checkrunner.service.PrintServiceInterface;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;



public class PrintServiceHandler implements InvocationHandler {
    private final PrintServiceInterface printService;
    private static final String EMPTY_STRING = "";

    public PrintServiceHandler(PrintServiceInterface printService) {
        this.printService = printService;
    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Logger logger = LogManager.getLogger(method.getDeclaringClass().getName());
        Gson parser = new Gson();
        Object invoke = method.invoke(printService, args);


        if (method.getAnnotation(Log.class) != null
                || printService.getClass()
                .getMethod(method.getName(), method.getParameterTypes())
                .getAnnotation(Log.class) != null) {

            String arguments = EMPTY_STRING;
            if (args != null) {
                arguments = parser.toJson(args);
            }
            String result = EMPTY_STRING;
            if (invoke != null) {
                result = parser.toJson(invoke);
            }
            logger.log(Level.DEBUG, "{} args={}", method.getName(), arguments);
            logger.log(Level.DEBUG, "{} result={}", method.getName(), result);
        }
        return invoke;
    }
}
