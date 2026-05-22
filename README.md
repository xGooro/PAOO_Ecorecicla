# ♻️ EcoRecicla - Gestão Ambiental de Resíduos

Projeto Full Stack (Java + React) desenvolvido como ferramenta de gestão ambiental para prefeituras e ONGs. A aplicação permite consultar, cadastrar e analisar dados reais do SNIS (Sistema Nacional de Informações sobre Saneamento) sobre a geração de resíduos e taxas de reciclagem por município.

Projeto desenvolvido para a disciplina de Programação Avançada Orientada a Objetos do curso de Análise e Desenvolvimento de Sistemas da Fatec Ipiranga, sob orientação da Profa. Mestre Sirley Ambrosia Vitorio Addão.

Membros do Grupo
*Heloisa bertoli
*Ariel pereira
*Caio eduardo

---

## 🛠️ Tecnologias Utilizadas

**Backend:**
* Java 21
* Spring Boot (v4.0.6)
* Spring Data JPA
* Banco de Dados H2 (In-Memory)
* Lombok
* OpenCSV (Para processamento de carga de dados)

**Frontend:**
* React
* Vite
* Axios (Integração de APIs)

---

## 🏗️ Arquitetura do Sistema (Backend)

O projeto segue rigorosamente a arquitetura em camadas MVC/REST:

1.  **Model (`@Entity`):** Entidade `RegistroResiduo` mapeando os dados de sustentabilidade (id, município, estado, quantidade gerada, taxa de reciclagem e ano).
2.  **Repository:** Interface estendendo `JpaRepository` contendo métodos de consulta customizados (`findByEstado` e `findByTaxaReciclagemLessThan`).
3.  **Service:** Isola a lógica de negócio e gerencia a injeção do arquivo CSV real via bibliotecas especializadas no momento da inicialização (`@PostConstruct`).
4.  **Controller:** Exposição dos endpoints RESTful com mapeamento de requisições, injeção de dependência e políticas de CORS liberadas.

---

## 🔗 Endpoints da API

A API está documentada e responde no endereço base: `http://localhost:8080/residuos`

| Método | Rota | Descrição | Status HTTP |
| :--- | :--- | :--- | :--- |
| **GET** | `/residuos` | Retorna a lista completa de todos os registros ambientais. | `200 OK` |
| **GET** | `/residuos/{id}` | Busca um registro específico pelo seu ID. | `200 OK` ou `404 Not Found` |
| **GET** | `/residuos/estado/{estado}` | Filtra e retorna os registros pertencentes a um Estado específico (Ex: SP). | `200 OK` |
| **GET** | `/residuos/taxa/{taxa}` | Retorna municípios com taxa de reciclagem abaixo da meta especificada. | `200 OK` |
| **POST** | `/residuos` | Cadastra um novo registro de resíduo. Requer JSON no body. | `201 Created` |
| **PUT** | `/residuos/{id}` | Atualiza os dados de um registro existente pelo ID. | `200 OK` ou `404 Not Found` |
| **DELETE** | `/residuos/{id}` | Remove um registro obsoleto da base de dados. | `204 No Content` |
| **POST** | `/residuos/carregar` | Força a releitura e carga do arquivo CSV para a base H2. | `200 OK` |

---

## 🚀 Como Executar o Projeto

O sistema é dividido em duas partes independentes. Siga os passos abaixo para rodar a aplicação localmente.

### 1. Inicializando o Backend (Spring Boot)

1. Certifique-se de ter o **Java 21** ou superior instalado.
2. Abra a pasta raiz do backend (`ecorecicla`) em sua IDE (IntelliJ, Eclipse, etc).
3. Atualize as dependências do Maven (`pom.xml`).
4. Garanta que o arquivo contendo a base de dados reais está localizado no caminho estrito: `src/main/resources/data/residuos.csv`.
5. Execute a classe principal `EcoreciclaApplication.java`.
6. O servidor iniciará na porta `8080`.
7. *Opcional:* O console do banco H2 pode ser acessado em `http://localhost:8080/h2-console` (JDBC URL: `jdbc:h2:mem:ecorecicla`, User: `SA`, Senha: em branco).

### 2. Inicializando o Frontend (React)

1. Certifique-se de ter o **Node.js** instalado em sua máquina.
2. Abra um terminal e navegue até a pasta do frontend (`ecorecicla-front`).
3. Instale as dependências do projeto executando o comando:
   ```bash
   npm install
