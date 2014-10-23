package eu.icolumbo.cinch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Paul Middelkoop
 */
public class SpringContext {

    private static final Logger log = LoggerFactory.getLogger(SpringContext.class);

    private static Map<Class<?>, AbstractApplicationContext> CONTEXTS = new HashMap<>();

    public static synchronized AbstractApplicationContext getContext(Class<?> springConfigurationClass) {
        if (!CONTEXTS.containsKey(springConfigurationClass)) {
            AbstractApplicationContext context = new AnnotationConfigApplicationContext(springConfigurationClass);
            context.registerShutdownHook();
            CONTEXTS.put(springConfigurationClass, context);
        }

        return CONTEXTS.get(springConfigurationClass);
    }

    public static void executeJob(Class<?> springConfigurationClass, Class<? extends SpringSparkJob> sparkJobClass) {
        AbstractApplicationContext context = SpringContext.getContext(springConfigurationClass);
        SpringSparkJob sparkJob = context.getBean(sparkJobClass);
        sparkJob.execute();
    }
}
