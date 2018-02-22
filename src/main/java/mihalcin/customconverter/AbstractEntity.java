package mihalcin.customconverter;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

import lombok.Getter;
import lombok.ToString;
import org.springframework.hateoas.Identifiable;

@MappedSuperclass
@Getter
@ToString
public class AbstractEntity<ID extends Serializable> implements Identifiable<ID> {

    @Id
    @GeneratedValue
    private final ID id;

    @Version
    @Column(name = "REC_VERSION", nullable = false)
    private Long version;

    protected AbstractEntity() {
        this.id = null;
    }
}