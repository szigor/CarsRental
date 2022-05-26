package pl.carsrental.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequiredArgsConstructor
@Transactional
public class ClientController {

    private final ClientService clientService;

    @GetMapping(path = "/admin/clients")
    public String getClients(ModelMap modelMap) {
        modelMap.addAttribute("clients", clientService.getClients());
        return "client-panel";
    }

    @GetMapping(path = "/admin/client/create")
    public String showCreateClientForm(ModelMap modelMap) {
        modelMap.addAttribute("emptyClient", new Client());
        return "client-create";
    }

    @PostMapping(path = "/admin/client/save")
    public String handleNewClient(@ModelAttribute("emptyClient") Client client) {
        log.info("Handle new client: " + client);
        clientService.save(client);
        return "redirect:/admin/clients";
    }

    @PostMapping(path = "/admin/client/update")
    public String handleUpdatedClient(@ModelAttribute("client") Client client) {
        log.info("Handle updated client: " + client);
        clientService.save(client);
        return "redirect:/admin/clients";
    }

    @GetMapping(path = "/admin/client/edit/{clientId}")
    public String showUpdateClientForm(@PathVariable Long clientId, ModelMap modelMap) {
        modelMap.addAttribute("client", clientService.getClientById(clientId));
        return "client-edit";
    }

    @GetMapping(path = "/admin/client/delete/{clientId}")
    public String deleteClient(@PathVariable("clientId") Long clientId) {
        clientService.deleteClientById(clientId);
        log.info("Deleted client with id " + clientId);
        return "redirect:/admin/clients";
    }
}
