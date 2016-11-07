package DTOEntities.UserPost;

import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * Created by Asad on 11/1/2016.
 */
public class UserPostDTO implements Serializable {

    @Size(min = 1, message = "title required")
    private String title;

    private String description;

    private int userId;

    private int priceCategoryId;

    private double price;

    public UserPostDTO(){}

    public UserPostDTO(String title, String description, int userId, int priceCategoryId, double price) {
        this.title = title;
        this.description = description;
        this.userId = userId;
        this.priceCategoryId = priceCategoryId;
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getPriceCategoryId() {
        return priceCategoryId;
    }

    public void setPriceCategoryId(int priceCategoryId) {
        this.priceCategoryId = priceCategoryId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
