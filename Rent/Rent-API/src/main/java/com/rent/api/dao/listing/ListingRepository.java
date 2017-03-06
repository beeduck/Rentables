package com.rent.api.dao.listing;

import com.rent.api.entities.listing.Listing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by Duck on 1/28/2017.
 */
public interface ListingRepository extends JpaRepository<Listing, Long>,
                                           ListingRepositoryCustom,
                                           QueryDslPredicateExecutor {

    Listing findById(Integer id);

    List<Listing> findListsByUserId(int userId);

    @Query("SELECT l.id FROM Listing l where l.userId = :userId")
    List<Integer> findAllIdByUserId(@Param("userId") int userId);

    boolean existsByUserIdAndId(int userId, int id);
}
