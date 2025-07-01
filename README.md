# Sistema de Votação para Cooperativas

Sistema web para gerenciar sessões de votação em cooperativas, desenvolvido com Spring Boot (backend) e React (frontend).

## 🎯 Funcionalidades

### Principais
- ✅ Cadastro de novas pautas
- ✅ Abertura de sessões de votação com tempo configurável
- ✅ Sistema de votação (Sim/Não) por CPF
- ✅ Contabilização automática de votos
- ✅ Visualização de resultados em tempo real
- ✅ Prevenção de voto duplicado por associado/pauta

### Funcionalidades Bônus Implementadas
- ✅ **Tarefa Bônus 1**: Integração com validação de CPF (simulada)
- ✅ **Tarefa Bônus 2**: Otimizado para performance com índices e cache
- ✅ **Tarefa Bônus 3**: API versionada (v1)

## 🏗️ Arquitetura

```
├── backend/                 # Spring Boot API
│   ├── src/main/java/
│   │   └── com/cooperativa/votacao/
│   │       ├── model/       # Entidades JPA
│   │       ├── repository/  # Repositórios Spring Data
│   │       ├── service/     # Lógica de negócio
│   │       ├── controller/  # Controllers REST
│   │       ├── dto/         # Data Transfer Objects
│   │       └── config/      # Configurações
│   └── src/main/resources/
│       ├── application.properties
│       └── data.sql         # Dados iniciais
│
├── frontend/                # React TypeScript
│   ├── src/
│   │   ├── components/      # Componentes React
│   │   ├── services/        # Cliente da API
│   │   └── types/           # Tipos TypeScript
│   └── public/
│
└── README.md
```

## 🚀 Como Executar

### Pré-requisitos
- Java 17 ou superior
- Node.js 16 ou superior
- Maven 3.6+
- Git

### 1. Clone o Repositório
```bash
git clone <url-do-repositorio>
cd TESTEDB
```

### 2. Backend (Spring Boot)
```bash
cd backend
mvn clean install
mvn spring-boot:run
```

O backend estará disponível em: `http://localhost:8080`

**Endpoints principais:**
- API Docs: `http://localhost:8080/swagger-ui.html`
- H2 Console: `http://localhost:8080/h2-console`
  - URL: `jdbc:h2:mem:votacao_db`
  - User: `sa`
  - Password: (vazio)

### 3. Frontend (React)
```bash
cd frontend
npm install
npm start
```

O frontend estará disponível em: `http://localhost:3000`

## 📡 API Endpoints

### Pautas
- `GET /api/v1/pautas` - Listar todas as pautas
- `POST /api/v1/pautas` - Criar nova pauta
- `GET /api/v1/pautas/{id}` - Buscar pauta por ID

### Sessões de Votação
- `POST /api/v1/sessoes/pautas/{pautaId}` - Abrir sessão de votação
- `GET /api/v1/sessoes/pautas/{pautaId}` - Listar sessões da pauta
- `GET /api/v1/sessoes/{id}` - Buscar sessão por ID

### Votação
- `POST /api/v1/votacao/sessoes/{sessaoId}/votos` - Registrar voto
- `GET /api/v1/votacao/sessoes/{sessaoId}/resultado` - Obter resultado

## 💡 Como Usar

### 1. Criar uma Nova Pauta
1. Acesse `http://localhost:3000`
2. Clique em "Nova Pauta"
3. Preencha título e descrição
4. Clique em "Criar Pauta"

### 2. Abrir Sessão de Votação
1. Na lista de pautas, clique em "Abrir Sessão"
2. Defina a duração em minutos (padrão: 1 minuto)
3. Clique em "Abrir Sessão"

### 3. Votar
1. Clique em "Ver Detalhes" na pauta desejada
2. Informe seu CPF
3. Clique em "Votar SIM" ou "Votar NÃO"

### 4. Ver Resultados
- Os resultados são atualizados em tempo real
- Sessões fecham automaticamente após o tempo configurado

## 🗄️ Modelo de Dados

### Pauta
- `id`: Identificador único
- `titulo`: Título da pauta
- `descricao`: Descrição detalhada
- `dataCriacao`: Data/hora de criação

### SessaoVotacao
- `id`: Identificador único
- `pautaId`: Referência à pauta
- `dataAbertura`: Data/hora de abertura
- `dataFechamento`: Data/hora de fechamento
- `duracaoMinutos`: Duração em minutos
- `status`: ABERTA ou FECHADA

### Voto
- `id`: Identificador único
- `sessaoVotacaoId`: Referência à sessão
- `cpfAssociado`: CPF do associado (único por sessão)
- `voto`: SIM ou NAO
- `dataVoto`: Data/hora do voto

## 🔒 Validações e Regras de Negócio

1. **CPF**: Validação matemática + simulação de API externa
2. **Voto Único**: Um associado pode votar apenas uma vez por sessão
3. **Sessão Ativa**: Votos só são aceitos em sessões abertas
4. **Tempo Limite**: Sessões fecham automaticamente
5. **Concorrência**: Sistema preparado para múltiplos usuários

## 🧪 Testes

### Backend
```bash
cd backend
mvn test
```

### Frontend
```bash
cd frontend
npm test
```

## 🎨 Tecnologias Utilizadas

### Backend
- **Spring Boot 3.2.0**: Framework principal
- **Spring Data JPA**: Persistência de dados
- **H2 Database**: Banco em memória para desenvolvimento
- **Spring Validation**: Validação de entrada
- **Swagger/OpenAPI**: Documentação da API
- **Maven**: Gerenciamento de dependências

### Frontend
- **React 18**: Library para UI
- **TypeScript**: Tipagem estática
- **React Router**: Navegação
- **Bootstrap + React Bootstrap**: Estilização
- **Axios**: Cliente HTTP

## 📈 Performance e Escalabilidade

- **Índices de banco**: Otimização de consultas
- **Scheduler**: Fechamento automático de sessões
- **Cache**: Resultados calculados sob demanda
- **Paginação**: Preparado para grandes volumes
- **CORS**: Configurado para múltiplos domínios

## 🔄 Versionamento da API

A API utiliza versionamento semântico através da URL:
- Versão atual: `/api/v1/`
- Futuras versões: `/api/v2/`, `/api/v3/`, etc.

**Estratégia de Versionamento:**
1. **URL Path Versioning**: Mais explícito e cacheable
2. **Backward Compatibility**: Versões antigas mantidas por pelo menos 6 meses
3. **Deprecation Headers**: Avisos sobre descontinuação
4. **Semantic Versioning**: Major.Minor.Patch para controle de mudanças

## 🐛 Tratamento de Erros

### Backend
- **400 Bad Request**: Dados inválidos
- **404 Not Found**: Recurso não encontrado
- **409 Conflict**: Conflito de estado (ex: voto duplicado)
- **500 Internal Server Error**: Erro interno

### Frontend
- Mensagens de erro user-friendly
- Validação de formulários
- Loading states
- Retry automático para falhas de rede

## 📝 Logs

O sistema gera logs estruturados para:
- Operações de votação
- Abertura/fechamento de sessões
- Erros e exceções
- Performance de queries

## 🚀 Deploy e Produção

### Considerações para Produção

1. **Banco de Dados**: Migrar para PostgreSQL/MySQL
2. **Segurança**: Implementar autenticação JWT
3. **Cache**: Redis para sessões e resultados
4. **Monitoramento**: Actuator + Prometheus
5. **Load Balancer**: Para múltiplas instâncias
6. **HTTPS**: Certificados SSL/TLS

### Variáveis de Ambiente
```properties
# Banco de Dados
DATABASE_URL=jdbc:postgresql://localhost:5432/votacao
DATABASE_USERNAME=username
DATABASE_PASSWORD=password

# Redis Cache
REDIS_URL=redis://localhost:6379

# Frontend
REACT_APP_API_URL=https://api.cooperativa.com.br/api/v1
```

## 🤝 Contribuição

1. Fork o projeto
2. Crie uma branch para sua feature (`git checkout -b feature/AmazingFeature`)
3. Commit suas mudanças (`git commit -m 'Add some AmazingFeature'`)
4. Push para a branch (`git push origin feature/AmazingFeature`)
5. Abra um Pull Request

## 📄 Licença

Este projeto está sob a licença MIT. Veja o arquivo `LICENSE` para mais detalhes.

## ✨ Autor

Desenvolvido como parte do desafio técnico para sistema de votação em cooperativas.

---

💡 **Dica**: Para testar rapidamente, use CPFs válidos como:
- `11144477735`
- `12345678909` 
- `98765432100`

🎯 **Nota sobre Performance**: O sistema foi otimizado para suportar milhares de votos simultâneos através de índices de banco, queries otimizadas e arquitetura stateless.
# votacao
# votacao
