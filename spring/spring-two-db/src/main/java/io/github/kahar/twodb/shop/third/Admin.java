package io.github.kahar.twodb.shop.third;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Admin {
    private String firstName;
    private String lastName;
}