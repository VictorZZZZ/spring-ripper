package com.zvv.borisov.post.processors;

import com.zvv.borisov.playground.annotations.Profiling;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

@Component
public class ProfilingHandlingBeanPostProcessor implements BeanPostProcessor {
    private Map<String, Class> map = new HashMap<>();

    /**
     * В методе Before запоминаем над каким бином стоит аннотация {@link Profiling}
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Class<?> beanClass = bean.getClass();
        if(beanClass.isAnnotationPresent(Profiling.class)){
            map.put(beanName, beanClass);
        }
        return bean;
    }

    /**
     * В методе After создаём прокси, который изменит выполнение методов в бине.
     * В данном случае посчитает время на выполнение метода.
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Class beanClass = map.get(beanName);
        if(beanClass != null){
            return Proxy.newProxyInstance(beanClass.getClassLoader(), beanClass.getInterfaces(), new InvocationHandler() {
                @Override
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                    System.out.println("Profiling method...");
                    long before = System.nanoTime();
                    Object retVal = method.invoke(bean, args);
                    long after = System.nanoTime();
                    System.out.println("Stop Profiling method...");
                    System.out.println(after - before + "nanoSec");
                    return retVal;
                }
            });
        }
        return bean;
    }
}
