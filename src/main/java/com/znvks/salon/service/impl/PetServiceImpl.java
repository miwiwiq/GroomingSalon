package com.znvks.salon.service.impl;

import com.znvks.salon.dao.AccountDAO;
import com.znvks.salon.dao.KindDAO;
import com.znvks.salon.dao.PetDAO;
import com.znvks.salon.dto.AccountDTO;
import com.znvks.salon.dto.PetDTO;
import com.znvks.salon.entity.Form;
import com.znvks.salon.entity.Kind;
import com.znvks.salon.entity.Pet;
import com.znvks.salon.entity.account.Account;
import com.znvks.salon.entity.account.User;
import com.znvks.salon.mapper.AccountMapper;
import com.znvks.salon.mapper.PetMapper;
import com.znvks.salon.service.PetService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Transactional(readOnly = true)
public class PetServiceImpl implements PetService {

    private final PetDAO petDAO;
    private final PetMapper petMapper;
    private final KindDAO kindDAO;
    private final AccountMapper accountMapper;


    @Override
    public Optional<PetDTO> getById(Long id) {
        Optional<Pet> pet = petDAO.getById(id);
        return Optional.ofNullable(petMapper.mapToDto(pet.orElse(null)));
    }

    @Override
    @Transactional
    public Long save(PetDTO petDTO) {
        Pet pet = petMapper.mapToEntity(petDTO);
        Optional<Kind> kind = kindDAO.getByName(petDTO.getKind());
        if (Objects.nonNull(kind) && kind.isPresent()) {
            pet.setKind(kind.get());
        } else {
            kindDAO.save(pet.getKind());
        }
        return petDAO.save(pet);
    }

    @Override
    @Transactional
    public void update(PetDTO petDTO) {
        petDAO.update(petMapper.mapToEntity(petDTO));
    }

    @Override
    @Transactional
    public void delete(PetDTO petDTO) {
        Pet pet = petDAO.getById(petDTO.getId()).orElse(null);
        petDAO.delete(pet);
    }

    @Override
    public List<PetDTO> getAll() {
        List<Pet> pets = petDAO.getAll();
        return petMapper.mapToListDto(pets);
    }

    @Override
    public List<PetDTO> getPetsByAcc(AccountDTO accountDTO) {
        List<Pet> pets = petDAO.getPetsByAcc( (User) accountMapper.mapToEntity(accountDTO));
        return petMapper.mapToListDto(pets);
    }

    @Override
    public List<PetDTO> getPetByKind(String kind) {
        List<Pet> pets = petDAO.getPetByKind(kind);
        return petMapper.mapToListDto(pets);
    }

    @Override
    public List<PetDTO> getPetByName(String name) {
        List<Pet> pets = petDAO.getPetByName(name);
        return petMapper.mapToListDto(pets);
    }
}
