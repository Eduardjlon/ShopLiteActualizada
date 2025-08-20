<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!doctype html>
<html lang="es">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width,initial-scale=1">
    <title>ShopLite • Admin</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">
<nav class="navbar navbar-expand-lg bg-white border-bottom">
    <div class="container">
        <a class="navbar-brand text-danger" href="${pageContext.request.contextPath}/home">ShopLite • Admin</a>
    </div>
</nav>

<section class="container my-5" style="max-width:720px;">
    <c:if test="${param.err=='1'}">
        <div class="alert alert-danger">Datos inválidos</div>
    </c:if>

    <!-- Formulario Nuevo/Editar Producto -->
    <div class="card shadow-sm mb-4">
        <div class="card-body p-4">
            <h3 class="mb-3">Nuevo producto</h3>
            <form id="productForm" method="post" action="${pageContext.request.contextPath}/admin" class="row g-3">
                <input type="hidden" name="id" id="productId" value="">
                <input type="hidden" name="action" id="formAction" value="add">
                <div class="col-12">
                    <label class="form-label">Nombre</label>
                    <input class="form-control" name="name" id="productName" placeholder="Teclado 60%" required>
                </div>
                <div class="col-12">
                    <label class="form-label">Precio</label>
                    <input class="form-control" name="price" id="productPrice" placeholder="59.99" required>
                </div>
                <div class="col-12">
                    <label class="form-label">Stock</label>
                    <input class="form-control" name="stock" id="productStock" placeholder="10" required>
                </div>
                <div class="col-12">
                    <button type="submit" class="btn btn-primary" id="submitBtn">Guardar</button>
                </div>
            </form>
        </div>
    </div>

    <!-- Tabla Productos -->
    <div class="card shadow-sm">
        <div class="card-body p-4">
            <h3 class="mb-3">Productos existentes</h3>
            <table class="table table-bordered">
                <thead>
                <tr>
                    <th>ID</th>
                    <th>Nombre</th>
                    <th>Precio</th>
                    <th>Stock</th>
                    <th>Acciones</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="p" items="${products}">
                    <tr>
                        <td>${p.id}</td>
                        <td>${p.name}</td>
                        <td>${p.price}</td>
                        <td>${p.stock}</td>
                        <td>
                            <button class="btn btn-sm btn-warning"
                                    onclick="editProduct('${p.id}', '${p.name}', '${p.price}', '${p.stock}')">Editar</button>
                            <form method="post" style="display:inline-block;" action="${pageContext.request.contextPath}/admin">
                                <input type="hidden" name="id" value="${p.id}">
                                <input type="hidden" name="action" value="delete">
                                <button class="btn btn-sm btn-danger">Borrar</button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</section>

<script>
    function editProduct(id, name, price, stock) {
        // Rellenar el formulario con los datos del producto
        document.getElementById('productId').value = id;
        document.getElementById('productName').value = name;
        document.getElementById('productPrice').value = price;
        document.getElementById('productStock').value = stock;

        // Cambiar acción y texto del botón
        document.getElementById('formAction').value = 'edit';
        document.getElementById('submitBtn').textContent = 'Actualizar';
        document.getElementById('productForm').scrollIntoView({ behavior: 'smooth' });
    }
</script>

</body>
</html>
