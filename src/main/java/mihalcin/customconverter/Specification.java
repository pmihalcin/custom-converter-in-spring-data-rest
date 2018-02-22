package mihalcin.customconverter;

import static javax.persistence.EnumType.STRING;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Created by Patrik.Mihalcin on 22.02.2018
 */
@Getter
@Entity
@RequiredArgsConstructor
public class Specification extends AbstractEntity<Long> {

    @Enumerated(STRING)
    @Column(name = "STATUS")
    private final Status status;

    public Specification() {
        this.status = null;
    }

    /**
     * Enumeration for all the statuses {@link Specification} can be in.
     */
    public enum Status {
        DRAFT,
        ACTIVE,
        CLOSED,
        EXPIRED;

        public String messageCode() {
            return this.getDeclaringClass().getName() + "." + this.name();
        }
    }

}
