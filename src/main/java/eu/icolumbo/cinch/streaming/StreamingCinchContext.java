package eu.icolumbo.cinch.streaming;

import eu.icolumbo.cinch.CinchContext;
import eu.icolumbo.cinch.SpringContext;
import org.apache.spark.api.java.function.VoidFunction;
import org.springframework.context.support.AbstractApplicationContext;

/**
 * @author Paul Middelkoop
 */
public class StreamingCinchContext extends CinchContext {

	/**
	 * Create a new cinch context,
	 */
    public static CinchContext springJavaConfiguration(Class<?> springConfigurationClass) {
        return new StreamingCinchContext(springConfigurationClass);
    }

    private StreamingCinchContext(Class<?> springConfigurationClass) {
        super(springConfigurationClass);
    }

	/**
	 * Executes your streaming job from the spring context.
	 */
    public void executeJob() {
        AbstractApplicationContext springContext = SpringContext.getContext(getSpringConfigurationClass());
        StreamingCinchJob cinchJob = springContext.getBean(StreamingCinchJob.class);

        cinchJob.execute(this);
    }

	/**
	 * Create a new ForeachRddFunction, which implements Spark's VoidFunction interface.
	 */
    public <T> ForeachRddFunction<T> foreachRddFunction(Class<? extends VoidFunction<T>> springBeanClass) {
        return new ForeachRddFunction<>(voidFunction(springBeanClass));
    }
}
