package com.znvks.salon.model.service;

import com.znvks.salon.model.dto.AccountDTO;
import com.znvks.salon.model.dto.PetDTO;

import java.util.List;

public interface PetService extends BaseService<PetDTO> {

    List<PetDTO> getPetsByAcc(AccountDTO user);

    List<PetDTO> getPetByKind(String kind);

    List<PetDTO> getPetByName(String name);
}
