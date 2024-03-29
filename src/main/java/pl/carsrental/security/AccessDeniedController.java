package pl.carsrental.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("/error")
public class AccessDeniedController implements AccessDeniedHandler {

    private final static Logger LOGGER = LoggerFactory.getLogger(AccessDeniedController.class);

    @GetMapping("/access-denied")
    public String accessDenied() {
        return "access-denied";
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            LOGGER.warn("User: " + auth.getName() + " attempted to access the protected URL: " + request.getRequestURI());
            response.sendRedirect(request.getContextPath() + "/error/access-denied");
        } else {
            response.sendRedirect(request.getContextPath() + "/login");
        }
    }
}
