# Manual rápido

1. Execute `sql/schema.sql` no MySQL.
2. Configure as variáveis do README no IntelliJ.
3. Execute `br.com.essenciaviva.Main`.
4. Entre com `admin` e `admin123`.
5. Cadastre primeiro profissionais e procedimentos. Os IDs aparecem nas tabelas.
6. Cadastre um cliente informando CPF com 11 dígitos. Na listagem, o CPF aparece mascarado.
7. Em Agendamentos, informe os IDs do cliente, profissional e procedimentos separados por vírgula. O sistema bloqueia horários sobrepostos e calcula o total.
8. Para demonstrar a sessão, use `SESSION_MINUTES=1`, aguarde o aviso e clique em “Continuar conectado” ou deixe expirar.
