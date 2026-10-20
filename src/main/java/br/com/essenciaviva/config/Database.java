package br.com.essenciaviva.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class Database {
    private Database() { }
    public static Connection getConnection() throws SQLException {
        String host = Config.get("DB_HOST", "localhost");
        String port = Config.get("DB_PORT", "3306");
        String database = Config.get("DB_NAME", "clinica_estetica");
        String user = Config.get("DB_USER", "root");
        String password = Config.get("DB_PASSWORD", "");
        String url = "jdbc:mysql://" + host + ":" + port + "/" + database + "?serverTimezone=America/Sao_Paulo&useSSL=false&allowPublicKeyRetrieval=true";
        return DriverManager.getConnection(url, user, password);
    }
}
