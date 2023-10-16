package com.junsim.whereami.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.junsim.whereami.config.MqttGateway;
import com.junsim.whereami.dto.*;
import com.junsim.whereami.service.ResourceService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ResourceController {
    private final MqttGateway mqttGateway;
    private final ResourceService resourceService;
    @PostMapping("/admin/registerResource")
    public ResponseEntity<?> registerResource(@RequestBody ResourceRequestDTO resourceRequestDTO) {
        resourceService.registerResource(resourceRequestDTO);
        return ResponseEntity.ok(true);
    }

    @GetMapping("/resources")
    public List<AllResourceResponseDTO> findAllResource() {
        return resourceService.findAllResource();
    }
    @PostMapping("/addResource")
    public ResponseEntity<?> addResource(@RequestBody AddResourceRequestDTO addResourceRequestDTO) {
        resourceService.addResource(addResourceRequestDTO);
        return ResponseEntity.ok(true);
    }
    @PostMapping("/askResource")
    public ResponseEntity<?> askResource(@RequestBody AskResourceRequestDTO askResourceRequestDTO) {
        resourceService.askResource(askResourceRequestDTO);
        return ResponseEntity.ok(true);
    }

    @GetMapping("/myResource")
    public List<MyResourceResponseDTO> findMyResource() {
        return resourceService.findMyResource();
    }

    @GetMapping("/admin/askList")
    public List<AllAskResponseDTO> findAllAsk() {
        return resourceService.findAllAsk();
    }

    @PostMapping("/admin/acceptAsk")
    public ResponseEntity<?> acceptAsk(@RequestBody AcceptAskRequestDTO acceptAskRequestDTO) {
        resourceService.acceptAsk(acceptAskRequestDTO);
        return ResponseEntity.ok(true);
    }
    @DeleteMapping("/admin/rejectAsk/{id}")
    public ResponseEntity<?> rejectAsk(@PathVariable Long id) {
        resourceService.rejectAsk(id);
        return ResponseEntity.ok(true);
    }

    @DeleteMapping("/deleteMyResource/{id}")
    public ResponseEntity<?> deleteMyResource(@PathVariable Long id) {
        resourceService.deleteMyResource(id);
        return ResponseEntity.ok(true);
    }

    @PostMapping("/turnOn/{id}")
    public ResponseEntity<?> turnOn(@PathVariable Long id, HttpServletRequest request) {
        resourceService.useResource(id, request);
        return ResponseEntity.ok(true);
    }

    @PostMapping("/admin/changeResource")
    public ResponseEntity<?> changeResource(@RequestBody ChangeResourceRequestDTO changeResourceRequestDTO) {
        resourceService.changeResource(changeResourceRequestDTO);
        return ResponseEntity.ok(true);
    }

}
