package com.br.igorsily.udemytestespringboot.dto.v1;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDTO  implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private UUID id;

    private String firstName;

    private String lastName;

    private String email;

}
