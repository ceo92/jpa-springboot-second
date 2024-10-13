package jpa.mvc.domain;

import jakarta.persistence.Entity;
import lombok.Getter;

@Entity
@Getter
public class Movie extends Item {

    private String director;
    private String actor;
}
