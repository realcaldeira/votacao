FROM node:18-alpine

WORKDIR /app

# Copiar package.json e package-lock.json
COPY package*.json ./

# Instalar dependências
RUN npm ci

# Copiar código fonte
COPY . .

# Expor a porta 3000
EXPOSE 3000

# Comando para executar em modo desenvolvimento
CMD ["npm", "start"]
