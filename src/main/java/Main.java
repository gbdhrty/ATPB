import API.Jogo;
import API.Review;
import API.Usuario;
import Client.JogoClient;
import Client.ReviewClient;
import Client.UsuarioClient;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        List<Jogo> jogos = JogoClient.getJogosToList();
        List<Usuario> usuarios = UsuarioClient.getUsuariosToList();
        Usuario usuarioAtual = new Usuario();

        boolean saidaMenuInicial = false;

        while (!saidaMenuInicial) {
            int opcao;
            exibirMenuInicial();
            opcao = entrarInt();

            switch (opcao) {
                case 1:
                    System.out.println("Digite seu login:");
                    usuarioAtual = verificarLogin(usuarios);
                    System.out.println("Digite sua senha:");
                    verificarSenha(usuarioAtual);
                    System.out.println("Olá, " + usuarioAtual.getNickName());
                    saidaMenuInicial = true;
                    break;
                case 2:
                    System.out.println("Digite o nome de usuário desejado:");
                    String novoLogin = verificarNovoLogin(usuarios);
                    System.out.println("Digite a senha desejada:");
                    String novaSenha = verificarNovaSenha();
                    usuarioAtual = criarUsuario(usuarios, novoLogin, novaSenha);
                    UsuarioClient.cadastrarUsuario(usuarioAtual);
                    System.out.println("Olá, " + usuarioAtual.getNickName());
                    saidaMenuInicial = true;
                    break;
                case 0:
                    System.out.println("Encerrando...");
                    saidaMenuInicial = true;
                    break;
                default:
                    System.out.println("Opção inválida, tente novamente.");
                    break;
            }
        }
        while (true) {
            int opcao;
            exibirJogos(jogos);
            opcao = entrarInt();

            if (opcao == 0) {
                System.out.println("Encerrando...");
                break;
            }
            else if (opcao > jogos.size()) {
                System.out.println("Jogo não encontrado, tente novamente.");
            }
            else {
                Jogo jogoAtual = jogos.get(opcao - 1);
                exibirDadosJogo(jogoAtual);
                System.out.println("\n[1] - Ver reviews");
                System.out.println("[2] - Escrever review");

                opcao = entrarInt();

                switch (opcao) {
                    case 1:
                        exibirReviews(jogoAtual.getId());
                        break;
                    case 2:
                        System.out.println("Digite sua review para " + jogoAtual.getNome() + ":");
                        Review review = criarReview(jogoAtual.getId(), usuarioAtual.getNickName());
                        ReviewClient.registrarReview(review);
                        break;
                    default:
                        System.out.println("Opção inválida, tente novamente.");
                        break;
                }
            }
        }
    }

    public static void exibirReviews(int id) {
        List<Review> reviews = ReviewClient.getReviewsToList();
        boolean temReview = false;
        for (Review review : reviews) {
            if (review.getId() == id) {
                temReview = true;
                System.out.println("\nReview de " + review.getAutor() + ":");
                System.out.println(review.getTexto());
            }
        }
        if (!temReview) {
            System.out.println("Ainda não há reviews para este jogo.");
        }
    }

    public static Review criarReview(int id, String autor) {
        Scanner sc = new Scanner(System.in);
        String texto;
        Review review;
        while (true) {
            texto = sc.nextLine();
            if (texto.isBlank()) {
                System.out.println("A review não pode estar em branco.");
            }
            else {
                review = new Review(id, autor, texto);
                break;
            }
        }
        return review;
    }

    public static void exibirMenuInicial() {
        System.out.println("\n[1] - Fazer Login");
        System.out.println("[2] - Criar Conta");
        System.out.println("\n[0] - Sair");
    }

   public static void exibirDadosJogo(Jogo jogo) {
       System.out.println("\nTítulo: " + jogo.getNome());
       System.out.println("Gênero: " + jogo.getGenero());
   }

    public static void exibirJogos(List<Jogo> jogos) {
        System.out.println("\nLista de Jogos:");
        for (Jogo jogo : jogos) {
            System.out.println("[" + jogo.getId() + "]" + " - " + jogo.getNome());
        }
        System.out.println("\n[0] - Sair\n");
        System.out.println("Digite o número correspondente ao jogo desejado para ver mais informações:");
    }

    public static Usuario criarUsuario(List<Usuario> usuarios, String login, String senha) {
        int id = usuarios.size() + 1;
        return new Usuario(id, login, senha);
    }

    public static String verificarNovoLogin(List<Usuario> usuarios) {
        Scanner sc = new Scanner(System.in);
        String login;
        while (true) {
            login = sc.nextLine();
            if (login.isBlank()) {
                System.out.println("O nome de usuário não pode estar vazio.");
            }
            else {
                String finalLogin = login;
                boolean existeUsuario = usuarios.stream().anyMatch(usuario -> usuario.getNickName().equals(finalLogin));
                if (existeUsuario) {
                    System.out.println("Usuário existente, tente novamente.");
                }
                else {
                    break;
                }
            }
        }
        return login;
    }

    public static String verificarNovaSenha() {
        Scanner sc = new Scanner(System.in);
        String senha;
        while (true) {
            senha = sc.nextLine();
            if (senha.isBlank()) {
                System.out.println("A senha não pode estar vazia.");
            }
            else {
                break;
            }
        }
        return senha;
    }

    public static Usuario verificarLogin(List<Usuario> usuarios) {
        Scanner sc = new Scanner(System.in);
        String login;
        Usuario usuarioSelecionado;
        while (true) {
            login = sc.nextLine();
            String finalLogin = login;
            usuarioSelecionado = (usuarios.stream()
                    .filter(usuario -> usuario.getNickName().equals(finalLogin))
                    .findFirst()
                    .orElse(null));
            if (usuarioSelecionado == null) {
                System.out.println("Usuário não encontrado, tente novamente.");
            }
            else {
                break;
            }
        }
        return usuarioSelecionado;
    }

    public static void verificarSenha(Usuario usuario) {
        Scanner sc = new Scanner(System.in);
        String senha;
        while (true) {
            senha = sc.nextLine();
            if (Objects.equals(senha, usuario.getSenha())) {
                break;
            }
            else {
                System.out.println("Senha incorreta.");
            }
        }
    }

    public static int entrarInt() {
        Scanner sc = new Scanner(System.in);
        int numero = 0;
        while (true) {
            try {
                numero = sc.nextInt();
                break;
            }
            catch (InputMismatchException e) {
                System.out.println("Digite uma opção válida.");
                sc.next();
            }
        }
        return numero;
    }
}
