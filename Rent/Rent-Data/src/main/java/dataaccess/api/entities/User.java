package dataaccess.api.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by duck on 10/24/16.
 */

@Entity
@Table(name = "Users")
public class User implements java.io.Serializable {
    private int id;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private boolean isActive;
    private Timestamp createDate;
    private Timestamp lastEditDate;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "username", nullable = false)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Basic
    @JsonIgnore
    @Column(name = "password", nullable = false)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @JsonIgnore
    @Column(name = "lastEditDate")
    public Timestamp getLastEditDate() {
        return lastEditDate;
    }

    public void setLastEditDate(Timestamp lastEditDate) {
        this.lastEditDate = lastEditDate;
    }

    @Basic
    @JsonIgnore
    @Column(name = "createDate")
    public Timestamp getCreateDate() {

        return createDate;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    @Basic
    @Column(name = "isActive")
    @Type(type = "yes_no")
    public boolean isActive() {

        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    @Basic
    @JsonIgnore
    @Column(name = "lastName")
    public String getLastName() {

        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Basic
    @JsonIgnore
    @Column(name = "firstName")
    public String getFirstName() {

        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
}