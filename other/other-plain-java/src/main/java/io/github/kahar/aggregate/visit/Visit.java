package io.github.kahar.aggregate.visit;

import io.github.kahar.aggregate.visit.event.CreateVisitDomainEvent;
import io.github.kahar.aggregate.visit.event.UpdateDescriptionVisitDomainEvent;
import io.github.kahar.aggregate.visit.event.VisitDomainEvent;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@Getter
public class Visit {

    private VisitId id;

    private Date date = new Date();

    private String description;

    private int petId;

    void apply(VisitDomainEvent event) {
        if (event instanceof CreateVisitDomainEvent) {
            CreateVisitDomainEvent createEvent = ((CreateVisitDomainEvent) event);
            this.id = new VisitId(createEvent.getId());
            this.date = createEvent.getDate();
            this.description = createEvent.getDescription();
            this.petId = createEvent.getPetId();
        } else if (event instanceof UpdateDescriptionVisitDomainEvent) {
            UpdateDescriptionVisitDomainEvent updateEvent = ((UpdateDescriptionVisitDomainEvent) event);
            this.description = updateEvent.getDescription();
        } else {
            throw new RuntimeException("no such event" + event);
        }
//        else if (event instanceof QAckWasSigned) {
//            this.status = QAckStatus.SIGNED;
//        } else if (event instanceof QAckWasSignedOnBehalf) {
//            this.status = QAckStatus.SIGNED;
//
    }
}


