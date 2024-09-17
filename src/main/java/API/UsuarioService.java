package API;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioService {
    private List<Usuario> usuarios;
    private final String USUARIOS_CSV = "usuarios.csv";
    private final File arqUsuarios = new File(USUARIOS_CSV);

    public List<Usuario> getUsuarios() {
        usuarios = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(arqUsuarios));
            String linha = br.readLine();
            while (linha != null) {
                System.out.println("Linha lida: " + linha);
                String[] campos = linha.split(";");
                int id = Integer.parseInt(campos[0]);
                String nickName = campos[1];
                String senha = campos[2];
                Usuario novoUsuario = new Usuario(id, nickName, senha);
                this.usuarios.add(novoUsuario);
                linha = br.readLine();
            }
            br.close();
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return this.usuarios;
    }

    public void criarUsuario(Usuario usuario) {
        System.out.println("Criando usuário");
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(arqUsuarios, true));
            bw.write(usuario.getId() + ";" + usuario.getNickName() + ";" + usuario.getSenha());
            bw.newLine();
            bw.close();
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("Usuario criado");
    }
}
