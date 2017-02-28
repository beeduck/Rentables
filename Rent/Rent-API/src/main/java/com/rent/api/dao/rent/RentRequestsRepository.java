package com.rent.api.dao.rent;

import com.rent.api.entities.rent.RentRequests;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by duck on 2/28/17.
 */
@Repository
public interface RentRequestsRepository extends JpaRepository<RentRequests, Long> {

    List<RentRequests> findByRequestingUser(int requestingUser);

    RentRequests findById(int id);

    List<RentRequests> findByListingId(int listingId);
}
