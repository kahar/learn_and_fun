package io.github.kahar.aggregate.visit;

import io.github.kahar.aggregate.visit.event.CreateVisitDomainEvent;
import io.github.kahar.aggregate.visit.event.UpdateDescriptionVisitDomainEvent;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class VisitTest {

    @Test
    public void happyTest() {
        Long id = -1L;
        UUID uuid = UUID.randomUUID();
        Date date = new Date();
        String description = null;
        Integer petId = -2;

        CreateVisitDomainEvent createVisitDomainEvent = new CreateVisitDomainEvent(id, uuid, date, description, petId);
        Visit visit = new Visit();
        // when
        visit.apply(createVisitDomainEvent);

        assertThat(visit.getId().getId()).isEqualTo(id);
        assertThat(visit.getDate()).isEqualTo(date);
        assertThat(visit.getDescription()).isEqualTo(description);
        assertThat(visit.getPetId()).isEqualTo(petId);

        String newDescription = "newDescription";

        UpdateDescriptionVisitDomainEvent updateDescriptionVisitDomainEvent = new UpdateDescriptionVisitDomainEvent(id, uuid, newDescription);
        // when
        visit.apply(updateDescriptionVisitDomainEvent);

        assertThat(visit.getDescription()).isEqualTo(description);
    }
}
