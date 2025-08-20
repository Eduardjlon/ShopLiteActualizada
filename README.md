ShopLite
Descripción

ShopLite es un sistema de prueba que implementa autenticación de usuarios, gestión de productos y roles de acceso (ADMIN/USER), usando Java / Jakarta EE y PostgreSQL.
El proyecto mantiene la arquitectura por capas: Controllers → Services → Repositories y utiliza filtros HTTP para control de acceso (AuthFilter y AdminFilter).
La aplicación se desplegó en WildFly y se configuró el proyecto con JDBC PostgreSQL driver (versión 42.6.0.jar) agregado en pom.xml.

Tecnologías
Java / Jakarta EE
Servlets y JSP con JSTL
PostgreSQL 17 (driver JDBC: 42.6.0.jar)
JDBC puro
Bootstrap 5 para la interfaz

Configuración de la Base de Datos
Se utilizó Docker para ejecutar PostgreSQL.
Contenedor: postgres-db
Puerto: 5555
Nota: Mi PC utiliza los puertos 5432 y 5433 para otros programas, por lo que fue necesario asignar un puerto disponible distinto.
Usuario: postgres
Contraseña: admin123
Base de datos: appdb

Creación de tablas y datos de prueba
Se ejecutó el siguiente script SQL en pgAdmin / Query Tool, brindado en clase:

-- Tabla de usuarios
CREATE TABLE IF NOT EXISTS users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(120) NOT NULL,
    role VARCHAR(10) NOT NULL CHECK (role IN ('ADMIN','USER')),
    active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL DEFAULT NOW()
);

INSERT INTO users (username, password, role) VALUES
('admin', 'admin123', 'ADMIN'),
('alice', 'alice123', 'USER'),
('bob', 'bob123', 'USER')
ON CONFLICT (username) DO NOTHING;

-- Tabla de productos
CREATE TABLE IF NOT EXISTS products (
    id SERIAL PRIMARY KEY,
    name VARCHAR(120) NOT NULL,
    price NUMERIC(10,2) NOT NULL DEFAULT 0,
    stock INTEGER NOT NULL DEFAULT 0,
    created_at TIMESTAMP NOT NULL DEFAULT NOW()
);

INSERT INTO products (name, price, stock) VALUES
('Teclado', 120.00, 10),
('Mouse', 75.50, 25),
('Monitor', 999.99, 5)
ON CONFLICT DO NOTHING;


Usuarios de prueba
Usuario	Contraseña	Rol
admin	admin123	ADMIN
alice	alice123	USER
bob	    bob123	    USER

Funcionalidades Implementadas
Autenticación
Login con HttpSession.
Regeneración de sesión y almacenamiento de auth, username y role.
Filtro AuthFilter protege /app/*.
Filtro AdminFilter protege /app/users/*.

Gestión de Productos
CRUD completo: agregar, actualizar, eliminar productos.
Paginación para visualizar los productos.
Acceso permitido a usuarios autenticados (rol ADMIN puede gestionar todos los productos).

Gestión de Usuarios
No se implementó el CRUD de usuarios, ya que el requerimiento de CRUD completo se especificaba únicamente para Productos, según las viñetas del enunciado.

Interfaz
Reutiliza vistas JSP + JSTL + Bootstrap.
Formularios y tablas ajustadas para leer y escribir desde la base de datos.

Notas Adicionales
Toda la persistencia se migró de almacenamiento en memoria a PostgreSQL usando JDBC.
Se centralizó la conexión a la base de datos mediante la clase DbConnection (Singleton).
Se utilizó username en lugar de correo en la base de datos y código de autenticación, migrando así el diseño original que utilizaba correo electrónico.
Se probó usando pgAdmin para validar tablas, datos y consultas.
La aplicación se desplegó en WildFly, con la dependencia de PostgreSQL agregada en pom.xml.