package nl.serviceplanet.rgsp.alpha;

import io.grpc.Server;
import io.grpc.netty.NettyServerBuilder;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import nl.serviceplanet.rgsp.messages.MathProtos;
import nl.serviceplanet.rgsp.messages.ReactorMathServiceGrpc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public final class MathServiceGrpc extends ReactorMathServiceGrpc.MathServiceImplBase {

	private final Logger log = LoggerFactory.getLogger(MathServiceGrpc.class);

	@Autowired
	private MathService mathService;

	private volatile Server server;

	public MathServiceGrpc() {

	}

	@PostConstruct
	void init() {
		try {
			log.info("Starting gRPC server.");
			server = NettyServerBuilder.forPort(8870).addService(this).build();
			server.start();
			log.info("gRPC server started.");
		} catch (Exception e) {
			throw new RuntimeException("Unable to start gRPC server.", e);
		}
	}

	@PreDestroy
	void destroy() {
		log.info("Shutdown gRPC server.");
		server.shutdownNow();
	}

	@Override
	public Mono<MathProtos.MultiplyResp> multiply(Mono<MathProtos.MultiplyReq> request) {

		return request
				.map(req -> new MathService.MultiplyRequest(req.getX(), req.getY()))
				.compose(dto -> mathService.multiply(dto))
				.map(result -> MathProtos.MultiplyResp.newBuilder().setAnswer(result.doubleValue()).build());
	}

	public Mono<MathProtos.MultiplyResp> multiplySecure(Mono<MathProtos.MultiplyReq> request) {
		Authentication authentication = new TestingAuthenticationToken("test_user", "test_password", "MULTIPLIER");

		return request
				.map(req -> new MathService.MultiplyRequest(req.getX(), req.getY()))
				.compose(dto -> mathService.multiplySecure(dto))
				.map(result -> MathProtos.MultiplyResp.newBuilder().setAnswer(result.doubleValue()).build())
				//.subscriberContext(ReactiveSecurityContextHolder.withAuthentication(authentication))
				.doOnError(t -> log.warn("An exception occurred in secure multiply.", t));
	}

}
