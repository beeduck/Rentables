package com.rent.api.dao.rent;

import com.rent.api.entities.rent.ConfirmedRentals;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by duck on 2/28/17.
 */
@Repository
public interface ConfirmedRentalsRepository extends JpaRepository<ConfirmedRentals, Long> {
    ConfirmedRentals findByListingId(int listingId);
    List<ConfirmedRentals> findByListingIdIn(List<Integer> listingIds);
    boolean existsByListingId(int listingId);

    List<ConfirmedRentals> findByRentingUser(int rentingUser);
}
