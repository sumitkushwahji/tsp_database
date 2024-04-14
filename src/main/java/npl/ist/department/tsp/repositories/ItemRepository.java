package npl.ist.department.tsp.repositories;
import npl.ist.department.tsp.models.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    @Query("SELECT CASE WHEN COUNT(i) > 0 THEN true ELSE false END FROM Item i WHERE "
            + "i.itemName = :itemName AND i.indentor = :indentor AND i.employeeId = :employeeId AND i.totalQty = :totalQty")
    boolean existsByItemNameAndIndentorAndEmployeeIdAndTotalQty(@Param("itemName") String itemName,
                                                                @Param("indentor") String indentor,
                                                                @Param("employeeId") Long employeeId,
                                                                @Param("totalQty") int totalQty);

}
