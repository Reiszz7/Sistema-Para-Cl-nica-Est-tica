# Segurança implementada

As senhas são armazenadas com PBKDF2-HMAC-SHA256, 120.000 iterações e salt aleatório. O CPF, nascimento, telefone, e-mail e endereço são cifrados individualmente com AES-256-GCM; cada valor recebe um nonce de 12 bytes novo. O CPF também recebe HMAC-SHA256 sobre os 11 dígitos normalizados para permitir busca e unicidade sem abrir a tabela inteira.

`APP_KEY` e `HMAC_KEY` devem ser variáveis de ambiente. O código possui apenas um valor de desenvolvimento para permitir o primeiro teste; em uma entrega real, configure chaves próprias antes de usar dados reais. O arquivo local de configurações está no `.gitignore`.

A sessão salva somente um hash do token no banco, atualiza a última atividade no servidor e expira pelo número de minutos definido em `SESSION_MINUTES`. O aplicativo exibe aviso nos 60 segundos finais e o botão Sair invalida todas as sessões ativas do usuário.

Como este é um programa desktop para demonstração local, não existe tráfego de navegador. Se o sistema for transformado em serviço de rede, deve usar TLS/HTTPS.
