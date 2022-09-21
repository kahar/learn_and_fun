package io.github.kahar.api.dto;

import lombok.Value;

import java.util.ArrayList;
import java.util.List;

@Value
public class Visits {

    private List<VisitDetails> items = new ArrayList<>();

}
