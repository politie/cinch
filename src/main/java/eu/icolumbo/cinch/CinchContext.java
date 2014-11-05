package eu.icolumbo.cinch;

import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.VoidFunction;
import org.springframework.context.support.AbstractApplicationContext;

/**
 * @author Paul Middelkoop
 */
public class CinchContext {

    private Class<?> springConfigurationClass;

	/**
	 * Create a new cinch context,
	 */
    public static CinchContext springJavaConfiguration(Class<?> springConfigurationClass) {
        return new CinchContext(springConfigurationClass);
    }

    protected CinchContext(Class<?> springConfigurationClass) {
        this.springConfigurationClass = springConfigurationClass;
    }

	/**
	 * Executes your job from the spring context.
	 */
    public void executeJob() {
        AbstractApplicationContext springContext = SpringContext.getContext(springConfigurationClass);
        CinchJob cinchJob = springContext.getBean(CinchJob.class);

        cinchJob.execute(this);
    }

	/**
	 * Create a new SpringFunction, which implements Spark's Function interface.
	 */
    public <T1, R> Function<T1, R>  function(Class<? extends Function<T1, R>> springBeanClass) {
        return new SpringFunction<>(springConfigurationClass, springBeanClass);
    }

	/**
	 * Create a new SpringVoidFunction, which implements Spark's VoidFunction interface.
	 */
    public <T> VoidFunction<T>  voidFunction(Class<? extends VoidFunction<T>> springBeanClass) {
        return new SpringVoidFunction<>(springConfigurationClass, springBeanClass);
    }

	/**
	 * Create a new SpringFlapMapFunction, which implements Spark's FlatMapFunction interface.
	 */
    public <T, R> FlatMapFunction<T, R> flatMapFunction(Class<? extends FlatMapFunction<T, R>> springBeanClass) {
        return new SpringFlatMapFunction<>(springConfigurationClass, springBeanClass);
    }

	/**
	 * Get the spring configuration class.
	 */
    public Class<?> getSpringConfigurationClass() {
        return springConfigurationClass;
    }
}
