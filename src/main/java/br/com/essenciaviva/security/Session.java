package br.com.essenciaviva.security;
import br.com.essenciaviva.config.Config;
import br.com.essenciaviva.config.Database;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.UUID;

public final class Session {
    private final int userId; private LocalDateTime lastActivity;
    private Session(int userId) { this.userId=userId; this.lastActivity=LocalDateTime.now(); }
    public static Session login(String login, String password) throws SQLException {
        try(Connection c=Database.getConnection(); PreparedStatement p=c.prepareStatement("SELECT id,senha_hash,status FROM usuario WHERE login=?")) {
            p.setString(1,login); try(ResultSet r=p.executeQuery()) { if(!r.next() || !r.getString("status").equals("ativo") || !PasswordUtil.matches(password,r.getString("senha_hash"))) return null;
                Session s=new Session(r.getInt("id")); String token=UUID.randomUUID().toString();
                try(PreparedStatement q=c.prepareStatement("INSERT INTO sessao(usuario_id,token_hash,criado_em,ultima_atividade) VALUES(?,?,NOW(),NOW())")){q.setInt(1,s.userId);q.setString(2,Crypto.hmac(token));q.executeUpdate();} return s; }
        }
    }
    public boolean expired() { return lastActivity.plusMinutes(Config.sessionMinutes()).isBefore(LocalDateTime.now()); }
    public long secondsToExpire() { return java.time.Duration.between(LocalDateTime.now(),lastActivity.plusMinutes(Config.sessionMinutes())).getSeconds(); }
    public void touch() throws SQLException { lastActivity=LocalDateTime.now(); try(Connection c=Database.getConnection(); PreparedStatement p=c.prepareStatement("UPDATE sessao SET ultima_atividade=NOW() WHERE usuario_id=? AND ativa=TRUE")){p.setInt(1,userId);p.executeUpdate();} }
    public void close() { try(Connection c=Database.getConnection(); PreparedStatement p=c.prepareStatement("UPDATE sessao SET ativa=FALSE WHERE usuario_id=? AND ativa=TRUE")){p.setInt(1,userId);p.executeUpdate();}catch(SQLException ignored){} }
}
