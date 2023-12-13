package model;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class Usuario {
    private int id;
    private String nome;
    private String senha;
}

