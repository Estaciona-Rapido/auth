CREATE TABLE estaciona_user(
    id_user INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    name VARCHAR(50) NOT NULL,
    password_hash TEXT NOT NULL
);

INSERT INTO estaciona_user (name, password_hash) VALUES ('admin', '$2a$10$c3gNi8dWeCxSYTGsn2.Kdujv73nn2opnMXc/ITTIdmSozusRoW0hG')
--SENHA: 12345678