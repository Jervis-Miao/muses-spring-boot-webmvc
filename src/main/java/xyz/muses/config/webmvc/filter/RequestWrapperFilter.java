package xyz.muses.config.webmvc.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.OncePerRequestFilter;

import xyz.muses.config.webmvc.BodyReaderHttpServletRequestWrapper;

/**
 * wrapper HttpServletRequest and return {@link BodyReaderHttpServletRequestWrapper}
 *
 * @author lvchenggang.
 * @date 2019/4/19 15:09
 * @see
 * @since
 */
public class RequestWrapperFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException {
        BodyReaderHttpServletRequestWrapper requestWrapper = new BodyReaderHttpServletRequestWrapper(request);
        filterChain.doFilter(requestWrapper, response);
    }
}
