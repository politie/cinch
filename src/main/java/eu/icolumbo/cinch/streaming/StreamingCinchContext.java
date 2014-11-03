package eu.icolumbo.cinch.streaming;

import eu.icolumbo.cinch.CinchContext;
import eu.icolumbo.cinch.SpringContext;
import org.apache.spark.api.java.function.VoidFunction;
import org.springframework.context.support.AbstractApplicationContext;

/**
 * @author Paul Middelkoop
 */
public class StreamingCinchContext extends CinchContext {

    public static CinchContext springJavaConfiguration(Class<?> springConfigurationClass) {
        return new StreamingCinchContext(springConfigurationClass);
    }

    private StreamingCinchContext(Class<?> springConfigurationClass) {
        super(springConfigurationClass);
    }

    public void executeJob() {
        AbstractApplicationContext springContext = SpringContext.getContext(getSpringConfigurationClass());
        StreamingCinchJob cinchJob = springContext.getBean(StreamingCinchJob.class);

        cinchJob.execute(this);
    }

    public <T> ForeachRddFunction<T> foreachRddFunction(Class<? extends VoidFunction<T>> springBeanClass) {
        return new ForeachRddFunction<>(voidFunction(springBeanClass));
    }
}
