# Gestão de Aeronaves

Sistema completo de gerenciamento de aeronaves com Spring Boot (backend) e React (frontend).

## Tecnologias

**Backend:** Java 17, Spring Boot, PostgreSQL, Maven  
**Frontend:** React 18, TypeScript, Vite, Axios

## Pré-requisitos

- Java 17+
- PostgreSQL 12+
- Node.js 16+

## Como Executar

### 1. Banco de Dados

**Opção A - PostgreSQL Local:**
```bash
# Crie o banco de dados
psql -U postgres -c "CREATE DATABASE gestao_aeronaves;"

# Configure a senha em: backend/src/main/resources/application.properties
spring.datasource.password=SUA_SENHA
```

**Opção B - Docker:**
```bash
cd backend
docker compose up -d
```

### 2. Backend

Abra um terminal no diretório `backend`:
```bash
cd backend
.\mvnw.cmd spring-boot:run
```

Backend disponível em: **http://localhost:8080**

### 3. Frontend

No diretório `frontend`:

**Se estiver no PowerShell** (e `npm` não for reconhecido), use o caminho completo:
```powershell
cd frontend
& "C:\Program Files\nodejs\npm.cmd" install
& "C:\Program Files\nodejs\npm.cmd" run dev
```

**Se estiver no Command Prompt (CMD):**
```bash
cd frontend
npm install
npm run dev
```

Frontend disponível em: **http://localhost:5173**

## API - Principais Endpoints

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| GET | `/aeronaves` | Lista todas as aeronaves |
| POST | `/aeronaves` | Cria nova aeronave |
| PUT | `/aeronaves/{id}` | Atualiza aeronave |
| DELETE | `/aeronaves/{id}` | Remove aeronave |
| GET | `/aeronaves/find?termo={termo}` | Busca por nome ou marca |
| GET | `/aeronaves/statistics/decadas` | Estatísticas por década |
| GET | `/aeronaves/statistics/marcas` | Estatísticas por marca |
| GET | `/aeronaves/manufacturers` | Lista fabricantes válidos |

## Exemplo de Requisição

```bash
POST http://localhost:8080/aeronaves
Content-Type: application/json

{
  "nome": "E2-190",
  "marca": "Embraer",
  "ano": 2014,
  "descricao": "Jato regional",
  "vendido": false
}
```

## Regras de Validação

- **nome**: 2-100 caracteres (obrigatório)
- **marca**: Fabricante válido (obrigatório)
- **ano**: 1900-2100 (obrigatório)
- **vendido**: true/false (padrão: false)

**Fabricantes válidos:** Airbus, Antonov, ATR, Beechcraft, Boeing, Bombardier, Cessna, Cirrus, Comac, Dassault, Embraer, Gulfstream, Honda, Lockheed Martin, Mitsubishi, Northrop Grumman, Piper, Sukhoi, Tupolev

## Estrutura do Projeto

```
gestao-aeronaves/
├── backend/
│   ├── src/main/java/com/sonda/gestaoaeronaves/
│   │   ├── config/         # Configurações (CORS, JPA)
│   │   ├── controller/     # REST Controllers
│   │   ├── dto/            # Data Transfer Objects
│   │   ├── exception/      # Tratamento de erros
│   │   ├── model/          # Entidades JPA
│   │   ├── repository/     # Acesso ao banco
│   │   └── service/        # Lógica de negócio
│   ├── src/main/resources/
│   │   ├── application.properties
│   │   └── data.sql        # Dados iniciais
│   └── pom.xml
├── frontend/
│   ├── src/
│   │   ├── components/     # Componentes React
│   │   ├── services/       # Cliente API (Axios)
│   │   ├── types/          # TypeScript types
│   │   └── App.tsx
│   ├── vite.config.ts
│   └── package.json
└── README.md
```

## Solução de Problemas

**"mvn não é reconhecido"**  
→ Use `.\mvnw.cmd` no diretório backend

**"npm não é reconhecido" (PowerShell)**  
→ Use: `& "C:\Program Files\nodejs\npm.cmd" install` e `& "C:\Program Files\nodejs\npm.cmd" run dev`  
→ Ou abra o Command Prompt (CMD) e use `npm install` e `npm run dev`

**"Porta 8080 em uso"**  
```bash
netstat -ano | findstr :8080
taskkill /PID <PID> /F
```

**"Connection refused" ao banco**  
→ Verifique se o PostgreSQL está rodando e as credenciais em `application.properties`

