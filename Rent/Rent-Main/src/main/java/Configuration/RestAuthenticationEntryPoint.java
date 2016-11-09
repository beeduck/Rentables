package Configuration;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Duck on 10/19/2016.
 */
@Component( "restAuthenticationEntryPoint" )
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException ) throws IOException {

        String userJson = (String) request.getSession().getAttribute("USER_SESSION");

        if(userJson == null) {
            response.setStatus(401);
        }


//        response.sendError( HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized" );
    }
}