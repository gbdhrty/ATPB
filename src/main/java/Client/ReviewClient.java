package Client;
import API.Review;
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

public class ReviewClient {
    static final String REVIEWS = "http://localhost:4567/reviews";
    static ObjectMapper mapper = new ObjectMapper();

    public static String getJson() {
        String json = "";
        try {
            HttpClient cliente = HttpClient.newBuilder().build();
            HttpRequest req = HttpRequest.newBuilder()
                    .uri(URI.create(REVIEWS))
                    .build();
            HttpResponse<String> res = cliente.send(req, HttpResponse.BodyHandlers.ofString());
            json = res.body();
        }
        catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        return json;
    }

    public static void registrarReview(Review review) {
        try {
            HttpClient cliente = HttpClient.newBuilder().build();
            String json = mapper.writeValueAsString(review);
            HttpRequest req = HttpRequest.newBuilder()
                    .uri(URI.create(REVIEWS))
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();
            HttpResponse<String> res = cliente.send(req, HttpResponse.BodyHandlers.ofString());
            System.out.println(res.body());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static List<Review> getReviewsToList() {
        String json = getJson();
        List<Review> reviews = new ArrayList<>();
        try {
            reviews = mapper.readValue(json, new TypeReference<List<Review>>() {});
        }
        catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return reviews;
    }
}
