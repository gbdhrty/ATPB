package API;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ReviewService {
    private List<Review> reviews;
    private final String REVIEWS_CSV = "reviews.csv";
    private final File arqReviews = new File(REVIEWS_CSV);

    public List<Review> getReviews() {
        reviews = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(arqReviews));
            String linha = br.readLine();
            while (linha != null) {
                String[] campos = linha.split(";");
                int id = Integer.parseInt(campos[0]);
                String autor = campos[1];
                String texto = campos[2];
                Review novoReview = new Review(id, autor, texto);
                this.reviews.add(novoReview);
                linha = br.readLine();
            }
            br.close();
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return this.reviews;
    }

    public void criarReview(Review review) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(arqReviews, true));
            bw.write(review.getId() + ";" + review.getAutor() + ";" + review.getTexto());
            bw.newLine();
            bw.close();
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
