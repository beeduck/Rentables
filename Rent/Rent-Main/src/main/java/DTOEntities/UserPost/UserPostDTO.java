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

    public UserPostDTO(){}

    public UserPostDTO(String title, String description, int userId) {
        this.title = title;
        this.description = description;
        this.userId = userId;
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
}
