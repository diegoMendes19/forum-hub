
CREATE TABLE usuarios (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    senha VARCHAR(255) NOT NULL
);

-- Usuário admin (senha: 123456, hash BCrypt)
INSERT INTO usuarios (nome, email, senha) VALUES
('Admin', 'admin@forumhub.com', '$2a$10$7EqJtq98hPqEX7fNZaFWoO.W7hHnbEWuuZbjS1WlIc5rH/7xY.wy');
