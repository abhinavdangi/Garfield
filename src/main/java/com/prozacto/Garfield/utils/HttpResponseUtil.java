package com.prozacto.Garfield.utils;

import com.prozacto.Garfield.domain.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

/**
 * This util class contains methods related to building of HttpServletResponse output.
 */
public final class HttpResponseUtil {

    private static final Logger LOG = LoggerFactory.getLogger(HttpResponseUtil.class);

    private HttpResponseUtil() {
    }

    /**
     * This method transforms the HttpResponse into HttpServletResponse.
     * @param response HttpServletResponse that needs to be populated with details.
     * @param httpResponse HttpResponse containing the response details.
     */
    public static void returnResponse(final HttpServletResponse response,
                                      final HttpResponse httpResponse)
            throws IOException {
        response.setStatus(httpResponse.getHttpStatus().value());
        response.getWriter().println(httpResponse.getMessage());
        LOG.info(String.valueOf(httpResponse.getHttpStatus().value()));
        LOG.info(String.valueOf(httpResponse.getMessage()));
        response.getWriter().flush();
        response.getWriter().close();
    }
}
