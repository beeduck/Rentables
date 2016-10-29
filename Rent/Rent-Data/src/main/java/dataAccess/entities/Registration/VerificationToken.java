package dataAccess.entities.Registration;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Duck on 10/28/2016.
 */
@Entity
@Table(name = "VerificationTokens")
public class VerificationToken {
    // TODO: Move to service logic
    private static final int EXPIRATION = 60 * 24;

    private int id;

    private String token;

    private Date expirationDate;

    private int userId;

    public VerificationToken() { }

    public VerificationToken(String token, int userId, Date expirationDate) {
        this.userId = userId;
        this.token = token;
        this.expirationDate = expirationDate;
    }

    // TODO: Move to service logic
    private Date calculateExpiryDate(int expirationTimeInMinutes) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Timestamp(cal.getTime().getTime()));
        cal.add(Calendar.MINUTE, expirationTimeInMinutes);
        return new Date(cal.getTime().getTime());
    }

    @Basic
    @Column(name = "userId", nullable = false)
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "expirationDate", nullable = false)
    public Date getExpirationDate() {

        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    @Basic
    @Column(name = "token", nullable = false)
    public String getToken() {

        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {

        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
