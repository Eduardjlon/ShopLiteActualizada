package com.darwinruiz.shoplite.filters;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

/**
 * Requisito: permitir /admin solo a usuarios con rol "ADMIN"; los demás ven 403.jsp.
 */
public class AdminFilter implements Filter {

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        // Obtener sesión sin crear nueva
        HttpSession session = request.getSession(false);

        if (session != null) {
            String role = (String) session.getAttribute("role");
            if (role != null && "ADMIN".equalsIgnoreCase(role.trim())) {
                // Usuario con rol ADMIN → permite continuar
                chain.doFilter(req, res);
                return;
            }
        }

        // Usuario sin rol válido → redirige a 403.jsp
        RequestDispatcher dispatcher = req.getRequestDispatcher("/403.jsp");
        dispatcher.forward(req, res);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // No se necesita inicialización
    }

    @Override
    public void destroy() {
        // No se necesita limpieza
    }
}
