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

- **SessaoCinema (Aggregate Root)**: representa uma sessão específica de um filme, incluindo horário, sala e ingressos disponíveis  
- **Ingresso (Entidade Interna)**: representa um ingresso vinculado à sessão, com informações como assento e status (reservado, cancelado)  
- **Horario (Value Object)**: encapsula data e hora da sessão, garantindo validade e consistência  
- **Assento (Value Object)**: representa a posição do assento (ex: fileira A, número 10), usado para controle de ocupação  
- **SessaoCinemaRepository (Repositório)**: interface para persistência e recuperação de sessões, com métodos como salvar, buscar por data, atualizar e remover  

### Serviços de Aplicação

- `ReservaIngressoService`: lógica para reservar um ingresso, verificando disponibilidade  
- `CancelamentoService`: permite cancelar uma reserva antes do início da sessão  
- `ListagemSessoesService`: retorna sessões disponíveis para uma data específica  

---

## Equipe

**Projeto desenvolvido por:**  
- Breno Lopes  : [@brenonlps](https://github.com/brenonlps)  
- Letícia Kaori: [@lekayabuuti](https://github.com/lekayabuuti)  

**Professor:**  
Prof. Dr. Lucas Oliveira  
