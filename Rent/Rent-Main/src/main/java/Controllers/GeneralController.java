package Controllers;

import Utilities.Constants;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@ComponentScan("dataaccess")
public class GeneralController {

    @PreAuthorize(Constants.OAUTH2_AUTH_USER)
    @RequestMapping("/")
    public List<Integer> index() {
        return null;
    }

}