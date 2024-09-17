import API.Jogo;
import API.Usuario;
import Cliente.ClienteJogo;
import Cliente.ClienteUsuario;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        List<Jogo> jogos = ClienteJogo.getJogosToList();
        List<Usuario> usuarios = ClienteUsuario.getUsuariosToList();

        boolean sair = false;

        System.out.println("1. Fazer Login");
        System.out.println("2. Criar Conta");
        while (!sair) {
            int opcao;
            opcao = entrarIntPositivo();
            switch (opcao) {
                case 1:
                    System.out.println("Digite seu login:");
                    Usuario usuarioAtual = verificarLogin(usuarios);
                    System.out.println("Digite sua senha:");
                    buscarSenha(usuarioAtual);
                    System.out.println("Olá, " + usuarioAtual.getNickName() + "!\n");
                    sair = true;
                    break;
                case 2:
                    System.out.println("Digite o nome de usuário desejado:");
                    String novoLogin = verificarNovoLogin(usuarios);
                    System.out.println("Digite a senha desejada:");
                    String novaSenha = verificarNovaSenha();
                    cadastrarUsuario(usuarios, novoLogin, novaSenha);
                    System.out.println("Olá, " + novoLogin + "!\n");
                    sair = true;
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
                System.out.println("\n[1] - Retornar");
                System.out.println("[0] - Sair");
                opcao = entrarInt();
                if (opcao == 0) {
                    System.out.println("Encerrando...");
                    break;
                }
            }
        }
    }

   public static void exibirDadosJogo(Jogo jogo) {
       System.out.println("\nTítulo: " + jogo.getNome());
       System.out.println("Gênero: " + jogo.getGenero());
       System.out.println("Descricao: " + jogo.getDescricao());
   }

    public static void exibirJogos(List<Jogo> jogos) {
        System.out.println("Lista de Jogos:");
        for (Jogo jogo : jogos) {
            System.out.println("[" + jogo.getId() + "]" + " - " + jogo.getNome());
        }
        System.out.println("\n[0] - Sair\n");
        System.out.println("Digite o número correspondente ao jogo desejado para ver mais informações:");
    }

    public static void cadastrarUsuario(List<Usuario> usuarios, String login, String senha) {
        int id = usuarios.size() + 1;
        Usuario novoUsuario = new Usuario(id, login, senha);
        ClienteUsuario.cadastrarUsuario(novoUsuario);
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

    public static void buscarSenha(Usuario usuario) {
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


    public static int entrarIntPositivo() {
        Scanner sc = new Scanner(System.in);
        int numero = 0;
        while (true) {
            try {
                numero = sc.nextInt();
                if (numero < 1) {
                    System.out.println("Digite um numero positivo.");
                }
                else {
                    break;
                }
            }
            catch (InputMismatchException e) {
                System.out.println("Digite uma opção válida.");
                sc.next();
            }
        }
        return numero;
    }
}
