package com.znvks.salon.service;

import com.znvks.salon.dto.AccountDTO;
import com.znvks.salon.dto.PetDTO;
import com.znvks.salon.dto.ServiceDTO;
import com.znvks.salon.entity.Pet;
import com.znvks.salon.entity.Service;
import com.znvks.salon.entity.account.Account;
import com.znvks.salon.entity.account.User;

import java.util.List;
import java.util.Optional;

public interface PetService extends BaseService<PetDTO, Pet> {

    List<PetDTO> getPetsByAcc(User user);

    List<PetDTO> getPetByKind(String kind);

    List<PetDTO> getPetByName(String name);
}
