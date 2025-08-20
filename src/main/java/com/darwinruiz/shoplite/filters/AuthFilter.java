package com.darwinruiz.shoplite.filters;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

/**
 * Requisito: bloquear todo lo no público si no existe una sesión con auth=true.
 */
public class AuthFilter implements Filter {
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest r = (HttpServletRequest) req;
        HttpServletResponse p = (HttpServletResponse) res;

        String uri = r.getRequestURI();
        boolean esPublica =
                uri.endsWith("/index.jsp") || uri.endsWith("/login.jsp") ||
                        uri.contains("/auth/login") || uri.endsWith("/");

        if (esPublica) {
            // Página pública, permitir acceso
            chain.doFilter(req, res);
            return;
        }

        HttpSession session = r.getSession(false); // No crear si no existe
        if (session != null) {
            Boolean auth = (Boolean) session.getAttribute("auth");
            if (auth != null && auth) {
                // Sesión válida y autenticada
                chain.doFilter(req, res);
                return;
            }
        }

        // No tiene sesión válida, redirigir a login.jsp
        p.sendRedirect(r.getContextPath() + "/login.jsp");
    }
}
