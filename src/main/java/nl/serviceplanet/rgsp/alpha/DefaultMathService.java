package nl.serviceplanet.rgsp.alpha;

import java.math.BigDecimal;
import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import static com.google.common.base.Preconditions.checkNotNull;

@Component
public final class DefaultMathService implements MathService {

	private static final Logger log = LoggerFactory.getLogger(DefaultMathService.class);

	@PostConstruct
	public void init() {
		log.info("MathService started.");
	}

	@Override
	public Mono<BigDecimal> multiply(Mono<MultiplyRequest> multiplyRequest) {
		checkNotNull(multiplyRequest);

		return multiplyRequest.map(req -> req.x.multiply(req.y));
	}

	@Override
	public Mono<BigDecimal> multiplySecure(Mono<MultiplyRequest> multiplyRequest) {
		checkNotNull(multiplyRequest);

		return multiplyRequest.map(req -> req.x.multiply(req.y));
	}
}
