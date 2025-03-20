package uz.mamarasulov.rest_sample.web.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Conditional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@ConditionalOnExpression("${app.check-client-api-header:true}")
public class ClientApiFilerChecker extends OncePerRequestFilter {

    private static final String HTTP_CLIENT_HEADER = "X-Client-Api-Key";

    @Value("${app.client-api-key}")
    private String clientApiKey;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String headerValue = request.getHeader(HTTP_CLIENT_HEADER);

        if (headerValue == null || !headerValue.equals(clientApiKey)) {
            response.setHeader(HTTP_CLIENT_HEADER, "Invalid");
            response.sendError(HttpStatus.BAD_REQUEST.value(), "X-Client-Api-Key is not VALID");
        }

        filterChain.doFilter(request, response);
    }
}
