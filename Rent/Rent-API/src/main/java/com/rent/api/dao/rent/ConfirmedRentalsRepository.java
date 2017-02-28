package com.rent.api.dao.rent;

import com.rent.api.entities.rent.ConfirmedRentals;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by duck on 2/28/17.
 */
@Repository
public interface ConfirmedRentalsRepository extends JpaRepository<ConfirmedRentals, Long> {

}
