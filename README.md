# Constraker - Sistema de Gerenciamento para Confeiteiros

O **Constraker** é uma aplicação desktop desenvolvida em Java, utilizando Java Swing para a interface gráfica e MySQL para o BD relacional. O sistema permite que empreendedores de confeitaria cadastrem ingredientes, receitas e produtos finais, calculando automaticamente o custo e o lucro com base em uma porcentagem desejada.

## Funcionalidades

- **Cadastro de Ingredientes**: Permite cadastrar ingredientes com nome, quantidade, custo unitário e unidade de medida.
- **Cadastro de Receitas**: Permite criar receitas utilizando os ingredientes cadastrados, definindo as quantidades necessárias.
- **Cálculo de Custo e Lucro**: Calcula automaticamente o custo total da receita e o preço de venda com base em uma porcentagem de lucro desejada.
- **Gestão de Produtos Finais**: Permite criar produtos finais a partir das receitas cadastradas, definindo o preço de venda e o lucro esperado.

## Tecnologias Utilizadas

- **Java**: Linguagem de programação principal.
- **Java Swing**: Biblioteca para criação da interface gráfica.
- **MySQL**: Banco de dados para armazenamento dos dados.
- **JDBC**: Conexão entre a aplicação Java e o banco de dados MySQL.

## Estrutura do Projeto

O projeto segue a arquitetura **MVC (Model-View-Controller)** para separação de responsabilidades:

- **Model**: Contém as classes que representam as entidades do sistema (Ingrediente, Receita, ProdutoFinal, etc.).
- **View**: Contém as interfaces gráficas criadas com Java Swing.
- **Controller**: Contém as classes que fazem a intermediação entre a View e o Model, implementando a lógica de negócio.
- **DAO (Data Access Object)**: Contém as classes responsáveis pela comunicação com o banco de dados, realizando operações de CRUD (Create, Read, Update, Delete).

----

![java_cCyinVLDUP](https://github.com/pemaismais/pi_constraker/assets/143559792/2dac5623-ae54-4c10-97d6-76e316e9d3a4)


![java_If5yFIpZLa](https://github.com/pemaismais/pi_constraker/assets/143559792/60cdfefe-49a4-4b1d-9c56-ab8757491a9a)

![java_hWs4XWUYwZ](https://github.com/pemaismais/pi_constraker/assets/143559792/0c3adbe4-19a5-4c1c-ad95-729b480729d5)

![java_rTYyGtKJAG](https://github.com/pemaismais/pi_constraker/assets/143559792/480eeb11-451f-41fc-b748-420b123ec99a)
