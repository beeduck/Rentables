package server;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;

public class ImageExclusionStrategy implements ExclusionStrategy {

    @Override
    public boolean shouldSkipField(FieldAttributes f) {

        return (f.getDeclaringClass() == dataobject.CreateListing.class && f.getName().equals("listingImage"));
    }

    @Override
    public boolean shouldSkipClass(Class<?> clazz) {
        return false;
    }
}
