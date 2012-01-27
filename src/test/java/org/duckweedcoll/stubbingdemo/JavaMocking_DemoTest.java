package org.duckweedcoll.stubbingdemo;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import javax.servlet.http.HttpServletResponse;

/**
 * Copyright AdScale GmbH, Germany, 2007
 */
public class JavaMocking_DemoTest {

    String redirected = "none";

    @Test
    public void test() throws Exception {

        HttpServletResponse response = new HttpServletResponse_stubbed() {
            @Override
            public void sendRedirect(String loc) {
                redirected = loc;
            }
        };
        response.sendRedirect("/");
        assertEquals("/", redirected);
    }
}
