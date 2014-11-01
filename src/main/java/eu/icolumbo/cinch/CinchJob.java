package eu.icolumbo.cinch;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;

/**
 * @author Paul Middelkoop
 */
public abstract class CinchJob extends AbstractCinchJob<CinchContext> {

    @Override
    public void execute(CinchContext cinchContext, SparkConf sparkConf) {
        JavaSparkContext sparkContext = new JavaSparkContext(sparkConf);

        execute(sparkContext, cinchContext);
    }

    protected abstract void execute(JavaSparkContext sparkContext, CinchContext cinchContext) ;
}
