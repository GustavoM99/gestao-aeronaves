package com.sonda.gestaoaeronaves.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "aeronaves", indexes = {
    @Index(name = "idx_marca", columnList = "marca"),
    @Index(name = "idx_ano", columnList = "ano"),
    @Index(name = "idx_vendido", columnList = "vendido"),
    @Index(name = "idx_created", columnList = "created")
})
@EntityListeners(AuditingEntityListener.class)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Aeronave {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nome é obrigatório")
    @Size(min = 2, max = 100, message = "Nome deve ter entre 2 e 100 caracteres")
    @Column(nullable = false, length = 100)
    private String nome;

    @NotBlank(message = "Marca é obrigatória")
    @Column(nullable = false, length = 50)
    private String marca;

    @NotNull(message = "Ano é obrigatório")
    @Min(value = 1900, message = "Ano deve ser maior ou igual a 1900")
    @Max(value = 2100, message = "Ano deve ser menor ou igual a 2100")
    @Column(nullable = false)
    private Integer ano;

    @Column(columnDefinition = "TEXT")
    private String descricao;

    @Column(nullable = false)
    private Boolean vendido = false;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime created;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updated;

    @Transient
    public Integer getDecada() {
        if (ano == null) return null;
        return (ano / 10) * 10;
    }
}
