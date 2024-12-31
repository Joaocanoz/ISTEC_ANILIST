# ISTEC_Animelist

Projeto realizado por João Ozkaplan e Guilherme Pedrosa do curso DDM diurno do 1º ano. Este projeto consiste numa API que faz a gestão de listas de animes e mangas, incluindo a possibilidade de adicionar reviews aos mesmos.

## Requisitos

Para executar este projeto, você precisará dos seguintes requisitos:

- **OpenJDK 23.0.1**: Certifique-se de que você tem a versão 23.0.1 do OpenJDK instalada.
- **IntelliJ IDEA**: Utilize a IDE IntelliJ IDEA para desenvolver e executar o projeto.

## Inicialização do Projeto

### Passo 1: Clonar o Repositório

Primeiro, você deve clonar o repositório do projeto para o seu ambiente de desenvolvimento local. Utilize o seguinte comando no seu terminal:

```bash
git clone <URL_DO_REPOSITORIO>
```
### Passo 2: Abrir o Projeto no IntelliJ IDEA

1. Abra a IDE IntelliJ IDEA.
2. Clique em `File` > `Open...`.
3. Navegue até a pasta onde você clonou o repositório e selecione-a.
4. Aguarde enquanto a IDE carrega o projeto e baixa as dependências necessárias.

### Passo 3: Configurar o Ambiente

Certifique-se de que o SDK do projeto está configurado para a versão correta do OpenJDK:

1. Clique em `File` > `Project Structure`.
2. Em `Project`, configure o `Project SDK` para a versão 23.0.1 do OpenJDK.
3. Clique em `OK` para salvar as configurações.

### Passo 4: Executar o Projeto

1. No painel de projeto, navegue até o arquivo `Application.kt`, geralmente localizado em `src/main/kotlin/<seu_pacote>/`.
2. Clique com o botão direito no arquivo `Application.kt` e selecione `Run 'Application.kt'`.

### Passo 5: Acessar o Swagger UI

Após a execução bem-sucedida do projeto, um caminho será exibido na consola indicando o endereço local onde a aplicação está sendo executada, por exemplo, `http://localhost:8080`.

Para testar a API, siga estas etapas:

1. Abra o seu navegador de internet.
2. Insira o endereço exibido na consola, seguido de `/swagger`. Exemplo: `http://localhost:8080/swagger`.
3. A interface do Swagger UI será carregada, permitindo que você visualize e teste os endpoints da API.

## Exemplos de Uso

### Criar um Novo Anime

- **Endpoint:** `POST /animes`
- **Corpo da Requisição:**

```json
{
  "title": "Novo Anime",
  "description": "Descrição do novo anime",
  "releaseDate": "2024-12-30",
  "episodes": 12,
  "genres": [
    {"id": "1", "name": "Aventura"}
  ],
  "rating": 8.5,
  "reviews": []
}
```

### Editar um Anime Existente

- **Endpoint:** `PUT /animes/{id}`
- **Corpo da Requisição:**

```json
{
  "title": "Anime Editado",
  "description": "Descrição editada do anime",
  "releaseDate": "2024-12-31",
  "episodes": 24,
  "genres": [
    {"id": "1", "name": "Aventura"},
    {"id": "2", "name": "Ação"}
  ],
  "rating": 9.0,
  "reviews": []
}
```

### Apagar um Anime

- **Endpoint:** `DELETE /animes/{id}`
- **Descrição:** Não é necessário um corpo de requisição para deletar um anime. A URL deve conter o ID do anime a ser deletado.

### Criar um Novo Manga

- **Endpoint:** `POST /mangas`
- **Corpo da Requisição:**

```json
{
  "title": "Novo Manga",
  "description": "Descrição do novo manga",
  "releaseDate": "2024-12-30",
  "chapters": 20,
  "genres": [
    {"id": "1", "name": "Fantasia"}
  ],
  "rating": 8.7,
  "reviews": []
}
```

### Editar um Manga Existente

- **Endpoint:** `PUT /mangas/{id}`
- **Corpo da Requisição:**

```json
{
  "title": "Manga Editado",
  "description": "Descrição editada do manga",
  "releaseDate": "2024-12-31",
  "chapters": 30,
  "genres": [
    {"id": "1", "name": "Fantasia"},
    {"id": "2", "name": "Aventura"}
  ],
  "rating": 9.2,
  "reviews": []
}
```
### Apagar um Manga

- **Endpoint:** `DELETE /mangas/{id}`
- **Descrição:** Não é necessário um corpo de requisição para deletar um manga. A URL deve conter o ID do manga a ser deletado.

### Configurações de Segurança no Swagger

Certifique-se de que o Swagger está configurado para usar autenticação JWT. Aqui está um exemplo de configuração no OpenAPI:

```yaml
openapi: "3.0.3"
info:
  title: "Anime and Manga API"
  description: "A REST API for managing anime and manga"
  version: "1.0.0"
servers:
  - url: http://localhost:8080

security:
  - bearerAuth: []

components:
  securitySchemes:
    bearerAuth:
      type: http
      scheme: bearer
      bearerFormat: JWT

  schemas:
    Anime:
      type: object
      properties:
        id:
          type: string
        title:
          type: string
        description:
          type: string
        releaseDate:
          type: string
        episodes:
          type: integer
        genres:
          type: array
          items:
            $ref: '#/components/schemas/Genre'
        rating:
          type: number
          format: float
        reviews:
          type: array
          items:
            $ref: '#/components/schemas/AnimeReview'
    AnimeRequest:
      type: object
      properties:
        title:
          type: string
        description:
          type: string
        releaseDate:
          type: string
        episodes:
          type: integer
        genres:
          type: array
          items:
            $ref: '#/components/schemas/Genre'
        rating:
          type: number
          format: float
        reviews:
          type: array
          items:
            $ref: '#/components/schemas/AnimeReview'
    Manga:
      type: object
      properties:
        id:
          type: string
        title:
          type: string
        description:
          type: string
        releaseDate:
          type: string
        chapters:
          type: integer
        genres:
          type: array
          items:
            $ref: '#/components/schemas/Genre'
        rating:
          type: number
          format: float
        reviews:
          type: array
          items:
            $ref: '#/components/schemas/MangaReview'
    MangaRequest:
      type: object
      properties:
        title:
          type: string
        description:
          type: string
        releaseDate:
          type: string
        chapters:
          type: integer
        genres:
          type: array
          items:
            $ref: '#/components/schemas/Genre'
        rating:
          type: number
          format: float
        reviews:
          type: array
          items:
            $ref: '#/components/schemas/MangaReview'
    Genre:
      type: object
      properties:
        id:
          type: string
        name:
          type: string
    AnimeReview:
      type: object
      properties:
        id:
          type: string
        animeId:
          type: string
        userEmail:
          type: string
        content:
          type: string
        rating:
          type: number
          format: float
        date:
          type: string
    MangaReview:
      type: object
      properties:
        id:
          type: string
        mangaId:
          type: string
        userEmail:
          type: string
        content:
          type: string
        rating:
          type: number
          format: float
        date:
      required:
        - id
        - mangaId
        - userEmail
        - content
        - rating
        - date
    LoginCredentials:
      type: object
      properties:
        email:
          type: string
        password:
          type: string
      required:
        - email
        - password
    RegisterCredentials:
      type: object
      properties:
        email:
          type: string
        password:
          type: string
      required:
        - email
        - password
paths:
  /animes:
    get:
      summary: Get all animes
      description: Retrieves a list of all animes
      responses:
        '200':
          description: List of animes
          content:
            application/json:
              schema:
                type: array
                items:
                  $ref: '#/components/schemas/Anime'
    post:
      summary: Create a new anime
      description: Creates a new anime
      security:
        - bearerAuth: []
      requestBody:
        required: true
        content:
          application/json:
            schema:
              $ref: '#/components/schemas/AnimeRequest'
      responses:
        '201':
          description: Anime created successfully
          content:
            application/json:
              schema:
                $ref: '#/components/schemas/Anime'
        '401':
          description: Unauthorized
  /animes/{id}:
    get:
      summary: Get anime by ID
      parameters:
        - name: id
          in: path
          required: true
          schema:
            type: string
      responses:
        '200':
          description: Anime found
          content:
            application/json:
              schema:
                $ref: '#/components/schemas/Anime'
        '404':
          description: Anime not found
    put:
      summary: Update an anime
      security:
        - bearerAuth: []
      parameters:
        - name: id
          in: path
          required: true
          schema:
            type: string
      requestBody:
        required: true
        content:
          application/json:
            schema:
              $ref: '#/components/schemas/AnimeRequest'
      responses:
        '200':
          description: Anime updated successfully
          content:
            application/json:
              schema:
                $ref: '#/components/schemas/Anime'
        '404':
          description: Anime not found
        '401':
          description: Unauthorized
    delete:
      summary: Delete an anime
      security:
        - bearerAuth: []
      parameters:
        - name: id
          in: path
          required: true
          schema:
            type: string
      responses:
        '200':
          description: Anime deleted successfully
        '404':
          description: Anime not found
        '401':
          description: Unauthorized

  /mangas:
    get:
      summary: Get all mangas
      description: Retrieves a list of all mangas
      responses:
        '200':
          description: List of mangas
          content:
            application/json:
              schema:
                type: array
                items:
                  $ref: '#/components/schemas/Manga'
    post:
      summary: Create a new manga
      description: Creates a new manga
      security:
        - bearerAuth: []
      requestBody:
        required: true
        content:
          application/json:
            schema:
              $ref: '#/components/schemas/MangaRequest'
      responses:
        '201':
          description: Manga created successfully
          content:
            application/json:
              schema:
                $ref: '#/components/schemas/Manga'
        '401':
          description: Unauthorized
  /mangas/{id}:
    get:
      summary: Get manga by ID
      parameters:
        - name: id
          in: path
          required: true
          schema:
            type: string
      responses:
        '200':
          description: Manga found
          content:
            application/json:
              schema:
                $ref: '#/components/schemas/Manga'
        '404':
          description: Manga not found
    put:
      summary: Update a manga
      security:
        - bearerAuth: []
      parameters:
        - name: id
          in: path
          required: true
          schema:
            type: string
      requestBody:
        required: true
        content:
          application/json:
            schema:
              $ref: '#/components/schemas/MangaRequest'
      responses:
        '200':
          description: Manga updated successfully
          content:
            application/json:
              schema:
                $ref: '#/components/schemas/Manga'
        '404':
          description: Manga not found
        '401':
          description: Unauthorized
    delete:
      summary: Delete a manga
      security:
        - bearerAuth: []
      parameters:
        - name: id
          in: path
          required: true
          schema:
            type: string
      responses:
        '200':
          description: Manga deleted successfully
        '404':
          description: Manga not found
        '401':
          description: Unauthorized

  /login:
    post:
      summary: Login
      requestBody:
        required: true
        content:
          application/json:
            schema:
              $ref: '#/components/schemas/LoginCredentials'
      responses:
        '200':
          description: Login successful
          content:
            application/json:
              schema:
                $ref: '#/components/schemas/LoginCredentials'
        '401':
          description: Unauthorized

  /register:
    post:
      summary: Register
      requestBody:
        required: true
        content:
          application/json:
            schema:
              $ref: '#/components/schemas/RegisterCredentials'
      responses:
        '201':
          description: Registration successful
          content:
            application/json:
              schema:
                $ref: '#/components/schemas/RegisterCredentials'
        '400':
          description: Bad request

  /protected:
    get:
      summary: Protected route
      security:
        - bearerAuth: []
      responses:
        '200':
          description: Protected content
        '401':
          description: Unauthorized
```
### Testando com Swagger UI

#### Obtenha o Token JWT:

Use o endpoint `/login` para autenticar e obter um token JWT.

- **Exemplo de corpo da requisição para login:**

```json
{
  "email": "usuario@example.com",
  "password": "senha123"
}
```
### Adicione o Token JWT no Swagger UI:

1. Clique no botão "Authorize" no topo da página do Swagger UI.
2. Insira o token JWT no formato `Bearer <token>` e clique em "Authorize".

### Teste os Endpoints Protegidos:

Agora você pode testar os endpoints protegidos como `/animes`, `/mangas`, etc., usando o token JWT.

Seguindo esses passos, você terá a API do ISTEC_Animelist configurada e pronta para uso. Utilize o Swagger UI para testar e interagir com a API de maneira fácil e intuitiva. Se precisar de ajuda adicional, consulte a documentação do Swagger ou entre em contato com os desenvolvedores do projeto.











