package io.github.kahar.creational.builder.car;

import lombok.Builder;
import lombok.ToString;

@Builder
@ToString
public class Car {
    private Long id;
    private String name;
}
