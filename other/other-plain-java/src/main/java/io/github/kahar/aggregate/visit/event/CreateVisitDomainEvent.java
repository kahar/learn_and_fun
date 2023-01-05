package io.github.kahar.aggregate.visit.event;

import lombok.Getter;

import java.util.Date;
import java.util.UUID;

@Getter
public class CreateVisitDomainEvent extends VisitDomainEvent {
    private final Date date;
    private final String description;
    private final int petId;

    public CreateVisitDomainEvent(Long id, UUID uuid, Date date, String description, int petId) {
        super(id, uuid);
        this.date = date;
        this.description = description;
        this.petId = petId;
    }
}
