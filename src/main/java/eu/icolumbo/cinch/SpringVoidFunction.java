package eu.icolumbo.cinch;

import org.apache.spark.api.java.function.VoidFunction;

/**
 * @author Paul Middelkoop
 */
public class SpringVoidFunction<T> implements VoidFunction<T> {

    private final Class<?> springConfigurationClass;
    private final Class<? extends VoidFunction> springBeanClass;

    SpringVoidFunction(Class<?> springConfigurationClass, Class<? extends VoidFunction> springBeanClass) {
        this.springConfigurationClass = springConfigurationClass;
        this.springBeanClass = springBeanClass;
    }
    
    @Override
    public void call(T t) throws Exception {
        VoidFunction function = SpringContext.getContext(springConfigurationClass).getBean(springBeanClass);
        function.call(t);
    }
}
