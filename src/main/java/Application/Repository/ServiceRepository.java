package Application.Repository;

import Application.Models.ServiceCni;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceRepository extends JpaRepository<ServiceCni, Integer> {

}