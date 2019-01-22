package nl.serviceplanet.rgsp.alpha;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public final class Main {

	private static final Logger log = LoggerFactory.getLogger(Main.class);

	public static void main(String[] args) throws Exception {
		log.info("Application starting.");

		try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringSecurityConfiguration.class)) {
			context.register(DefaultMathService.class, MathServiceGrpc.class);

			MathServiceGrpc mathServiceGrpc = context.getBean(MathServiceGrpc.class);

			Thread.currentThread().join();
		}

		log.info("Application finished.");
	}
}
