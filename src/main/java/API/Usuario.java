package API;

import lombok.*;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@ToString
public class Usuario {
    private int id;
    private String nickName;
    private String senha;
}
