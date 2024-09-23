package Client;

import API.Jogo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class JogoClient {
    static final String JOGOS = "http://localhost:4567/jogos";
    static ObjectMapper mapper = new ObjectMapper();
    static String json = getJson();

    public static String getJson() {
        String json = "";
        try {
            HttpClient cliente = HttpClient.newBuilder().build();
            HttpRequest req = HttpRequest.newBuilder()
                    .uri(URI.create(JOGOS))
                    .build();
            HttpResponse<String> res = cliente.send(req, HttpResponse.BodyHandlers.ofString());
            json = res.body();
        }
        catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        return json;
    }

    public static List<Jogo> getJogosToList() {
        List<Jogo> jogos = new ArrayList<>();
        try {
            jogos = mapper.readValue(json, new TypeReference<List<Jogo>>() {});
        }
        catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return jogos;
    }
}
