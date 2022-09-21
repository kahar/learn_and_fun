package io.github.kahar.vet.web;

import io.github.kahar.vet.model.Vet;
import io.github.kahar.vet.model.VetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/vets")
@RestController
@RequiredArgsConstructor
class VetResource {

    private final VetRepository vetRepository;

    @GetMapping
    public List<Vet> showResourcesVetList() {
        return vetRepository.findAll();
    }
}
