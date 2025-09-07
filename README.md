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

## User Story

> Como **usuário**, eu quero **reservar um ingresso para uma sessão de cinema, escolhendo o assento**, para que **eu possa garantir meu lugar no filme**  

---

## Cenários BDD

### Cenário 1 – Reserva bem-sucedida
**Dado que** existe uma sessão de cinema com ingressos disponíveis  
**E** o assento escolhido está livre  
**Quando** o usuário reservar o ingresso para esse assento  
**Então** o ingresso deve ser criado  
**E** o assento marcado como reservado  
**E** a sessão atualizada com a nova reserva  

---

### Cenário 2 – Assento já reservado
**Dado que** o assento escolhido já está reservado  
**Quando** o usuário tentar reservar o ingresso  
**Então** a reserva não deve ser efetuada  
**E** exibir "Assento indisponível"  

---

### Cenário 3 – Sessão do passado
**Dado que** a sessão está em data/hora passada  
**Quando** o usuário tentar reservar o ingresso  
**Então** a reserva não deve ser efetuada  
**E** exibir "Sessão encerrada"  

---

### Cenário 4 – Sessão lotada
**Dado que** a sessão está com todos os assentos preenchidos  
**Quando** o usuário tentar reservar o ingresso  
**Então** a reserva não deve ser efetuada  
**E** exibir "Sessão lotada"  

---

## Equipe

**Projeto desenvolvido por:**  
- Breno Lopes  : [@brenonlps](https://github.com/brenonlps)  
- Letícia Kaori: [@lekayabuuti](https://github.com/lekayabuuti)  

**Professor:**  
Prof. Dr. Lucas Oliveira  
