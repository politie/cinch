package eu.icolumbo.cinch.streaming;

import eu.icolumbo.cinch.AbstractCinchJob;
import org.apache.spark.SparkConf;
import org.apache.spark.streaming.Duration;
import org.apache.spark.streaming.api.java.JavaStreamingContext;

/**
 * @author Paul Middelkoop
 */
public abstract class StreamingCinchJob extends AbstractCinchJob<StreamingCinchContext> {

    private long durationInMillis;

    public StreamingCinchJob(long durationInMillis) {
        this.durationInMillis = durationInMillis;
    }

	/**
	 * Execute cinch job with context and spark conf.
	 */
    @Override
    public void execute(StreamingCinchContext cinchContext, SparkConf sparkConf)  {
        JavaStreamingContext streamingContext = new JavaStreamingContext(sparkConf, new Duration(durationInMillis));

        defineJob(streamingContext, cinchContext);

        streamingContext.start();
        streamingContext.awaitTermination();
    }

    protected abstract void defineJob(JavaStreamingContext streamingContext, StreamingCinchContext cinchContext);
}
