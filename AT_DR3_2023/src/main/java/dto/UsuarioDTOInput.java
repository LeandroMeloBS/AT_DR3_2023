package dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UsuarioDTOInput {
    private int id;
    private String nome;
    private String senha;
}