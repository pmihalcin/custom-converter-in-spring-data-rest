package mihalcin.customconverter;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

/**
 * Created by Patrik.Mihalcin on 22.02.2018
 */
public interface SpecificationRepository extends JpaRepository<Specification, Long>, QueryDslPredicateExecutor<Specification> {
}
