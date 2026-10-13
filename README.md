# Marketplace — Backend (Java 21 + Spring Boot + PostgreSQL)

## Como rodar
1. Inicie o Código
2. Coloque suas credenciais nos campos de "application.properties" e crie um banco "marketplace" no PostgreSQL, o código vai criar as tabelas automaticamente
3. Abra preferencialmente o POSTMAN para testar endpoints
4. A API sobe em `http://localhost:8080`

## Endpoints
| Método | Rota | Descrição |
|---|---|---|
| POST | /usuarios | Cadastra usuário |
| GET | /usuarios/{id} | Dados do usuário (inclui saldo de pontos) |
| POST | /auth/login | Login (e-mail + senha) |
| POST | /produtos | Cadastra produto (exige `vendedorId` existente) |
| GET | /produtos?vendedorId=&apenasDisponiveis= | Lista produtos |
| GET | /produtos/{id} | Detalhe do produto |
| PUT | /produtos/{id} | Atualiza nome/valor/quantidade |
| POST | /pedidos | Cria pedido (`compradorId`, `produtoId`, `quantidade`, `usarPontos`) |
| GET | /pedidos?compradorId=&vendedorId= | Lista pedidos |
| GET | /pedidos/{id} | Detalhe do pedido |
| PATCH | /pedidos/{id}/status | Muda status (`PAGO`, `ENVIADO`, `ENTREGUE`, `CANCELADO`) |


## Códigos de erro (JSON)
400 validação · 401 login inválido · 404 não encontrado · 409 CPF/e-mail duplicado · 422 regra de negócio violada
