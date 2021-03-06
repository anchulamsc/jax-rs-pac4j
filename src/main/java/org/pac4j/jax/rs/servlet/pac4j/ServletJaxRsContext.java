package org.pac4j.jax.rs.servlet.pac4j;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.ext.Providers;

import org.pac4j.core.context.session.SessionStore;
import org.pac4j.core.util.CommonHelper;
import org.pac4j.jax.rs.pac4j.JaxRsContext;

/**
 * 
 * Notice: depending on the servlet implementations, there is often chances that the JAX-RS implementation will read the
 * input stream of the request when it arrives, and after that, it becomes impossible for the Servlet implementation to
 * read it. In particular this means that {@link HttpServletRequest#getParameter(String)} won't be able to return FORM
 * parameters. This is why we don't override {@link JaxRsContext#getRequestParameter(String)} to use the Servlet
 * implementation.
 * 
 * @author Victor Noel - Linagora
 * @since 1.0.0
 *
 */
public class ServletJaxRsContext extends JaxRsContext {

    private final HttpServletRequest request;

    public ServletJaxRsContext(Providers providers, ContainerRequestContext requestContext,
            SessionStore sessionStore, HttpServletRequest request) {
        super(providers, requestContext, sessionStore != null ? sessionStore : new ServletSessionStore());
        CommonHelper.assertNotNull("request", request);
        this.request = request;
    }
    
    @Override
    public void setSessionStore(SessionStore sessionStore) {
        if (sessionStore == null) {
            super.setSessionStore(new ServletSessionStore());
        } else {
            super.setSessionStore(sessionStore);
        }
    }
    
    public HttpServletRequest getRequest() {
        return request;
    }

    @Override
    public String getRemoteAddr() {
        return request.getRemoteAddr();
    }

}
