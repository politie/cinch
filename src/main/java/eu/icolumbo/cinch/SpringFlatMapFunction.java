package eu.icolumbo.cinch;

import org.apache.spark.api.java.function.FlatMapFunction;

/**
 * @author Jethro Bakker
 */
public class SpringFlatMapFunction<T, R> implements FlatMapFunction<T, R> {

	private final Class<?> springConfigurationClass;
	private final Class<? extends FlatMapFunction<T, R>> springBeanClass;

	SpringFlatMapFunction(Class<?> springConfigurationClass, Class<? extends FlatMapFunction<T, R>> springBeanClass) {
		this.springConfigurationClass = springConfigurationClass;
		this.springBeanClass = springBeanClass;
	}

	@Override
	public Iterable<R> call(T t) throws Exception {
		FlatMapFunction<T, R> function = SpringContext.getContext(springConfigurationClass).getBean(springBeanClass);
		return function.call(t);
	}
}
