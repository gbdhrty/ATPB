package Cliente;

import API.Usuario;
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

public class ClienteUsuario {
    static final String USUARIOS = "http://localhost:4567/usuarios";
    static String json = getJson();

    public static String getJson() {
        String json = "";
        try {
            HttpClient cliente = HttpClient.newBuilder().build();
            HttpRequest req = HttpRequest.newBuilder()
                    .uri(URI.create(USUARIOS))
                    .build();
            HttpResponse<String> res = cliente.send(req, HttpResponse.BodyHandlers.ofString());
            json = res.body();
        }
        catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        return json;
    }

    public static void cadastrarUsuario(Usuario usuario) {
        try {
            HttpClient cliente = HttpClient.newBuilder().build();
            ObjectMapper mapper = new ObjectMapper();
            String requestBody = mapper.writeValueAsString(usuario);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(USUARIOS))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();
            HttpResponse<String> res = cliente.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(res.body());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }


    public static List<Usuario> getUsuariosToList() {
        List<Usuario> usuarios = new ArrayList<>();
        try {
            ObjectMapper mapper = new ObjectMapper();
            usuarios = mapper.readValue(json, new TypeReference<List<Usuario>>() {});
        }
        catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return usuarios;
    }
}
