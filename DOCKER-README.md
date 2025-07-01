# 🚀 Sistema de Votação - Docker (SUPER FÁCIL!)

## ⚡ INÍCIO ## 🌐 ENDPOINTS

### Frontend
- **Interface:** http://localhost:3000

### Backend (Java Spring Boot)
- **API:** http://localhost:8080

### Opção 1: Desenvolvimento (Recomendado)
```bash
# Duplo clique no arquivo:
start-dev.bat

# Ou execute no terminal:
docker-compose -f docker-compose.dev.yml up --build
```
**✅ Acesse:** http://localhost:3000

### Opção 2: Sistema Completo (Java + React)
```bash
# Duplo clique no arquivo:
start-full.bat

# Ou execute no terminal:
docker-compose up --build
```
**✅ Acesse:** http://localhost:3000

### Para Parar:
```bash
# Duplo clique no arquivo:
stop-dev.bat
```

## 🎯 SISTEMA INTEGRADO

### � INICIAR.bat (Sistema Completo)
- ✅ Frontend React (http://localhost:3000)
- ✅ Backend Java Spring Boot (http://localhost:8080)
- ✅ Banco H2 em memória
- ✅ APIs REST completas
- ✅ Sistema totalmente integrado

## 📋 PRÉ-REQUISITOS

1. **Docker Desktop** instalado
   - Download: https://www.docker.com/products/docker-desktop
   - Certifique-se que está rodando

2. **Nada mais!** 🎉

## � COMANDOS ÚTEIS

```bash
# Ver logs em tempo real
docker-compose logs -f

# Parar tudo
docker-compose down

# Limpar cache
docker system prune -a

# Status dos containers
docker ps
```

## � ENDPOINTS

### Frontend
- **Interface:** http://localhost:3000

### Desenvolvimento (Java)
- **API:** http://localhost:8080
- **Pautas:** http://localhost:8080/api/v1/pautas
- **Sessões:** http://localhost:8080/api/v1/sessoes
- **Swagger:** http://localhost:8080/swagger-ui.html
- **H2 Console:** http://localhost:8080/h2-console

## 🛠️ TROUBLESHOOTING

### Erro de porta ocupada:
```bash
stop-dev.bat
# Aguarde 10 segundos
start-dev.bat
```

### Container não inicia:
```bash
docker system prune -a
start-dev.bat
```

### Mudanças não aparecem:
```bash
docker-compose down
docker-compose up --build
```

## ✅ VERIFICAÇÃO RÁPIDA

1. Execute `start-dev.bat`
2. Aguarde aparecer "Compiled successfully!"
3. Abra http://localhost:3000
4. Deve aparecer a lista de pautas
5. Teste criar uma pauta nova

**🎉 Se funcionou, está tudo certo!**
