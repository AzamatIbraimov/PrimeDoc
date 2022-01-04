package one.primedoc.backend.controller;

import one.primedoc.backend.entity.ClientEntity;
import one.primedoc.backend.model.ClientModel;
import one.primedoc.backend.model.MedCardModel;
import one.primedoc.backend.service.ClientService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/client")
public class ClientRestController {

    private final ClientService clientService;

    public ClientRestController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/")
    public List<ClientEntity> getAllClients() { return clientService.getAll(); }

    @GetMapping("/{id}")
    public ClientEntity getClientById(@PathVariable("id") Long id) {
        return clientService.getById(id);
    }

    @GetMapping("/card")
    public Page<List<ClientModel>> getAllMedCards(@RequestParam("page") Integer page, @RequestParam(value = "size", defaultValue = "10") Integer size) {
        return clientService.getAllMedCards(PageRequest.of(page, size));
    }

    @GetMapping("/card/{id}")
    public MedCardModel getMedCardById(@PathVariable("id") Long id) {
        return clientService.getMedCardById(id);
    }

    @PutMapping("/{id}")
    public ClientEntity putClientById(@PathVariable("id") Long id, @ModelAttribute("imageFile") MultipartFile imageFile) {
        return clientService.updateById(id, imageFile);
    }

    @PutMapping("/card/{id}")
    public MedCardModel putClientMedCardById(@PathVariable("id") Long id, @RequestBody MedCardModel med) {
        return clientService.updateMedCardById(id, med);
    }

    @DeleteMapping("/{id}")
    public String deleteClientById(@PathVariable("id") Long id) {
        return clientService.deleteById(id);
    }
}

