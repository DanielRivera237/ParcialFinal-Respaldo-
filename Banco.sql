CREATE DATABASE BancoCentralNlogonia;
USE BancoCentralNlogonia;

CREATE TABLE Cliente(
    id_cliente VARCHAR(6) NOT NULL PRIMARY KEY,
    nombres VARCHAR(30) NOT NULL,
    apellidos VARCHAR(30) NOT NULL,
    direccion VARCHAR(100) NOT NULL,
    telefono VARCHAR(15) DEFAULT 'No especificado'
);


CREATE TABLE Admin(
    id_admin VARCHAR(5) NOT NULL PRIMARY KEY,
    nombre VARCHAR (15) NOT NULL,
    clave VARCHAR(20) NOT NULL
);

CREATE TABLE Tarjeta(
    numero VARCHAR(16) NOT NULL PRIMARY KEY,
    fecha_caducidad DATE NOT  NULL,
    tipo VARCHAR(15) NOT NULL,
    facilitador VARCHAR(20) NOT NULL,
    id_cliente VARCHAR(6) NOT NULL,

    FOREIGN KEY (id_cliente) REFERENCES Cliente(id_cliente) ON DELETE CASCADE
);

CREATE TABLE Transaccion(
    id_transaccion INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    fecha_compra DATE NOT NULL,
    total_gastado DECIMAL(10, 2) NOT NULL,
    descripcion_compra VARCHAR(200) DEFAULT 'No especificado',
    numero_tarjeta VARCHAR(16) NOT NULL,

    FOREIGN KEY (numero_tarjeta) REFERENCES Tarjeta(numero) ON DELETE CASCADE
);


-- Insertando datos
INSERT INTO Cliente (id_cliente, nombres, apellidos, direccion, telefono)
VALUES
('C0001', 'Juan Carlos', 'Pérez González', 'Calle Principal, San Salvador', '1234-5678'),
('C0002', 'María Isabel', 'González López', 'Avenida Central, Santa Tecla', '8765-4321'),
('C0003', 'Carlos Alberto', 'López Martínez', 'Calle del Rosario, Sonsonate', '2222-3333'),
('C0004', 'Ana María', 'Martínez Rodríguez', 'Calle del Sol, San Miguel', '4444-5555'),
('C0005', 'Luis Ángel', 'Rodríguez Hernández', 'Avenida de las Flores, La Libertad', '6666-7777'),
('C0006', 'Sofía Carolina', 'Hernández Sánchez', 'Calle de la Paz, La Paz', '8888-9999'),
('C0007', 'Diego Alejandro', 'Sánchez Flores', 'Avenida de los Arboles, San Vicente', '1111-2222'),
('C0008', 'Valentina Isabel', 'Flores Gómez', 'Calle de la Iglesia, Santa Ana', '3333-4444'),
('C0009', 'Sebastián Andrés', 'Gómez Vargas', 'Avenida del Lago, La Union', '5555-6666'),
('C0010', 'Camila Fernanda', 'Vargas Silva', 'Calle de la Amistad, Chalatenango', '7777-8888'),
('C0011', 'Mateo José', 'Silva Benítez', 'Avenida de la Cultura, San Salvador', '9999-0000'),
('C0012', 'Valeria Carolina', 'Benítez Morales', 'Calle de la Unidad, Sonsonate', '1234-5678'),
('C0013', 'Leonardo Andrés', 'Morales Ramírez', 'Avenida de la Paz, Santa Tecla', '8765-4321'),
('C0014', 'Isabella Sofía', 'Ramírez Cruz', 'Calle del Sol, San Miguel', '2222-3333'),
('C0015', 'Emilio Juan', 'Cruz Gutiérrez', 'Avenida de las Flores, La Libertad', '4444-5555'),
('C0016', 'Lucía Carolina', 'Gutiérrez Reyes', 'Calle de la Paz, La Paz', '6666-7777'),
('C0017', 'Felipe Diego', 'Reyes Jiménez', 'Avenida de los Arboles, San Vicente', '8888-9999'),
('C0018', 'Valentina Isabel', 'Jiménez Ortiz', 'Calle de la Iglesia, Santa Ana', '1111-2222'),
('C0019', 'Benjamín Mateo', 'Ortiz Moreno', 'Avenida del Lago, La Union', '3333-4444'),
('C0020', 'Mariana Sofía', 'Moreno Herrera', 'Calle de la Amistad, Chalatenango', '5555-6666'),
('C0021', 'Nicolás Juan', 'Herrera Medina', 'Avenida de la Cultura, San Salvador', '7777-8888'),
('C0022', 'Sara Carolina', 'Medina Vega', 'Calle de la Unidad, Sonsonate', '9999-0000'),
('C0023', 'Gabriel Diego', 'Vega Castro', 'Avenida de la Paz, Santa Tecla', '1234-5678'),
('C0024', 'Emilia Valentina', 'Castro Ramos', 'Calle del Sol, San Miguel', '2222-3333'),
('C0025', 'Samuel Mateo', 'Ramos Mendoza', 'Avenida de las Flores, La Libertad', '4444-5555'),
('C0026', 'Alejandra Carolina', 'Mendoza Aguilar', 'Calle de la Paz, La Paz', '6666-7777'),
('C0027', 'Daniel Felipe', 'Aguilar Torres', 'Avenida de los Arboles, San Vicente', '8888-9999'),
('C0028', 'Valeria Sofía', 'Torres Núñez', 'Calle de la Iglesia, Santa Ana', '1111-2222'),
('C0029', 'José Luis', 'Núñez Cabrera', 'Avenida del Lago, La Union', '3333-4444'),
('C0030', 'María Isabel', 'Cabrera Fuentes', 'Calle de la Amistad, Chalatenango', '5555-6666'),
('C0031', 'Andrés Juan', 'Fuentes Hurtado', 'Avenida de la Cultura, San Salvador', '7777-8888'),
('C0032', 'Catalina Sofía', 'Hurtado Sandoval', 'Calle de la Unidad, Sonsonate', '9999-0000'),
('C0033', 'Miguel Diego', 'Sandoval Guerrero', 'Avenida de la Paz, Santa Tecla', '1234-5678'),
('C0034', 'Carolina Valentina', 'Guerrero Villalobos', 'Calle del Sol, San Miguel', '2222-3333'),
('C0035', 'Fernando Juan', 'Villalobos Rojas', 'Avenida de las Flores, La Libertad', '4444-5555'),
('C0036', 'Ana Carolina', 'Rojas Salazar', 'Calle de la Paz, La Paz', '6666-7777'),
('C0037', 'Ricardo Diego', 'Salazar Córdova', 'Avenida de los Arboles, San Vicente', '8888-9999'),
('C0038', 'Paula Sofía', 'Córdova Rivera', 'Calle de la Iglesia, Santa Ana', '1111-2222'),
('C0039', 'Javier Luis', 'Rivera Suárez', 'Avenida del Lago, La Union', '3333-4444'),
('C0040', 'Daniela Carolina', 'Suárez Mora', 'Calle de la Amistad, Chalatenango', '5555-6666'),
('C0041', 'Pablo Andrés', 'Mora Vásquez', 'Avenida de la Cultura, San Salvador', '7777-8888'),
('C0042', 'Gabriela Isabel', 'Vásquez Castillo', 'Calle de la Unidad, Sonsonate', '9999-0000'),
('C0043', 'Alejandro Juan', 'Castillo Orozco', 'Avenida de la Paz, Santa Tecla', '1234-5678'),
('C0044', 'Fernanda Carolina', 'Orozco Escobar', 'Calle del Sol, San Miguel', '2222-3333'),
('C0045', 'Juan Diego', 'Escobar Ríos', 'Avenida de las Flores, La Libertad', '4444-5555'),
('C0046', 'Sofía Isabel', 'Ríos Contreras', 'Calle de la Paz, La Paz', '6666-7777'),
('C0047', 'Diego Felipe', 'Contreras Méndez', 'Avenida de los Arboles, San Vicente', '8888-9999'),
('C0048', 'Valentina Sofía', 'Méndez Cano', 'Calle de la Iglesia, Santa Ana', '1111-2222'),
('C0049', 'Sebastián Mateo', 'Cano Villa', 'Avenida del Lago, La Union', '3333-4444'),
('C0050', 'Camila Carolina', 'Villa Pérez', 'Calle de la Amistad, Chalatenango', '5555-6666');

INSERT INTO Tarjeta (numero, fecha_caducidad, tipo, facilitador, id_cliente)
VALUES
('1234567890123456', '2025-12-31', 'Crédito', 'Mastercard', 'C0003'),
('2345678901234567', '2024-06-30', 'Débito', 'Visa', 'C0010'),
('3456789012345678', '2023-09-30', 'Crédito', 'American Express', 'C0015'),
('4567890123456789', '2026-03-31', 'Débito', 'Mastercard', 'C0022'),
('5678901234567890', '2025-01-31', 'Crédito', 'Visa', 'C0029'),
('6789012345678901', '2024-11-30', 'Débito', 'American Express', 'C0006'),
('7890123456789012', '2023-08-31', 'Crédito', 'Mastercard', 'C0017'),
('8901234567890123', '2026-05-31', 'Débito', 'Visa', 'C0024'),
('9012345678901234', '2025-02-28', 'Crédito', 'American Express', 'C0031'),
('0123456789012345', '2024-10-31', 'Débito', 'Mastercard', 'C0038'),
('1111222233334444', '2023-07-31', 'Crédito', 'Visa', 'C0009'),
('2222333344445555', '2026-04-30', 'Débito', 'American Express', 'C0019'),
('3333444455556666', '2025-03-31', 'Crédito', 'Mastercard', 'C0026'),
('4444555566667777', '2024-12-31', 'Débito', 'Visa', 'C0033'),
('5555666677778888', '2023-09-30', 'Crédito', 'American Express', 'C0040'),
('6666777788889999', '2026-06-30', 'Débito', 'Mastercard', 'C0002'),
('7777888899990000', '2025-01-31', 'Crédito', 'Visa', 'C0012'),
('8888999900001111', '2024-11-30', 'Débito', 'American Express', 'C0020'),
('9999000011112222', '2023-08-31', 'Crédito', 'Mastercard', 'C0027');


INSERT INTO Transaccion (fecha_compra, total_gastado, descripcion_compra, numero_tarjeta)
VALUES
('2022-01-01', 50.00, 'Grocery shopping', '0123456789012345'),
('2022-01-02', 25.50, 'Coffee shop', '1111222233334444'),
('2022-01-03', 100.75, 'Electronics store', '1234567890123456');



/*DROP TABLE Transaccion;
DROP TABLE Tarjeta;*/

SELECT
    CONCAT(Cliente.nombres, ' ', Cliente.apellidos) AS cliente,
    COUNT(Transaccion.id_transaccion) AS cantidad_compras,
    SUM(Transaccion.total_gastado) AS total_gastado
FROM
    Cliente
INNER JOIN
    Tarjeta ON Cliente.id_cliente = Tarjeta.id_cliente
INNER JOIN
    Transaccion ON Tarjeta.numero = Transaccion.numero_tarjeta
WHERE
    Tarjeta.facilitador = 'Mastercard'
group by Cliente.nombres, Cliente.apellidos;


-- ---------------------------------
INSERT INTO Admin(id_admin, nombre, clave) VALUES
    ('0010', 'Gerardo', '00104923')




