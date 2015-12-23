package eu.icolumbo.cinch.streaming;

import eu.icolumbo.cinch.SpringContext;
import org.apache.spark.storage.StorageLevel;
import org.apache.spark.streaming.receiver.Receiver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Paul Middelkoop
 */
public abstract class SpringReceiver<T> extends Receiver<T> {

    private static final Logger log = LoggerFactory.getLogger(SpringReceiver.class);

    private final Class<?> springConfigurationClass;

    public SpringReceiver(StreamingCinchContext cinchContext) {
        super(StorageLevel.MEMORY_ONLY());

        this.springConfigurationClass = cinchContext.getSpringConfigurationClass();
    }

    @Override
    public void onStart() {
        SpringContext.getContext(springConfigurationClass).getAutowireCapableBeanFactory().autowireBean(this);

        new Thread() {
            @Override
            public void run() {
                try {
                    receive();
                } catch (Exception e) {
                    log.error("Error while receiving data", e);
					restart("Restaring Receiver after " + e.getMessage());
                }
            }
        }.start();
    }

    @Override
    public void onStop() {
    }

    protected abstract void receive() throws Exception;
}
