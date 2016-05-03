[Spring](http://spring.io/) integration for [Spark](https://spark.apache.org/)
------------------------------------------------------------------------------

"The program was a cinch to use"

Get Started
===========

Add the following dependency to your project:

Maven
-----

```xml
<dependency>
	<groupId>eu.icolumbo.cinch</groupId>
	<artifactId>cinch</artifactId>
	<version>1.0.0</version>
</dependency>
```

Batch
-----

The application need to extend the CinchJob class in order to use Cinch.

```java
@Component
public class BatchJob extends CinchJob {

	private static final Logger logger = Logger.getLogger(BatchJob.class);

	/**
	 * Main method to start job.
	 */
	public static void main(String[] args) {
		CinchContext.springJavaConfiguration(SpringConfiguration.class).executeJob();
	}

	/**
	 * Execute method contains all the business logic.
	 * The cinch context is used to create Spark functions.
	 */
	@Override
	protected void execute(JavaSparkContext sparkContext, CinchContext cinchContext) {
		logger.info("Start execution of example cinch batch job");

		JavaRDD<String> rdd = sparkContext.textFile("src/main/resources/example.txt");
		rdd.foreach(cinchContext.voidFunction(PrintFunction.class));

		logger.info("Finished execution example cinch batch job");
	}
}
```


Streaming
---------

The application need to extend the StreamingCinchJob class in order to use the streaming API.

```java
@Component
public class StreamingJob extends StreamingCinchJob {

	@Value("${streaming.host}")
	private String host;

	@Value("${streaming.port}")
	private int port;

	/**
	 * duration of micro batch is 5 seconds.
	 */
	public StreamingJob() {
		super(5000);
	}

	/**
	 * Main method to launch job.
	 */
	public static void main(String[] args) {
		StreamingCinchContext.springJavaConfiguration(SpringConfiguration.class).executeJob();
	}

	/**
	 * Execute job, this method contains all the business logic.
	 * The cinch context is used to create the Spark functions.
	 */
	@Override
	protected void defineJob(JavaStreamingContext streamingContext, StreamingCinchContext cinchContext) {
		JavaReceiverInputDStream<String> dStream = streamingContext.socketTextStream(host, port);
		dStream.foreachRDD(cinchContext.foreachRddFunction(PrintFunction.class));
	}
}
```

For more information see the kickstarter project.


Contributors
============
* Paul Middelkoop [@GitHub](http://github.com/paulmiddelkoop)
* Jethro Bakker [@GitHub](http://github.com/jethrobakker)
* Jeroen van Wilgenburg [@GitHub](http://github.com/jvwilge)

