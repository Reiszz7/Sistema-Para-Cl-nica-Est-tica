package br.com.essenciaviva.ui;
import br.com.essenciaviva.config.Database;
import br.com.essenciaviva.security.PasswordUtil;
import br.com.essenciaviva.security.Session;
import javax.swing.*; import java.awt.*; import java.sql.*;

public class LoginFrame extends JFrame {
    private final JTextField login=new JTextField(); private final JPasswordField senha=new JPasswordField(); private final JLabel mensagem=new JLabel(" ");
    public LoginFrame(){ setTitle("Essência Viva - Login"); setSize(390,260); setLocationRelativeTo(null); setDefaultCloseOperation(EXIT_ON_CLOSE); criarUsuarioInicial(); criarDadosIniciais();
        JPanel p=new JPanel(new GridLayout(5,1,8,8)); p.setBorder(BorderFactory.createEmptyBorder(25,35,25,35)); p.add(new JLabel("Usuário")); p.add(login); p.add(new JLabel("Senha")); p.add(senha); JButton entrar=new JButton("Entrar"); p.add(entrar); add(p,BorderLayout.CENTER); mensagem.setForeground(Color.RED); add(mensagem,BorderLayout.SOUTH); entrar.addActionListener(e->entrar()); senha.addActionListener(e->entrar()); }
    private void criarUsuarioInicial(){ try(Connection c=Database.getConnection(); Statement s=c.createStatement(); ResultSet r=s.executeQuery("SELECT COUNT(*) FROM usuario")){ if(r.next()&&r.getInt(1)==0) try(PreparedStatement p=c.prepareStatement("INSERT INTO usuario(nome,login,senha_hash,perfil) VALUES('Administrador','admin',?,'administrador')")){p.setString(1,PasswordUtil.hash("admin123"));p.executeUpdate();} }catch(SQLException ignored){} }
    private void criarDadosIniciais(){try(Connection c=Database.getConnection()){try(Statement s=c.createStatement();ResultSet r=s.executeQuery("SELECT COUNT(*) FROM profissional")){r.next();if(r.getInt(1)==0)s.executeUpdate("INSERT INTO profissional(nome,registro,especialidade) VALUES('Mariana Souza','EST-001','Estética facial')");}try(Statement s=c.createStatement();ResultSet r=s.executeQuery("SELECT COUNT(*) FROM procedimento")){r.next();if(r.getInt(1)==0)s.executeUpdate("INSERT INTO procedimento(nome,descricao,duracao_minutos,valor) VALUES('Limpeza de pele','Higienização e hidratação',60,120.00),('Drenagem linfática','Massagem corporal',50,100.00)");}}catch(SQLException ignored){}}
    private void entrar(){ try { Session s=Session.login(login.getText().trim(),new String(senha.getPassword())); if(s==null){mensagem.setText("Usuário ou senha inválidos."); return;} dispose(); new MainFrame(s).setVisible(true); } catch(SQLException e){ mensagem.setText("Não foi possível conectar ao banco."); } }
}
