# Diagrama de casos de uso

```mermaid
flowchart LR
  A[Administrador] --> L(Login)
  R[Recepcionista] --> L
  P[Profissional] --> L
  L --> C[Gerenciar clientes]
  L --> F[Gerenciar profissionais]
  L --> T[Gerenciar procedimentos]
  L --> G[Gerenciar agendamentos]
  G -. inclui .-> V[Verificar disponibilidade]
  L --> S[Sair]
  X[Sistema] --> E[Expirar sessão por inatividade]
  E -. estende .-> L
  C -. inclui .-> D[Visualizar dado sensível]
```
