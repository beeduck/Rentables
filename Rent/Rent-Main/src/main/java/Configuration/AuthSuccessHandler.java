package Configuration;

import Services.User.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import dataAccess.entities.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by Duck on 10/19/2016.
 */
@Component
public class AuthSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Autowired
    UserService userService;

    private RequestCache requestCache = new HttpSessionRequestCache();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws ServletException, IOException {

        PrintWriter printWriter = response.getWriter();

        String username = authentication.getName();
        User user = userService.getUserByName(username);
        userService.updateUserModifiedTime(user);

        ObjectMapper mapper = new ObjectMapper();
        String userString = mapper.writeValueAsString(user);

        printWriter.write(userString);

        response.setStatus(200);

        clearAuthenticationAttributes(request);
    }

    public void setRequestCache(RequestCache requestCache) {
        this.requestCache = requestCache;
    }
}