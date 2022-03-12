package com.znvks.salon.dao;

import com.znvks.salon.entity.Pet;
import com.znvks.salon.entity.account.User;

import java.util.List;

public interface PetDAO extends BaseDAO<Long, Pet> {

    List<Pet> getPetsByAcc(User user);

    List<Pet> getPetByKind(String kind);

    List<Pet> getPetByName(String name);

}