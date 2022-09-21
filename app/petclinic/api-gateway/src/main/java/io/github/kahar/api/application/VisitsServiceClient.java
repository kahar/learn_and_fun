package io.github.kahar.api.application;

import io.github.kahar.api.dto.Visits;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

import static java.util.stream.Collectors.joining;

@Component
@RequiredArgsConstructor
public class VisitsServiceClient {

    // Could be changed for testing purpose
    private String hostname = "http://petclinic-visit-service/";

    private final WebClient.Builder webClientBuilder;

    public Mono<Visits> getVisitsForPets(final List<Integer> petIds) {
        return webClientBuilder.build()
                .get()
                .uri(hostname + "pets/visits?petId={petId}", joinIds(petIds))
                .retrieve()
                .bodyToMono(Visits.class);
    }

    private String joinIds(List<Integer> petIds) {
        return petIds.stream().map(Object::toString).collect(joining(","));
    }

    void setHostname(String hostname) {
        this.hostname = hostname;
    }
}
