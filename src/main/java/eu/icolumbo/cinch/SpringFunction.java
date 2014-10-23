package eu.icolumbo.cinch;

import org.apache.spark.api.java.function.Function;

/**
 * @author Paul Middelkoop
 */
public class SpringFunction<T1, R> implements Function<T1, R> {

	private final Class<?> springConfigurationClass;
	private final Class<? extends Function<T1, R>> springBeanClass;

	public SpringFunction(Class<?> springConfigurationClass, Class<? extends Function<T1, R>> springBeanClass) {
		this.springConfigurationClass = springConfigurationClass;
		this.springBeanClass = springBeanClass;
	}

	@Override
	public R call(T1 t) throws Exception {
		Function<T1, R> function = SpringContext.getContext(springConfigurationClass).getBean(springBeanClass);
		return function.call(t);
	}
}
