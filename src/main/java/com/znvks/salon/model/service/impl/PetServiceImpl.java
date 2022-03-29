package com.znvks.salon.model.service.impl;

import com.znvks.salon.model.dao.KindDAO;
import com.znvks.salon.model.dao.PetDAO;
import com.znvks.salon.model.dto.AccountDTO;
import com.znvks.salon.model.dto.PetDTO;
import com.znvks.salon.model.entity.Kind;
import com.znvks.salon.model.entity.Pet;
import com.znvks.salon.model.entity.account.User;
import com.znvks.salon.model.service.PetService;
import com.znvks.salon.model.mapper.AccountMapper;
import com.znvks.salon.model.mapper.PetMapper;
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
            Long id = kindDAO.save(pet.getKind());
            kind = kindDAO.getById(id);
            kind.ifPresent(pet::setKind);
        }
        return petDAO.save(pet);
    }

    @Override
    @Transactional
    public void update(PetDTO petDTO) {
        Pet pet = petMapper.mapToEntity(petDTO);
        Optional<Kind> kind = kindDAO.getByName(petDTO.getKind());
        if (Objects.nonNull(kind) && kind.isPresent()) {
            pet.setKind(kind.get());
        }
        petDAO.update(pet);
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
