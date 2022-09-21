package io.github.kahar.api.application;

import io.github.kahar.api.dto.OwnerDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class CustomersServiceClient {

    private final WebClient.Builder webClientBuilder;

    public Mono<OwnerDetails> getOwner(final int ownerId) {
        return webClientBuilder.build().get()
                .uri("http://petclinic-customer-service/owners/{ownerId}", ownerId)
                .retrieve()
                .bodyToMono(OwnerDetails.class);
    }
}
