package com.intern.user.security.header;

import com.intern.user.security.info.UserInfo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Vector;

public class HeaderUtil {

    public static HttpServletRequest createCustomHeader(
        HttpServletRequest request,
        UserInfo userInfo
    ) {
        String userId = String.valueOf(userInfo.userId());
        String username = userInfo.username();
        String role = String.valueOf(userInfo.role());

        return new HttpServletRequestWrapper(request) {
            @Override
            public String getHeader(String name) {
                if ("X-User-Id".equalsIgnoreCase(name)) {
                    return userId;
                }
                if ("X-User-Name".equalsIgnoreCase(name)) {
                    return username;
                }
                if ("X-User-Role".equalsIgnoreCase(name)) {
                    return role;
                }
                return super.getHeader(name);
            }

            @Override
            public Enumeration<String> getHeaderNames() {
                Vector<String> names = new Vector<>(Collections.list(super.getHeaderNames()));
                names.add("X-User-Id");
                names.add("X-User-Name");
                names.add("X-User-Role");
                return names.elements();
            }

            @Override
            public Enumeration<String> getHeaders(String name) {
                if ("X-User-Id".equalsIgnoreCase(name)) {
                    return new Vector<>(List.of(userId)).elements();
                }
                if ("X-User-Name".equalsIgnoreCase(name)) {
                    return new Vector<>(List.of(username)).elements();
                }
                if ("X-User-Role".equalsIgnoreCase(name)) {
                    return new Vector<>(List.of(role)).elements();
                }
                return super.getHeaders(name);
            }
        };
    }
}
