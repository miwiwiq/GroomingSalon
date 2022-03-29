package com.znvks.salon.model.dto;

import com.znvks.salon.model.entity.Condition;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class FormDTO {

    private Long id;
    private Condition condition;
    private PetDTO pet;
    private List<ServiceDTO> services = new ArrayList<>();
}
