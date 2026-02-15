# 🎯 Simulador de Riscos

> **Plataforma moderna e intuitiva para avaliação e simulação de riscos corporativos**

![Java](https://img.shields.io/badge/Java-17-ED8B00?style=flat-square&logo=java)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.0.2-6DB33F?style=flat-square&logo=spring-boot)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-18-336791?style=flat-square&logo=postgresql)
![License](https://img.shields.io/badge/License-MIT-green?style=flat-square)

---

## ✨ Destaques

- 🎨 **Design Moderno** - Interface preto e azul ciano com efeitos sofisticados
- 🔐 **Autenticação Segura** - Sistema completo de login e cadastro
- 📊 **Avaliações Dinâmicas** - Crie e responda avaliações de risco customizadas
- 📈 **Simulações** - Simule cenários e analise o impacto financeiro
- 💾 **Banco de Dados Robusto** - PostgreSQL com JPA/Hibernate
- 📱 **Responsivo** - Funciona perfeitamente em qualquer dispositivo
- ⚡ **Performance** - Carregamento rápido e transições suaves

---

## 🚀 Quick Start

### Pré-requisitos

- **Java 17+**
- **PostgreSQL 18+**
- **Maven 3.8+**

### Instalação

1. **Clone o repositório**
```bash
git clone https://github.com/DevViniciusAlves/SimuladorDeRiscos.git
cd SimuladorDeRiscos
```

2. **Configure o banco de dados**
```bash
createdb simuladorderiscos
```

3. **Configure application.properties**
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/simuladorderiscos
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
```

4. **Inicie a aplicação**
```bash
cd SimuladorDeRiscos
mvnw spring-boot:run
```

5. **Acesse no navegador**
```
http://localhost:8080
```

---

## 📋 Funcionalidades Principais

### 👤 Autenticação
- ✅ Cadastro com validação de email e CPF
- ✅ Login seguro com email e senha
- ✅ Sessão persistida no localStorage
- ✅ Logout seguro

### 📝 Avaliações
- ✅ Criar novas avaliações de risco
- ✅ Responder perguntas dinâmicas com opções
- ✅ Visualizar histórico de avaliações
- ✅ Calcular nível de risco automaticamente
- ✅ Finalizar avaliações com insights

### 🎲 Simulações
- ✅ Criar simulações de cenários
- ✅ Definir impacto financeiro e operacional
- ✅ Associar a tipos de risco específicos
- ✅ Visualizar e comparar resultados

### 📊 Dashboard
- ✅ Visão geral de todas as avaliações
- ✅ Tipos de risco com ranges percentuais
- ✅ Interface intuitiva com tabs
- ✅ Saudação personalizada com nome do usuário

---

## 🏗️ Arquitetura

### Backend (Spring Boot)
```
src/main/java/com/ploydev/SimuladorDeRiscos/
├── SimuladorDeRiscosApplication.java
├── config/
├── controller/
├── service/
├── repository/
├── entity/
└── dto/
```

### Frontend (Vanilla JavaScript)
```
src/main/resources/
├── static/
│   ├── index.html
│   ├── css/styles.css
│   └── js/app.js
└── templates/index.html
```

---

## 🛠️ Stack Tecnológico

| Camada | Tecnologia | Versão |
|--------|-----------|--------|
| Backend | Spring Boot | 4.0.2 |
| Linguagem | Java | 17 |
| Banco de Dados | PostgreSQL | 18 |
| ORM | Hibernate/JPA | 7.2.1 |
| Build | Maven | 3.8+ |
| Frontend | HTML5/CSS3/JS | ES6+ |

---

## 🎨 Design System

### Cores
- **Preto Puro**: `#000000` - Background
- **Azul Escuro**: `#001a33` - Secundário
- **Cyan Vibrante**: `#00d9ff` - Destaques
- **Branco**: `#ffffff` - Textos

### Componentes
- Cards com gradiente e bordas cyan
- Botões com efeito glow
- Inputs com focus effect
- Modais com transições suaves

---

## 📝 Validações

- ✅ Senha mínima de 6 caracteres
- ✅ CPF válido (11 dígitos)
- ✅ Email único
- ✅ Mensagens de erro descritivas

---

## 📊 API Endpoints

### Autenticação
```
POST   /api/usuarios/cadastro
POST   /api/usuarios/login
GET    /api/usuarios/{id}
```

### Avaliações
```
GET    /api/avaliacoes/usuario/{id}
POST   /api/avaliacoes/{usuarioId}
GET    /api/avaliacoes/{id}
POST   /api/avaliacoes/{id}/finalizar
GET    /api/avaliacoes/{id}/respostas
```

### Perguntas
```
GET    /api/perguntas/ativas
GET    /api/perguntas
GET    /api/perguntas/{id}
POST   /api/perguntas
PUT    /api/perguntas/{id}/desativar
PUT    /api/perguntas/{id}/ativar
```

### Opções de Resposta
```
GET    /api/opcoes/pergunta/{id}
POST   /api/opcoes/{perguntaId}
GET    /api/opcoes/{id}
PUT    /api/opcoes/{id}/desativar
PUT    /api/opcoes/{id}/ativar
```

### Respostas
```
POST   /api/respostas
GET    /api/respostas/{id}
GET    /api/respostas/avaliacao/{avaliacaoId}
PUT    /api/respostas/{id}
PUT    /api/respostas/{id}/desativar
PUT    /api/respostas/{id}/ativar
```

### Tipos de Risco
```
GET    /api/tipo-risco
POST   /api/tipo-risco
GET    /api/tipo-risco/{id}
PUT    /api/tipo-risco/{id}/desativar
PUT    /api/tipo-risco/{id}/ativar
```

### Simulações
```
POST   /api/simulacoes
GET    /api/simulacoes/avaliacao/{id}
GET    /api/simulacoes/{id}
```

---

## 👨‍💻 Desenvolvedor

**Vinicius Alves**
- 🐙 GitHub: [DevViniciusAlves](https://github.com/DevViniciusAlves/)
- 💼 LinkedIn: [viniciushf](https://www.linkedin.com/in/viniciushf/)

---

<div align="center">

**⭐ Se esse projeto foi útil, deixe uma star!**

Feito com ❤️

</div>

