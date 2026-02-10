package com.sonda.gestaoaeronaves.controller;

import com.sonda.gestaoaeronaves.dto.*;
import com.sonda.gestaoaeronaves.service.AeronaveService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/aeronaves")
@RequiredArgsConstructor
@Slf4j
public class AeronaveController {

    private final AeronaveService aeronaveService;

    @GetMapping
    public ResponseEntity<List<AeronaveResponseDTO>> getAllAeronaves() {
        List<AeronaveResponseDTO> aeronaves = aeronaveService.findAll();
        return ResponseEntity.ok(aeronaves);
    }

    @GetMapping("/find")
    public ResponseEntity<List<AeronaveResponseDTO>> searchAeronaves(@RequestParam(required = false) String termo) {
        List<AeronaveResponseDTO> aeronaves = aeronaveService.findBySearchTerm(termo);
        return ResponseEntity.ok(aeronaves);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AeronaveResponseDTO> getAeronaveById(@PathVariable Long id) {
        AeronaveResponseDTO aeronave = aeronaveService.findById(id);
        return ResponseEntity.ok(aeronave);
    }

    @PostMapping
    public ResponseEntity<AeronaveResponseDTO> createAeronave(@Valid @RequestBody AeronaveRequestDTO dto) {
        AeronaveResponseDTO created = aeronaveService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AeronaveResponseDTO> updateAeronave(@PathVariable Long id, @Valid @RequestBody AeronaveRequestDTO dto) {
        AeronaveResponseDTO updated = aeronaveService.update(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAeronave(@PathVariable Long id) {
        aeronaveService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/statistics/nao-vendidos")
    public ResponseEntity<Long> countNaoVendidos() {
        Long count = aeronaveService.countNaoVendidos();
        return ResponseEntity.ok(count);
    }

    @GetMapping("/statistics/last-week")
    public ResponseEntity<List<AeronaveResponseDTO>> getLastWeek() {
        List<AeronaveResponseDTO> aeronaves = aeronaveService.findLastWeek();
        return ResponseEntity.ok(aeronaves);
    }

    @GetMapping("/statistics/decadas")
    public ResponseEntity<List<DecadaStatisticsDTO>> getDecadaStatistics() {
        List<DecadaStatisticsDTO> statistics = aeronaveService.getDecadaStatistics();
        return ResponseEntity.ok(statistics);
    }

    @GetMapping("/statistics/marcas")
    public ResponseEntity<List<MarcaStatisticsDTO>> getMarcaStatistics() {
        List<MarcaStatisticsDTO> statistics = aeronaveService.getMarcaStatistics();
        return ResponseEntity.ok(statistics);
    }

    @GetMapping("/manufacturers")
    public ResponseEntity<Set<String>> getValidManufacturers() {
        Set<String> manufacturers = aeronaveService.getValidManufacturers();
        return ResponseEntity.ok(manufacturers);
    }
}
