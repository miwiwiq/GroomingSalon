package com.znvks.salon.dto;

import com.znvks.salon.entity.Condition;
import com.znvks.salon.entity.Pet;
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
