package io.github.kahar.customer.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.core.style.ToStringCreator;

import javax.persistence.*;
import java.util.Date;


@Data
@Entity
@Table(name = "pets")
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "birth_date")
    @Temporal(TemporalType.DATE)
    private Date birthDate;

    @ManyToOne
    @JoinColumn(name = "type_id")
    private PetType type;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    @JsonIgnore
    private Owner owner;

    @Override
    public String toString() {
        return new ToStringCreator(this)
                .append("id", this.getId())
                .append("name", this.getName())
                .append("birthDate", this.getBirthDate())
                .append("type", this.getType().getName())
                .append("ownerFirstname", this.getOwner().getFirstName())
                .append("ownerLastname", this.getOwner().getLastName())
                .toString();
    }

}
