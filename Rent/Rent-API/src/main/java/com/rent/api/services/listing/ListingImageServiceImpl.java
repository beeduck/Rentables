package com.rent.api.services.listing;

import com.rent.api.dto.listing.ListingImageDTO;
import com.rent.data.dataaccess.api.dao.listing.ListingImageDAO;
import com.rent.data.dataaccess.api.entities.listing.ListingImage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Asad on 11/29/2016.
 */

@Service
public class ListingImageServiceImpl implements ListingImageService {

    @Autowired
    ListingImageDAO listingImageDAO;

    public ListingImage uploadImage(ListingImageDTO listingImageDTO) {
        return null;
    }

    public ListingImage getImageById(int id) {
        return listingImageDAO.getImageById(id);
    }

    public List<ListingImage> getImageByListingId(int listingId) {
        return listingImageDAO.getImageByListingId(listingId);
    }
}
