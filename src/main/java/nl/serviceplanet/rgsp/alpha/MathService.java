package nl.serviceplanet.rgsp.alpha;

import java.math.BigDecimal;
import org.springframework.security.access.prepost.PreAuthorize;
import reactor.core.publisher.Mono;

public interface MathService {

	Mono<BigDecimal> multiply(Mono<MultiplyRequest> multiplyRequest);

	@PreAuthorize("hasRole('MULTIPLIER')")
	Mono<BigDecimal> multiplySecure(Mono<MultiplyRequest> multiplyRequest);

	final class MultiplyRequest {
		public final BigDecimal x;
		public final BigDecimal y;

		MultiplyRequest(BigDecimal x, BigDecimal y) {
			this.x = x;
			this.y = y;
		}

		MultiplyRequest(double x, double y) {
			this.x = BigDecimal.valueOf(x);
			this.y = BigDecimal.valueOf(y);
		}
	}

}
