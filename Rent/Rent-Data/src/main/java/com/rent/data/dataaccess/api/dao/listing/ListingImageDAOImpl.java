package com.rent.data.dataaccess.api.dao.listing;

import com.rent.data.dataaccess.api.dao.ApiDAO;
import com.rent.data.dataaccess.api.entities.listing.ListingImage;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Created by Asad on 11/29/2016.
 */

@Repository
public class ListingImageDAOImpl extends ApiDAO implements ListingImageDAO {

    @Transactional
    public ListingImage uploadImage(ListingImage listingImage) {
        if(this.save(listingImage)) {
            return listingImage;
        }
        return null;
    }

    @Transactional(readOnly = true)
    public ListingImage getImageById(int id) {
        Criteria criteria = getSession().createCriteria(ListingImage.class);
        criteria.add(Restrictions.eq("id",id));
        return (ListingImage)criteria.uniqueResult();
    }

    @Transactional(readOnly = true)
    public List<ListingImage> getImageByListingId(int listingId) {
        Criteria criteria = getSession().createCriteria(ListingImage.class);
        criteria.add(Restrictions.eq("listingId",listingId));
        return criteria.list();
    }
}