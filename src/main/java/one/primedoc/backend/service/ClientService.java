package one.primedoc.backend.service;

import one.primedoc.backend.entity.ClientEntity;
import one.primedoc.backend.model.ClientModel;
import one.primedoc.backend.model.MedCardModel;
import one.primedoc.backend.model.RegistrationModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ClientService {
    public List<ClientEntity> getAll();
    public ClientEntity getById(Long id);
    public ClientEntity create(RegistrationModel client);
    public ClientEntity updateById(Long id, MultipartFile imageFile);
    public String deleteById(Long id);
    public MedCardModel updateMedCardById(Long id, MedCardModel med);
    public Page<List<ClientModel>> getAllMedCards(Pageable pageable);
    public MedCardModel getMedCardById(Long id);
}
