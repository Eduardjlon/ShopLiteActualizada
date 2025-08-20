package com.darwinruiz.shoplite.controllers;

import com.darwinruiz.shoplite.models.Product;
import com.darwinruiz.shoplite.repositories.ProductRepository;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/home")
public class HomeServlet extends HttpServlet {

    private ProductRepository repo;

    @Override
    public void init() {
        try {
            repo = new ProductRepository(); // Conexión a PostgreSQL
        } catch (SQLException e) {
            throw new RuntimeException("Error inicializando ProductRepository", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int page = 1;
        int size = 10;

        try {
            String pageParam = req.getParameter("page");
            String sizeParam = req.getParameter("size");

            if (pageParam != null) page = Integer.parseInt(pageParam);
            if (sizeParam != null) size = Integer.parseInt(sizeParam);
        } catch (NumberFormatException e) {
            // Valores por defecto si parse falla
        }

        if (page < 1) page = 1;
        if (size < 1) size = 10;

        try {
            int offset = (page - 1) * size;
            List<Product> items = repo.findAll(size, offset);

            // Para mostrar total de productos y paginación
            int total = repo.countAll(); // Método nuevo que cuenta todos los productos

            int maxPage = (int) Math.ceil((double) total / size);
            if (page > maxPage) page = maxPage > 0 ? maxPage : 1;

            req.setAttribute("items", items);
            req.setAttribute("page", page);
            req.setAttribute("size", size);
            req.setAttribute("total", total);

            req.getRequestDispatcher("/home.jsp").forward(req, resp);

        } catch (SQLException e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error accediendo a la base de datos");
        } catch (Exception e) {
            throw new IOException(e);
        }
    }
}
