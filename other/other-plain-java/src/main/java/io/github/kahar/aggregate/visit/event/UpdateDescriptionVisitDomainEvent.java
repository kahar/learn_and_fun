package io.github.kahar.aggregate.visit.event;

import lombok.Getter;

import java.util.UUID;

@Getter
public class UpdateDescriptionVisitDomainEvent extends VisitDomainEvent {
    private final String description;

    public UpdateDescriptionVisitDomainEvent(Long id, UUID uuid, String description) {
        super(id, uuid);
        this.description = description;
    }
}
