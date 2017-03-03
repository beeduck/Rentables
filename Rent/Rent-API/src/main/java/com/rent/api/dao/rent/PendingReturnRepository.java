package com.rent.api.dao.rent;

import com.rent.api.entities.rent.PendingReturn;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by duck on 3/2/17.
 */
public interface PendingReturnRepository extends JpaRepository<PendingReturn, Long> {

    PendingReturn findByListingId(int listingId);

    void deleteByListingId(int listingId);

    PendingReturn findById(int id);

    List<PendingReturn> findByReturningUserId(int returningUserId);
    List<PendingReturn> findByListingIdIn(List<Integer> listingIds);

}
