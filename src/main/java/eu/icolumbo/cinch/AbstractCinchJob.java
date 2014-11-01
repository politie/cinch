package eu.icolumbo.cinch;

import org.apache.spark.SparkConf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Paul Middelkoop
 */
public abstract class AbstractCinchJob<T extends CinchContext> {

    private static final Logger log = LoggerFactory.getLogger(AbstractCinchJob.class);

    public final void execute(T cinchContext) {
        SparkConf sparkConf = new SparkConf().setAppName(getClass().getSimpleName());
        setUpSparkConf(sparkConf);

        log.info("Starting Spark job: {}", sparkConf.get("spark.app.name"));

        execute(cinchContext, sparkConf);
    }

    protected abstract void execute(T cinchContext, SparkConf sparkConf) ;

    protected void setUpSparkConf(SparkConf sparkConf) {
        // subclasses may implement this
    }
}
