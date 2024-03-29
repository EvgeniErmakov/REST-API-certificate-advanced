package com.epam.esm.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Set;

@EqualsAndHashCode(callSuper = false)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class CertificateDTO extends RepresentationModel<CertificateDTO> {

    private Long id;

    @Size(min = 1, max = 300)
    @NotBlank
    private String name;

    @NotBlank
    @Size(min = 1, max = 300)
    private String description;

    @DecimalMin("0.1")
    @DecimalMax("1000.0")
    @NotNull
    private BigDecimal price;

    @Min(1)
    @Max(366)
    @NotNull
    private int duration;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    private ZonedDateTime createDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    private ZonedDateTime lastUpdateDate;

    @Valid
    private Set<TagDTO> tags;
}
