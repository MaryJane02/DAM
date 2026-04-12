# Assignment 1 — Hello Kotlin. Hello Android World!

Desenvolvimento de Aplicações Móveis<br>
Mariana Amador de Almeida, 49749<br>
15 de Março de 2026

---

## 1. Introdução

O presente trabalho tem como objetivo introduzir os conceitos fundamentais da linguagem Kotlin e do desenvolvimento de aplicações móveis Android. Através deste conjunto de exercícios, pretende-se proporcionar uma base sólida no que diz respeito à programação orientada a objetos, bem como à utilização das ferramentas necessárias para o desenvolvimento de aplicações móveis.

Inicialmente, são abordados conceitos básicos de Kotlin, incluindo tipos de dados, controlo de fluxo e manipulação de estruturas. Posteriormente, é realizada a criação de uma aplicação Android simples (“Hello World”), permitindo compreender a estrutura de um projeto Android. Por fim, são reforçados conceitos de programação orientada a objetos através da implementação de um sistema de biblioteca virtual.

---

## 2. Visão Geral do Sistema

O sistema desenvolvido divide-se em duas componentes principais:

* Aplicações de consola em Kotlin para exploração de conceitos fundamentais;
* Aplicação móvel Android desenvolvida em Android Studio.

### Funcionalidades principais:

* Implementação de exercícios em Kotlin (arrays, controlo de fluxo, sequências);
* Calculadora de consola com múltiplas operações;
* Aplicação Android com interface gráfica simples;
* Sistema de biblioteca virtual com gestão de livros.

### Casos de uso:

* Execução de programas Kotlin para validação de lógica;
* Interação com a aplicação Android através do emulador;
* Gestão de livros (adicionar, requisitar, devolver, pesquisar).

---

## 3. Arquitetura e Design

O projeto segue uma organização modular, separando claramente as diferentes componentes.

### Estrutura do projeto Kotlin:

* `dam.exer_1`, `dam.exer_2`, `dam.exer_3`: exercícios base;
* `dam.virtual_library`: sistema orientado a objetos.

### Estrutura do projeto Android:

* `manifests`: configuração da aplicação;
* `java`: código Kotlin (Activities);
* `res`: recursos (layouts, strings, imagens).

### Decisões de design:

* Utilização de princípios de programação orientada a objetos (herança, encapsulamento);
* Separação por packages para maior organização;
* Uso de layouts XML para definição da interface;
* Adoção de Kotlin como linguagem principal devido à sua integração com Android.

---

## 4. Implementação

A implementação do trabalho foi realizada em diferentes fases:

### Exercícios em Kotlin:

* Criação de arrays de quadrados perfeitos utilizando diferentes abordagens;
* Desenvolvimento de uma calculadora com `when`, tratamento de exceções e formatação de output;
* Modelação de uma sequência de saltos utilizando `generateSequence`.

### Aplicação Android:

* Criação de uma aplicação “Hello World”;
* Utilização de componentes como `TextView`, `ImageView` e `CalendarView`;
* Externalização de strings para `strings.xml`;
* Ajuste de layouts e constraints.

### Sistema de Biblioteca Virtual:

* Classe base `Book` com getters e setters personalizados;
* Subclasses `DigitalBook` e `PhysicalBook`;
* Classe `Library` responsável pela gestão de livros;
* Implementação das operações:

    * `addBook()`
    * `borrowBook()`
    * `returnBook()`
    * `searchByAuthor()`
* Utilização de companion objects e data classes.

---

## 5. Testes e Validação

A validação foi realizada através de testes manuais:

* Verificação dos outputs dos programas de consola;
* Testes da calculadora, incluindo casos de erro (ex: divisão por zero);
* Execução da aplicação Android em emulador (AVD);
* Validação da interface gráfica e comportamento dos componentes;
* Testes das operações da biblioteca virtual.

### Casos limite:

* Tentativa de requisição de livros sem cópias disponíveis;
* Inserção de valores inválidos na calculadora;
* Definição de valores negativos para cópias de livros.

### Limitações:

* Ausência de testes automatizados;
* Interface gráfica simples.

---

## 6. Instruções de Utilização

### Requisitos:

* IntelliJ IDEA (para exercícios Kotlin);
* Android Studio (versão estável);
* Android SDK e emulador.

### Configuração:

1. Abrir o projeto no IntelliJ ou Android Studio;
2. Garantir que todas as dependências estão instaladas;
3. Configurar um dispositivo virtual (AVD).

### Execução:

* Executar os ficheiros Kotlin através da função `main()`;
* Executar a aplicação Android através do emulador ou dispositivo físico.

---

# Secções de Engenharia de Software Autónoma

## 7. Estratégia de Prompting

As ferramentas de inteligência artificial foram utilizadas principalmente para apoio na redação do relatório e esclarecimento de conceitos.

Os prompts evoluíram de questões simples para pedidos mais estruturados, como a geração de um relatório completo com base nos requisitos do enunciado.

---

## 8. Workflow com Agentes Autónomos

A utilização de ferramentas de IA contribuiu para:

* Estruturação do relatório;
* Melhoria da clareza textual;
* Organização da informação.

O desenvolvimento do código foi realizado manualmente, respeitando as restrições do trabalho.

---

## 9. Verificação de Artefactos Gerados por IA

Todo o conteúdo gerado por IA foi revisto manualmente, garantindo:

* Correção técnica;
* Coerência com o trabalho realizado;
* Conformidade com os requisitos.

---

## 10. Contribuição Humana vs IA

* Desenvolvido pelo estudante:

    * Código Kotlin e Android;
    * Estrutura do projeto;
* Assistido por IA:

    * Redação do relatório;
    * Revisão textual.

---

## 11. Uso Ético e Responsável

A utilização de ferramentas de IA foi feita de forma responsável, respeitando as regras do enunciado. Não foi utilizada IA nas secções onde tal era proibido.

Todo o conteúdo foi compreendido e validado antes da submissão.

---

# Processo de Desenvolvimento

## 12. Controlo de Versões e Histórico de Commits

Foi utilizado Git para controlo de versões, com commits realizados ao longo do desenvolvimento para refletir a evolução do trabalho.

---

## 13. Dificuldades e Lições Aprendidas

### Dificuldades:

* Compreensão da estrutura de projetos Android;
* Utilização de constraints nos layouts;
* Aplicação correta de conceitos de OOP em Kotlin.

### Lições aprendidas:

* Importância da organização do código;
* Melhor compreensão de Kotlin;
* Familiarização com o ambiente Android.

---

## 14. Melhorias Futuras

* Desenvolvimento de interfaces mais complexas;
* Implementação de armazenamento persistente;
* Aplicação de princípios de Material Design;
* Introdução de testes automatizados.

---

## 15. Declaração de Utilização de IA (Obrigatório)

Ferramentas utilizadas:

* ChatGPT (apoio na redação e estruturação do relatório)

Todo o conteúdo foi revisto e validado, sendo a estudante totalmente responsável pelo trabalho apresentado.
