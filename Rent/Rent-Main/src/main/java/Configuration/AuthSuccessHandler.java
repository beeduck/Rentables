package Configuration;

import Services.User.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import dataAccess.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

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

    private RequestCache requestCache = new HttpSessionRequestCache();

    @Autowired
    UserService userService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws ServletException, IOException {

        PrintWriter printWriter = response.getWriter();

        Object object = authentication.getPrincipal();
        String name = authentication.getName();
        //userService.updateUserModifiedTime(user);

        User user = userService.getUserByName(name);

        ObjectMapper mapper = new ObjectMapper();
        String userString = mapper.writeValueAsString(user);

        printWriter.write(userString);

        response.setStatus(200);
        response.setHeader("content-type=application/json", "test");

        // TODO: Return authenticated user details
        // TODO: Update user modified timestamp

//        SavedRequest savedRequest = requestCache.getRequest(request, response);
//
//        if (savedRequest == null) {
//            clearAuthenticationAttributes(request);
//            return;
//        }
//        String targetUrlParam = getTargetUrlParameter();
//        if (isAlwaysUseDefaultTargetUrl() ||
//                (targetUrlParam != null &&
//                        StringUtils.hasText(request.getParameter(targetUrlParam)))) {
//            requestCache.removeRequest(request, response);
//            clearAuthenticationAttributes(request);
//            return;
//        }

        clearAuthenticationAttributes(request);
    }

    public void setRequestCache(RequestCache requestCache) {
        this.requestCache = requestCache;
    }
}