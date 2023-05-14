package com.jamesclear.first.microservice.CurrencyExchangeMicroservice.Controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import io.github.resilience4j.retry.annotation.Retry;
import reactor.core.publisher.Mono;

@RestController
public class CircuitBreakerController {

	private Logger logger = LoggerFactory.getLogger(CircuitBreakerController.class);
	
	@GetMapping("/circuitBreaker/abc")
	@Retry(name = "circuitBreaker-abc-5times" , fallbackMethod = "justFallBackRetry")
	public String sampleApi() {
		logger.info("Minh wait Sample api call received");
		ResponseEntity<String> forEntity = new RestTemplate().getForEntity("http://localhost:5000/some-dummy-url", 
					String.class);
		return forEntity.getBody();
		
		//return "minh";
		
	}
	
	public String justFallBackRetry(Exception e) {
		return "No method circuitBreaker/abc" ;
		
	}
	
//	public Mono<String> justFallBackRetry(NoSuchMethodException e){
//		return Mono.just(e.getMessage()) ;
//	}
	
}
