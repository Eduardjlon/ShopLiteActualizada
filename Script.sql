-- Tabla de usuarios
CREATE TABLE IF NOT EXISTS users (
  id          SERIAL PRIMARY KEY,
  username    VARCHAR(50) UNIQUE NOT NULL,
  password    VARCHAR(120) NOT NULL,    -- DEMO: texto plano (no recomendado en producción)
  role        VARCHAR(10) NOT NULL CHECK (role IN ('ADMIN','USER')),
  active      BOOLEAN NOT NULL DEFAULT TRUE,
  created_at  TIMESTAMP NOT NULL DEFAULT NOW()
);

-- Datos iniciales
INSERT INTO users (username, password, role) VALUES
('admin', 'admin123', 'ADMIN'),
('alice', 'alice123', 'USER'),
('bob',   'bob123',   'USER')
ON CONFLICT (username) DO NOTHING;

-- Tabla de productos
CREATE TABLE IF NOT EXISTS products (
  id          SERIAL PRIMARY KEY,
  name        VARCHAR(120) NOT NULL,
  price       NUMERIC(10,2) NOT NULL DEFAULT 0,
  stock       INTEGER NOT NULL DEFAULT 0,
  created_at  TIMESTAMP NOT NULL DEFAULT NOW()
);

INSERT INTO products (name, price, stock) VALUES
('Teclado', 120.00, 10),
('Mouse',    75.50, 25),
('Monitor', 999.99, 5)
ON CONFLICT DO NOTHING;