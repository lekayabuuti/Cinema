# CinemaVVTS

Projeto desenvolvido como parte do Trabalho Prático da disciplina **Verificação, Validação e Teste de Software**, ministrada pelo **Prof. Dr. Lucas Oliveira** no **Instituto Federal de Educação, Ciência e Tecnologia de São Paulo – IFSP, Câmpus São Carlos**.

---

## Contexto Acadêmico

Este projeto tem como objetivo vivenciar o processo de desenvolvimento de um software de qualidade por meio da execução de diferentes atividades de teste. A abordagem utilizada inclui:

- **Behavior-Driven Development (BDD)** para especificação de requisitos  
- **Test-Driven Development (TDD)** para implementação do domínio e endpoints  
- Aplicação de técnicas de teste **funcional**, **estrutural** e **baseada em defeitos**  
- Desenvolvimento de um **front-end simples** e realização de **testes de integração e sistema** com foco na interface do usuário (UI)  

---

## Módulo Proposto

O módulo implementa um agregado de classes seguindo os princípios de **Domain-Driven Design (DDD)**, voltado ao gerenciamento de sessões de cinema.

### Aggregate

- **SessaoCinema (Aggregate Root)**: representa uma sessão específica de um filme, incluindo ID, nome do filme, horário, sala e todos os assentos 
- **Ingresso (Entidade Interna)**: representa um ingresso vinculado a um assento de uma determinada sessão, com informações como código do assento e status (reservado, cancelado ou disponível)  
- **Horario (Value Object)**: encapsula data e hora da sessão, garantindo validade e consistência  
- **Assento (Value Object)**: representa a posição do assento (ex: fileira A, número 10), usado para controle de ocupação  
- **SessaoCinemaRepository (Repositório)**: interface para persistência e recuperação de sessões, com métodos como salvar, buscar por data, atualizar e remover  

### Serviços de Aplicação

- `ReservaIngressoService`: lógica para reservar um ingresso, verificando disponibilidade  
- `CancelamentoService`: permite cancelar uma reserva até 5 minutos antes do início da sessão  
- `ListagemSessoesService`: retorna sessões disponíveis dentro de um intervalo entre duas datas específicas, que devem possuir no máximo um intervalo de 7 dias entre si, e não devem ser maiores que 7 dias da data atual  

---

## Equipe

**Projeto desenvolvido por:**  
- Breno Nascimento Lopes  : [@brenonlps](https://github.com/brenonlps)  
- Letícia Kaori Yabuuti: [@lekayabuuti](https://github.com/lekayabuuti)
- Lucas Matheus dos Santos: [@lucaofaz](https://github.com/LucaoFaz)

**Professor:**  
Prof. Dr. Lucas Oliveira  
