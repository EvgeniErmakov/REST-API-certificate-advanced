package com.epam.esm.model.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@EqualsAndHashCode(callSuper = false)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TagDTO extends RepresentationModel<TagDTO> {

    private long id;

    @Size(min = 1, max = 300)
    @NotBlank
    private String name;
}
