# Assignment 3 — Annotations, MVVM and Jetpack Compose

Desenvolvimento de Aplicações Móveis  
Mariana Amador de Almeida, 49749  
3 de Maio de 2026

---

## 1. Introdução

No âmbito da unidade curricular de Desenvolvimento de Aplicações Móveis, foi proposto o desenvolvimento do Tutorial 3 — Annotations, MVVM and Jetpack Compose.

O trabalho incidiu sobre dois pontos principais: a implementação de um annotation processor em Kotlin e o desenvolvimento de uma aplicação Android utilizando a arquitetura MVVM e Jetpack Compose.

O objetivo foi consolidar conhecimentos sobre geração automática de código em tempo de compilação e aplicar boas práticas modernas no desenvolvimento de interfaces Android.

---

## 2. Visão Geral do Sistema

O sistema desenvolvido é composto por duas componentes distintas:

- Um projeto Kotlin responsável pelo processamento de anotações;
- Uma aplicação Android baseada em MVVM e Jetpack Compose.

Na primeira componente, foi criada uma annotation personalizada e um processor capaz de gerar código automaticamente. Este mecanismo permite adicionar comportamento a métodos sem alterar diretamente o código original.

Na segunda componente, foi desenvolvida uma aplicação meteorológica que obtém dados de uma API externa e apresenta informação como temperatura, vento e pressão atmosférica.

A aplicação encontra-se organizada em três camadas principais:

- Data: responsável pelos dados e comunicação com a API;
- ViewModel: responsável pela gestão do estado;
- UI: responsável pela apresentação dos dados.

---

## 3. Arquitetura e Design

O projeto segue uma abordagem modular e organizada por responsabilidades.

Na componente de annotation processing, foi adotada uma separação entre definição de annotations, processamento e aplicação, permitindo maior clareza e reutilização.

Na aplicação Android, foi utilizado o padrão MVVM, que separa a lógica de negócio da interface gráfica.

Foram utilizados os seguintes conceitos principais:

- Annotation Processing: geração de código em tempo de compilação;
- Wrapper Pattern: encapsulamento de classes para adicionar comportamento;
- MVVM: separação entre dados, lógica e interface;
- Jetpack Compose: construção declarativa da interface.

Esta abordagem permite um código mais organizado, modular e fácil de manter.

---

## 4. Implementação

A implementação foi dividida em duas partes principais.

Na primeira parte, foi criada uma annotation personalizada e um processor responsável por analisar o código e gerar classes adicionais durante a compilação. Este processo permite automatizar tarefas e reduzir repetição de código.

Na segunda parte, foi desenvolvida uma aplicação Android com Jetpack Compose. O estado da aplicação é gerido pelo ViewModel, enquanto a interface observa esse estado e é atualizada automaticamente.

Foram aplicados conceitos como gestão de estado, separação de responsabilidades e construção declarativa de interfaces.

---

## 5. Testes e Validação

A validação do sistema foi realizada através de testes funcionais manuais.

No annotation processor, foi verificado se o código era corretamente gerado e se o comportamento esperado era aplicado aos métodos anotados.

Na aplicação Android, foram testados:

- Atualização de dados meteorológicos;
- Comunicação com a API;
- Atualização do estado;
- Apresentação correta da interface.

Os testes realizados permitiram garantir o correto funcionamento do sistema.

---

## 6. Uso de Inteligência Artificial

Foram utilizadas ferramentas de inteligência artificial apenas como apoio à estruturação e escrita do relatório.

A implementação do código foi realizada de forma autónoma.

---

## 7. Conclusão

Este trabalho permitiu consolidar conhecimentos sobre annotation processing e desenvolvimento moderno de aplicações Android.

A utilização de MVVM e Jetpack Compose contribuiu para uma melhor organização do código e para a construção de interfaces mais flexíveis e reativas.

De forma geral, os objetivos propostos foram cumpridos com sucesso.