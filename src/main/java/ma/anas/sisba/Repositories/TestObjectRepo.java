package ma.anas.sisba.Repositories;

import ma.anas.sisba.Entities.TestObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TestObjectRepo extends JpaRepository<TestObject, String> {
    @Query("select DISTINCT(t.name) from TestObject t where t.deleted=false ")
    List<String> getLabels();

    @Query("select sum(t.price) from TestObject t where t.deleted=false and t.name = :name")
    Double getTotalPrice(@Param("name") String name);

    @Query("select t from TestObject t  where t.deleted=false and Lower(t.name) LIKE CONCAT('%',:label, '%') ")
    Page<TestObject> getPage(@Param("label") String label, PageRequest of);
}
