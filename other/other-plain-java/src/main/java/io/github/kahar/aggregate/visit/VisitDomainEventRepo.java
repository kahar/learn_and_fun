package io.github.kahar.aggregate.visit;


import io.github.kahar.aggregate.visit.event.VisitDomainEvent;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/*For now, it is not used.*/
class VisitDomainEventRepo {

    private ConcurrentHashMap<UUID, VisitDomainEvent> map = new ConcurrentHashMap<>();


    Visit load(VisitId id) {
        List<VisitDomainEvent> events = loadStream(id);
        Visit visit = new Visit();
        events.forEach(visit::apply);
        return visit;
    }

    private List<VisitDomainEvent> loadStream(VisitId id) {
        return map.values().stream().filter(e -> id.getId().equals(e.getId())).collect(Collectors.toList());
    }
}
