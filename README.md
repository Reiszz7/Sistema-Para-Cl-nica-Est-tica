# Sistema Essência Viva

Aplicação desktop em Java Swing para controlar clientes, profissionais, procedimentos e agendamentos de uma clínica estética. O projeto foi organizado para ser aberto no IntelliJ IDEA.

## Como executar

1. Instale Java 21, IntelliJ IDEA, Maven e MySQL.
2. No MySQL, execute o arquivo `sql/schema.sql`. Ele cria o banco e as tabelas.
3. Copie `src/main/resources/config.properties.example` para `config.properties` (ou configure as variáveis de ambiente `DB_HOST`, `DB_PORT`, `DB_NAME`, `DB_USER`, `DB_PASSWORD`, `APP_KEY`, `HMAC_KEY` e `SESSION_MINUTES`). Esse arquivo local não deve ser enviado ao Git.
4. Abra a pasta no IntelliJ como projeto Maven, espere o Maven baixar `mysql-connector-j` e execute `br.com.essenciaviva.Main`.
5. No primeiro acesso, use `admin` e `admin123`. Troque a senha pelo cadastro de usuários quando essa tela for ampliada.

## Segurança implementada

Senhas usam PBKDF2-HMAC-SHA256 com salt. Os dados pessoais do cliente usam AES-256-GCM com nonce novo em cada valor. O CPF possui HMAC-SHA256 separado para pesquisa e unicidade. A chave é lida por variável de ambiente e não fica no código. A sessão é registrada no banco, atualizada pelo servidor e expira após `SESSION_MINUTES` (15 por padrão); os últimos 60 segundos devem ser demonstrados reduzindo esse valor.

No desenvolvimento local a aplicação é desktop e não usa navegador/HTTPS. Em um cenário de publicação em rede, a comunicação deve ser protegida com TLS.

## Banco de dados

O usuário MySQL padrão é `root` sem senha, mas todos os valores podem ser alterados pelas configurações locais. A aplicação gera o usuário inicial e os dados de demonstração; informações sensíveis não são inseridas em texto claro pelo SQL.

## Entregáveis e explicação

`sql/schema.sql` contém a estrutura relacional. `docs/DER.md` e `docs/CASOS_DE_USO.md` contêm diagramas Mermaid, que podem ser exportados para PNG pelo Mermaid Live ou draw.io. A camada `config` abre conexões, `security` protege senhas/dados e `ui` contém as telas Swing.

Também existem as versões visuais `docs/DER.svg` e `docs/CASOS_DE_USO.svg`, que podem ser abertas diretamente no navegador ou importadas no draw.io. Para cadastrar um agendamento, informe os IDs dos procedimentos separados por vírgula; o sistema copia os valores atuais para a tabela associativa e grava o total.

## Commit e push

No terminal do IntelliJ:

```bash
git init
git add .
git commit -m "Implementa sistema da clinica estetica"
git branch -M main
git remote add origin https://github.com/SEU_USUARIO/Sistema-Para-Cl-nica-Est-tica.git
git push -u origin main
```

Se o remoto já estiver configurado, não repita `git remote add origin`; use somente `git add .`, `git commit` e `git push`.
