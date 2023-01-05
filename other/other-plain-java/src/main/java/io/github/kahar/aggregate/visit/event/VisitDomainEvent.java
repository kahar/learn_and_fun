package io.github.kahar.aggregate.visit.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public abstract class VisitDomainEvent {
    private Long id;
    private UUID uuid;
}
