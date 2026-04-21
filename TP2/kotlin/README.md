# Assignment 2 — Cool Weather App!

Desenvolvimento de Aplicações Móveis<br>
Mariana Amador de Almeida, 49749<br>
12 de Abril de 2026

---

## 1. Introdução

No âmbito da unidade curricular de Desenvolvimento de Aplicações Móveis, foi proposto o desenvolvimento do Tutorial 2 – Cool Weather Application, cujo principal objetivo é consolidar competências na linguagem Kotlin e no desenvolvimento de aplicações Android. Este trabalho encontra-se estruturado em duas componentes principais: uma primeira parte dedicada à resolução de exercícios em Kotlin, com foco em conceitos fundamentais de programação, e uma segunda parte orientada ao desenvolvimento de uma aplicação móvel funcional.

A secção inicial do trabalho visa o reforço de conceitos como funções de ordem superior, generics, lambdas e sobrecarga de operadores, incentivando a compreensão da documentação e a implementação autónoma de soluções. Já a componente Android consiste na criação de uma aplicação de meteorologia que obtém dados em tempo real através de uma API REST, apresentando informação relevante ao utilizador e adaptando a interface a diferentes tamanhos de ecrã e orientações .

Ao longo do desenvolvimento da aplicação, são explorados diversos aspetos essenciais do ecossistema Android, incluindo a construção de interfaces gráficas, gestão de recursos e, opcionalmente, a aplicação de boas práticas de arquitetura como o padrão MVVM. Este trabalho permite, assim, uma abordagem integrada entre conceitos teóricos e a sua aplicação prática no desenvolvimento de software móvel.

---

## 2. Visão Geral do Sistema 

O sistema desenvolvido no âmbito deste trabalho consiste numa aplicação móvel Android denominada Cool Weather App, complementada por um conjunto de componentes desenvolvidos em Kotlin na fase inicial do projeto. O objetivo global do sistema é permitir a visualização de informação meteorológica em tempo real para uma determinada localização geográfica, ao mesmo tempo que consolida competências fundamentais de programação e desenvolvimento mobile.

A aplicação móvel baseia-se numa arquitetura cliente que comunica com um serviço externo de dados meteorológicos, recorrendo a uma API REST (Open-Meteo). Através desta integração, a aplicação obtém dados como temperatura, velocidade e direção do vento, pressão atmosférica e estado do tempo, apresentando-os ao utilizador de forma clara e visualmente apelativa .

Do ponto de vista funcional, o sistema permite ao utilizador:

- Visualizar dados meteorológicos atuais para uma localização específica;
- Atualizar a localização através da introdução de coordenadas (latitude e longitude);
- Observar alterações dinâmicas na interface com base nas condições meteorológicas e período do dia;
- Interagir com uma interface adaptável a diferentes tamanhos de ecrã e orientações (portrait e landscape).

A aplicação inclui ainda mecanismos de adaptação da interface, nomeadamente através da utilização de diferentes layouts e temas (modo claro e escuro).

A nível estrutural, o sistema pode ser dividido em três componentes principais:

- Interface de Utilizador (UI): responsável pela apresentação dos dados e interação com o utilizador;
- Lógica de Aplicação: responsável pelo processamento dos dados obtidos e atualização da interface;
- Camada de Dados: responsável pela comunicação com a API externa e obtenção da informação meteorológica.

Por fim, o desenvolvimento do sistema segue uma abordagem progressiva, iniciando-se com exercícios fundamentais em Kotlin (como manipulação de dados, pipelines e estruturas genéricas), que servem de base para a implementação da aplicação Android. Esta abordagem permite uma transição natural entre conceitos teóricos e a sua aplicação prática num sistema real.

---

## 3. Arquitetura e Design

Este projeto encontra-se dividido em duas componentes principais: exercícios em Kotlin e uma aplicação Android (Cool Weather App). 

### 3.1 Estrutura do Repositório 

A organização do repositório segue a seguinte abordagem:

```plaintext
TP2/
│
├── Kotlin/
│   ├── exer1/
│   │   └── Event.kt
│   ├── exer2/
│   │   └── Cache.kt
│   ├── exer3/
│   │   └── Pipeline.kt
│   ├── exer4/
│   │   └── Vec2.kt
│
└── Android/

````

### 3.2 Padrões de Design Utilizados

#### - Programação Funcional 
Nos exercícios Kotlin foram utilizados conceitos associados a programação funcional:

- Higher-Order Functions → (processEvents, Pipeline)
- Extension Functions → (filterByUser, totalSpent)
- Imutabilidade e Transformação de Dados

Justificação: 
- Promove código mais declarativo e reutilizável;
- Facilita testes e manutenção;
- Está alinhado com boas práticas modernas de Kotlin.

#### - Generic Programming 
No exercício Cache.kt foi utilizado:

- Generics (Cache<K, V>)

Justificação:
- Permite reutilização da estrutura para múltiplos tipos;
- Garante type safety em tempo de compilação.

#### - Pipeline Pattern 
No exercício Pipeline.kt:

- Implementação de um Data Processing Pipeline

Justificação:

- Permite encadear transformações de forma flexível;
- Facilita extensão (adicionar/remover etapas);
- Aproxima-se de arquiteturas usadas em sistemas reais de processamento de dados.

#### - Operator Overloading
No Vec2.kt:

- Sobrecarga de operadores (+, -, *, compareTo)

Justificação:

- Torna o código mais expressivo e próximo da matemática;
- Melhora legibilidade e usabilidade da classe.

### 3.3 Arquitetura da Aplicação Android
A aplicação segue uma arquitetura simplificada baseada na separação de responsabilidades:

## 4. Implementação
Este capítulo descreve os principais módulos desenvolvidos, os conceitos utilizados e a sua fundamentação teórica, com base nos exercícios em Kotlin realizados na primeira parte do trabalho.

### 4.1 Módulos Principais

A primeira parte do trabalho prático encontra-se dividida em quatro módulos independentes:

- Exer1 — Event Processing (Event.kt)
- Exer2 — Generic Cache (Cache.kt)
- Exer3 — Data Pipeline (Pipeline.kt)
- Exer4 — Vector Library (Vec2.kt)

Cada módulo aborda um conjunto específico de conceitos fundamentais da linguagem Kotlin.

### 4.2 Conceitos e Fundamentos Teóricos

#### - Programação Funcional

A programação funcional é um paradigma onde as funções são tratadas como valores de primeira classe, podendo ser passadas como argumento ou retornadas por outras funções.

Conceitos utilizados:
- Higher-Order Functions: Funções que recebem outras funções como parâmetro ou retornam funções.
- Lambdas: Expressões anónimas usadas para definir comportamento de forma concisa.
- Imutabilidade: Evita alteração direta de dados, promovendo segurança e previsibilidade.

#### - Extension Functions

Permitem adicionar funcionalidades a classes existentes sem modificar o seu código original.

Vantagens:
- Código mais modular;
- Melhor legibilidade;
- Evita herança desnecessária.

#### - Generics 

Permitem criar estruturas reutilizáveis que funcionam com diferentes tipos de dados.

Conceito:
- Definição de tipos genéricos (<K, V>)
- Garantia de segurança de tipos (type safety)

#### - Data Processing Pipeline

Um pipeline consiste numa sequência de transformações aplicadas a dados.

Conceito:
- Cada etapa transforma o resultado da anterior;
- Permite composição de funções.

#### - Operator Overloading

Permite redefinir operadores para classes personalizadas.

Vantagens:
- Código mais intuitivo;
- Representação natural de conceitos matemáticos.

#### - Cálculo Vetorial 

No exercício Vec2, foram utilizados conceitos matemáticos:

- Magnitude (norma)
- Produto escalar 
- Normalização

## 5. Testes e Validação
O processo de validação do sistema teve como objetivo garantir o correto funcionamento das funcionalidades implementadas, bem como a fiabilidade e consistência dos resultados obtidos.

De forma geral, os testes realizados focaram-se na verificação dos módulos desenvolvidos em Kotlin e na aplicação Android, assegurando que cada componente cumpre os requisitos definidos no enunciado.

### 5.1 Estratégia de Testes

A abordagem adotada baseou-se em testes funcionais manuais, consistindo na execução dos diferentes módulos com dados de entrada representativos e na comparação dos resultados obtidos com os resultados esperados.

Foram considerados os seguintes aspetos:

- Correção lógica das operações implementadas;
- Consistência dos resultados produzidos;
- Comportamento do sistema perante diferentes tipos de input;
- Tratamento de casos limite e situações de erro.

### 5.2 Validação dos Módulos Kotlin

Os módulos desenvolvidos em Kotlin foram testados individualmente, garantindo que cada funcionalidade produz os resultados esperados. A validação incidiu sobre:

- Processamento e manipulação de dados;
- Aplicação correta de funções e transformações;
- Comportamento de estruturas genéricas;
- Execução de operações matemáticas e lógicas.

Estes testes permitiram confirmar a correta aplicação dos conceitos fundamentais utilizados, como programação funcional, generics e abstração de dados.

### 5.3 Validação da Aplicação Android

Na aplicação Android, os testes centraram-se na verificação do comportamento global do sistema, incluindo:

- Obtenção e apresentação de dados provenientes da API externa;
- Atualização dinâmica da interface;
- Interação com o utilizador;
- Adaptação a diferentes orientações de ecrã.

## 6.Instruções de Utilização 

### 6.1 Executar exercícios Kotlin
1. Abrir o ficheiro pretendido (Event.kt, Cache.kt, Pipeline.kt, Vec2.kt);
2. Executar a função main() associada;
3. Verificar os resultados na consola.


### 6.2 Executar a aplicação Android
1. Abrir o projeto no Android Studio;
2. Configurar um dispositivo: Emulador (AVD), ou Dispositivo físico Android;
3. Carregar em Run  para compilar e executar a aplicação.

# Autonomous Software Engineering

## 7. Estratégia de Prompting 

Durante o desenvolvimento deste projeto, foram utilizadas ferramentas de inteligência artificial como apoio à compreensão de conceitos teóricos e à estruturação da documentação.

A estratégia de prompting evoluiu ao longo do tempo, começando com questões mais gerais e tornando-se progressivamente mais específica e orientada ao contexto do projeto.

Tipos de prompts utilizados:
- Explicação de conceitos (ex: generics, lambdas, MVVM);
- Apoio na organização de conteúdos;
- Reformulação e melhoria da escrita técnica.

Evolução:
- Fase inicial: pedidos genéricos para compreensão global;
- Fase posterior: pedidos mais detalhados e focados em problemas concretos.

Esta evolução permitiu melhorar a qualidade e relevância das respostas obtidas.

---

## 8. Autonomous Agent Workflow

As ferramentas de IA foram utilizadas como apoio ao longo de várias fases do desenvolvimento:

- Planeamento: organização da estrutura do relatório e definição de conteúdos;
- Compreensão: esclarecimento de conceitos e boas práticas;
- Documentação: apoio na escrita e formatação dos diferentes capítulos.

O desenvolvimento e implementação das funcionalidades foram conduzidos de forma autónoma, sendo a IA utilizada como suporte complementar.

---

## 9. Verificação dos Conteúdos Gerados

A validação dos conteúdos obtidos com apoio de IA foi realizada através de:

- Revisão manual da informação;
- Comparação com documentação oficial;

Este processo garantiu a correção e adequação dos conteúdos utilizados no projeto.

---

## 10. Contribuição Humana vs IA
Contribuição Humana:
- Implementação dos exercícios em Kotlin;
- Desenvolvimento da aplicação Android;
- Integração com a API externa;
- Testes e validação do sistema;
- Tomada de decisões de design e arquitetura.

Contribuição de IA:
- Apoio na explicação de conceitos teóricos;
- Suporte na organização e escrita da documentação.

---

## 11. Utilização Ética e Responsável

A utilização de ferramentas de IA foi realizada de forma responsável, tendo em consideração:

- A validação de toda a informação antes da sua utilização;
- A garantia de compreensão dos conceitos aplicados;
- A utilização da IA como ferramenta de apoio e não como substituição do processo de aprendizagem.

---

# Processo de Desenvolvimento

## 12. Controlo de Versões e Histórico de Commits

O controlo de versões foi realizado utilizando o GitHub.

Cada commit corresponde a uma alteração específica.

---

## 13. Uso de IA 

Durante o desenvolvimento deste projeto, foram utilizadas ferramentas de inteligência artificial como apoio à compreensão de conceitos e à elaboração da documentação.

A utilização destas ferramentas teve como objetivo auxiliar o processo de aprendizagem e melhorar a qualidade da apresentação dos conteúdos.

Declaração:
Todo o trabalho foi desenvolvido, testado e validado pelo autor, que assume total responsabilidade pelo conteúdo apresentado.




