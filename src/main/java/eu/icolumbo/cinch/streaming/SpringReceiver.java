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

    public SpringReceiver(Class<?> springConfigurationClass) {
        super(StorageLevel.MEMORY_ONLY());

        this.springConfigurationClass = springConfigurationClass;
    }

    @Override
    public void onStart() {
        new Thread() {
            @Override
            public void run() {
                try {
                    SpringContext.getContext(springConfigurationClass).getAutowireCapableBeanFactory().autowireBean(SpringReceiver.this);
                    receive();
                } catch (Exception e) {
                    log.error("Error while receiving data", e);
                }
            }
        }.start();
    }

    @Override
    public void onStop() {
    }

    protected abstract void receive() throws Exception;
}
