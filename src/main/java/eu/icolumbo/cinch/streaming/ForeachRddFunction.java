package eu.icolumbo.cinch.streaming;

import eu.icolumbo.cinch.AbstractVoidFunction;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.VoidFunction;

/**
* @author Paul Middelkoop
*/
public class ForeachRddFunction<T> extends AbstractVoidFunction<JavaRDD<T>> {

    private final VoidFunction<T> voidFunction;

    ForeachRddFunction(VoidFunction<T> voidFunction) {
        this.voidFunction = voidFunction;
    }

    @Override
    public void voidCall(JavaRDD<T> rdd) throws Exception {
        rdd.foreach(voidFunction);
    }
}
