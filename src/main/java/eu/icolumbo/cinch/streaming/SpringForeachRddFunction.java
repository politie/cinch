package eu.icolumbo.cinch.streaming;

import eu.icolumbo.cinch.AbstractVoidFunction;
import eu.icolumbo.cinch.SpringVoidFunction;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.VoidFunction;

/**
* @author Paul Middelkoop
*/
public class SpringForeachRddFunction<T> extends AbstractVoidFunction<JavaRDD<T>> {

    private final SpringVoidFunction<T> voidFunction;

    public SpringForeachRddFunction(Class<?> springConfigurationClass, Class<? extends VoidFunction> springBeanClass) {
        voidFunction = new SpringVoidFunction<>(springConfigurationClass, springBeanClass);
    }

    @Override
    public void voidCall(JavaRDD<T> rdd) throws Exception {
        rdd.foreach(voidFunction);
    }
}
