package eu.icolumbo.cinch;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Paul Middelkoop
 */
public class SpringContext {

    private static Map<Class<?>, AbstractApplicationContext> CONTEXTS = new HashMap<>();

	/**
	 * Get application context for configuration class.
	 */
    public static synchronized AbstractApplicationContext getContext(Class<?> springConfigurationClass) {
        if (!CONTEXTS.containsKey(springConfigurationClass)) {
            AbstractApplicationContext context = new AnnotationConfigApplicationContext(springConfigurationClass);
            context.registerShutdownHook();
            CONTEXTS.put(springConfigurationClass, context);
        }

        return CONTEXTS.get(springConfigurationClass);
    }
}
