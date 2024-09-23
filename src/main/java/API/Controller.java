package API;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

import static spark.Spark.*;

public class Controller {
    public static void main(String[] args) {
        JogoService js = new JogoService();
        UsuarioService us = new UsuarioService();
        ReviewService rs = new ReviewService();
        ObjectMapper mapper = new ObjectMapper();

        get("/jogos", (req, res) -> {
            List<Jogo> jogos = js.getJogos();
            res.type("application/json");
            res.status(200);
            return mapper.writeValueAsString(jogos);
        });

        get("/usuarios", (req, res) -> {
            List<Usuario> usuarios = us.getUsuarios();
            res.type("application/json");
            res.status(200);
            return mapper.writeValueAsString(usuarios);
        });

        get("/reviews", (req, res) -> {
            List<Review> reviews = rs.getReviews();
            res.type("application/json");
            res.status(200);
            return mapper.writeValueAsString(reviews);
        });

        post("/usuarios", (req, res) -> {
            res.type("application/json");
            Usuario novoUsuario = mapper.readValue(req.body(), Usuario.class);
            us.criarUsuario(novoUsuario);
            res.status(201);
            return "Usuário criado com sucesso!";
        });

        post("/reviews", (req, res) -> {
            res.type("application/json");
            Review novoReview = mapper.readValue(req.body(), Review.class);
            rs.criarReview(novoReview);
            res.status(201);
            return "Review enviada com sucesso!";
        });
    }
}
