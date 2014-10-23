package eu.icolumbo.cinch;

import org.apache.spark.api.java.function.Function;
import scala.Tuple2;

public class SpringTuple2Function<R> implements Function<Tuple2<String, String>, R> {

	private final Class<?> springConfigurationClass;
	private final Class<? extends Function<Tuple2<String, String>, R>> springBeanClass;

	public SpringTuple2Function(Class<?> springConfigurationClass, Class<? extends Function<Tuple2<String, String>,
			R>> springBeanClass) {
		this.springConfigurationClass = springConfigurationClass;
		this.springBeanClass = springBeanClass;
	}

	@Override
	public R call(Tuple2<String, String> t) throws Exception {
		Function<Tuple2<String, String>, R> function = SpringContext.getContext(springConfigurationClass).getBean(springBeanClass);
		return function.call(t);
	}
}
