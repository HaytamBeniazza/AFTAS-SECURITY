package com.WI.WIGOLDFISH.controllers;

import com.WI.WIGOLDFISH.entities.fish.FishDtoReq;
import com.WI.WIGOLDFISH.services.impl.FishServiceImpl;
import com.WI.WIGOLDFISH.services.interfaces.FishService;
import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/fish")
@RequiredArgsConstructor
public class FishController {
    private final FishService fishServiceImpl;

    @PostMapping
    @PreAuthorize("hasAnyAuthority('JURY', 'MANAGER')")
    public ResponseEntity<?> createFish(@Valid @RequestBody FishDtoReq fishDtoReq) {
        fishDtoReq = fishServiceImpl.save(fishDtoReq);
        Map<String, Object> response = new HashMap<>();
        response.put("data", fishDtoReq);
        response.put("message", "Fish created successfully");
        return ResponseEntity.ok(response);
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ROLE_MANAGER', 'ROLE_JURY','ROLE_ADHERENT')")
    public ResponseEntity<?> getFishs() {
        return ResponseEntity.ok(fishServiceImpl.findAll());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_MANAGER', 'ROLE_JURY','ROLE_ADHERENT')")
    public ResponseEntity<?> getFish(@PathVariable String id) {
        return ResponseEntity.ok(fishServiceImpl.findOne(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('JURY', 'MANAGER')")
    public ResponseEntity<?> updateFish(@PathVariable String id, @Valid @RequestBody FishDtoReq fishDtoReq) {
        fishDtoReq = fishServiceImpl.update(fishDtoReq, id);
        Map<String, Object> response = new HashMap<>();
        response.put("data", fishDtoReq);
        response.put("message", "Fish updated successfully");
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('JURY', 'MANAGER')")
    public ResponseEntity<?> deleteFish(@PathVariable String id) {
        fishServiceImpl.delete(id);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Fish deleted successfully");
        return ResponseEntity.ok(response);
    }
}
