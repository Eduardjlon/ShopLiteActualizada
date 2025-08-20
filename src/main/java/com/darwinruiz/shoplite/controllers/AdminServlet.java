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

@WebServlet("/admin")
public class AdminServlet extends HttpServlet {

    private ProductRepository repo;

    @Override
    public void init() {
        try {
            repo = new ProductRepository();
        } catch (SQLException e) {
            throw new RuntimeException("Error inicializando ProductRepository", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            // Obtener todos los productos para mostrar en la tabla
            List<Product> products = repo.listAll();
            req.setAttribute("products", products);

            req.getRequestDispatcher("/admin.jsp").forward(req, resp);
        } catch (Exception e) {
            throw new IOException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String action = req.getParameter("action");
        String idParam = req.getParameter("id");
        String name = req.getParameter("name");
        String priceParam = req.getParameter("price");
        String stockParam = req.getParameter("stock");

        int id = 0;
        if (idParam != null && !idParam.isEmpty()) {
            try { id = Integer.parseInt(idParam); } catch (NumberFormatException ignored) {}
        }

        double price = 0;
        int stock = 0;
        try {
            if(priceParam != null) price = Double.parseDouble(priceParam);
            if(stockParam != null) stock = Integer.parseInt(stockParam);
        } catch(NumberFormatException e) {
            resp.sendRedirect(req.getContextPath() + "/admin?err=1");
            return;
        }

        try {
            switch(action != null ? action : "add") {
                case "edit":
                    if(id > 0 && name != null && !name.trim().isEmpty()) {
                        repo.update(new Product(id, name.trim(), price, stock));
                    }
                    break;
                case "delete":
                    if(id > 0) {
                        repo.delete(id);
                    }
                    break;
                default: // add
                    if(name != null && !name.trim().isEmpty()) {
                        repo.add(new Product(0, name.trim(), price, stock));
                    }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            resp.sendRedirect(req.getContextPath() + "/admin?err=2");
            return;
        }

        resp.sendRedirect(req.getContextPath() + "/admin");
    }
}
