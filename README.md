# Descrição do Projeto

O projeto de Lista de Tarefas em Java é uma aplicação que permite aos usuários gerenciar suas tarefas diárias, facilitando o acompanhamento e a conclusão de suas responsabilidades. 

# Tecnologias Utilizadas:
- Java versão **17** como linguagem de programação principal.
- Spring Boot para desenvolvimento do aplicativo.
- JUnit para testes unitários.
- Banco de dados H2 como armazenamento de dados.
- Swagger para documentação da API.
- Autenticação JWT para segurança da aplicação.
- Liquibase para gerenciamento de migrações de banco de dados.
- Enum para definir categorias, prioridades e estados de tarefas.


# Acesso ao Banco de Dados
Link de acesso: http://localhost:8080/h2-console
Driver Class: org.h2.Driver
JDBC URL: jdbc: h2:~/movie
User Name: sa
Password: (vazio, não informar)

# Acesso aos endoints através do Swagger
Link de acesso: http://localhost:8080/swagger-ui.html.

Para acessar os endpoints que estão protegídos é necessário se autenticar através do token que pode ser obtido no http://localhost:8080/login informando qualquer usuário e senha. Após o token ser gerado deve ser inserido no *Authorize*



[========]

&copy;DiegoHaefliger