package eu.icolumbo.cinch;

import org.apache.spark.api.java.function.VoidFunction;

/**
 * @author Paul Middelkoop
 */
public class SpringVoidFunction<T> implements VoidFunction<T> {

    private final Class<?> springConfigurationClass;
    private final Class<? extends VoidFunction<T>> springBeanClass;

    SpringVoidFunction(Class<?> springConfigurationClass, Class<? extends VoidFunction<T>> springBeanClass) {
        this.springConfigurationClass = springConfigurationClass;
        this.springBeanClass = springBeanClass;
    }
    
    @Override
    public void call(T t) throws Exception {
        VoidFunction<T> function = SpringContext.getContext(springConfigurationClass).getBean(springBeanClass);
        function.call(t);
    }
}
