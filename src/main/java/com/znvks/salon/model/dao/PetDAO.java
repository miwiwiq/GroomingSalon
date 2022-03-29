package com.znvks.salon.model.dao;

import com.znvks.salon.model.entity.Pet;
import com.znvks.salon.model.entity.account.User;

import java.util.List;

public interface PetDAO extends BaseDAO<Long, Pet> {

    List<Pet> getPetsByAcc(User user);

    List<Pet> getPetByKind(String kind);

    List<Pet> getPetByName(String name);

}