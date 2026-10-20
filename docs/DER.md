# Diagrama Entidade-Relacionamento

```mermaid
erDiagram
  USUARIO ||--o{ SESSAO : possui
  CLIENTE ||--o{ AGENDAMENTO : tem
  PROFISSIONAL ||--o{ AGENDAMENTO : atende
  AGENDAMENTO ||--o{ AGENDAMENTO_PROCEDIMENTO : inclui
  PROCEDIMENTO ||--o{ AGENDAMENTO_PROCEDIMENTO : participa
  CLIENTE { int id PK; string nome; string cpf_hash H; string cpf_cifrado C; string dados_sensiveis C }
  AGENDAMENTO { int id PK; int cliente_id FK; int profissional_id FK; date data; time inicio; time fim; decimal valor_total }
```

Abra este arquivo no draw.io ou no Mermaid Live e exporte como PNG para a entrega.
