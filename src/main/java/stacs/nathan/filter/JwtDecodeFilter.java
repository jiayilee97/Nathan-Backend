package stacs.nathan.filter;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import stacs.nathan.context.RequestContext;
import stacs.nathan.dto.DecodedJwtDTO;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
public class JwtDecodeFilter implements Filter {
    private static String ACCESS_TOKEN_HEADER = "x-access-token";

    @Autowired
    private RequestContext requestContext;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String accessToken = ((HttpServletRequest) servletRequest).getHeader(ACCESS_TOKEN_HEADER);

        if (!StringUtils.isEmpty(accessToken)) {
//             DecodedJwtDTO decodedUser = new DecodedJwtDTO(...);
//            requestContext.store(decodedUser);
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }
}
