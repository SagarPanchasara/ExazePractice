package com.example.practical.controller;

import com.example.practical.json.ClientJson;
import com.example.practical.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/client")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;

    @PostMapping
    public ClientJson insert(@RequestBody @Valid ClientJson clientJson) {
        return clientService.insert(clientJson);
    }

    @PutMapping("/{id}")
    public void update(@PathVariable long id, @RequestBody @Valid ClientJson clientJson) {
        clientService.update(id, clientJson);
    }

    @GetMapping
    public Page<ClientJson> getList(@RequestParam(required = false) String query, Pageable pageable) {
        return clientService.getList(query, pageable);
    }
}
