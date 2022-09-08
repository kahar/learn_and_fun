package io.github.kahar._transient;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Book implements Serializable {
    private static final long serialVersionUID = -2936687026040726549L;
    private final transient String bookCategory = "Fiction";
    private String bookName;
    private transient String description;
    private transient int copies;
}