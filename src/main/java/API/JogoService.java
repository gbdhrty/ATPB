package API;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JogoService {
    private List<Jogo> jogos;
    private final String JOGOS_CSV = "jogos.csv";
    private File arqJogos = new File(JOGOS_CSV);

    public List<Jogo> getJogos() {
        this.jogos = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(arqJogos));
            String linha = br.readLine();
            while (linha != null) {
                String[] campos = linha.split(";");
                int id = Integer.parseInt(campos[0]);
                String jogo = campos[1];
                String genero = campos[2];
                String descricao = campos[3];
                Jogo novoJogo = new Jogo(id, jogo, genero, descricao);
                this.jogos.add(novoJogo);
                linha = br.readLine();
            }
            br.close();
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return this.jogos;
    }
}
