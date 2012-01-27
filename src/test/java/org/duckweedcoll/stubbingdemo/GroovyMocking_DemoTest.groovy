package org.duckweedcoll.stubbingdemo

import javax.servlet.http.HttpServletResponse
import org.junit.Test
import static org.junit.Assert.assertEquals

class GroovyMocking_DemoTest {

    @Test
    public void demoMockSession() throws Exception {
        def redirectedTo = 'none'

        assertEquals 'precond: should be set', redirectedTo, 'none'

        HttpServletResponse response = ['sendRedirect': {redirectedTo = it}] as HttpServletResponse

        response.sendRedirect('/')

        assertEquals 'post: we have been redirected', redirectedTo, '/'
    }

}
