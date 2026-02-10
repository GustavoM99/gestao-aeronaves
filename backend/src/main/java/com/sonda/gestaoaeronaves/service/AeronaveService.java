package com.sonda.gestaoaeronaves.service;

import com.sonda.gestaoaeronaves.dto.*;
import com.sonda.gestaoaeronaves.exception.InvalidManufacturerException;
import com.sonda.gestaoaeronaves.exception.ResourceNotFoundException;
import com.sonda.gestaoaeronaves.model.Aeronave;
import com.sonda.gestaoaeronaves.repository.AeronaveRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AeronaveService {

    private final AeronaveRepository aeronaveRepository;

    private static final Set<String> VALID_MANUFACTURERS = new HashSet<>(Arrays.asList(
        "Embraer", "Boeing", "Airbus", "Cessna", "Bombardier", 
        "Gulfstream", "Dassault", "Cirrus", "Piper", "Beechcraft",
        "ATR", "Lockheed Martin", "Northrop Grumman", "Mitsubishi",
        "Antonov", "Tupolev", "Sukhoi", "Comac", "Honda"
    ));

    @Transactional(readOnly = true)
    public List<AeronaveResponseDTO> findAll() {
        return aeronaveRepository.findAll().stream()
            .map(AeronaveResponseDTO::fromEntity)
            .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public AeronaveResponseDTO findById(Long id) {
        Aeronave aeronave = aeronaveRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Aeronave não encontrada com id: " + id));
        return AeronaveResponseDTO.fromEntity(aeronave);
    }

    @Transactional(readOnly = true)
    public List<AeronaveResponseDTO> findBySearchTerm(String termo) {
        if (termo == null || termo.trim().isEmpty()) {
            return findAll();
        }
        return aeronaveRepository.findByTermoBusca(termo).stream()
            .map(AeronaveResponseDTO::fromEntity)
            .collect(Collectors.toList());
    }

    @Transactional
    public AeronaveResponseDTO create(AeronaveRequestDTO dto) {
        validateManufacturer(dto.getMarca());
        
        Aeronave aeronave = new Aeronave();
        aeronave.setNome(dto.getNome());
        aeronave.setMarca(dto.getMarca());
        aeronave.setAno(dto.getAno());
        aeronave.setDescricao(dto.getDescricao());
        aeronave.setVendido(dto.getVendido() != null ? dto.getVendido() : false);
        
        Aeronave saved = aeronaveRepository.save(aeronave);
        return AeronaveResponseDTO.fromEntity(saved);
    }

    @Transactional
    public AeronaveResponseDTO update(Long id, AeronaveRequestDTO dto) {
        Aeronave aeronave = aeronaveRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Aeronave não encontrada com id: " + id));
        
        if (dto.getMarca() != null && !dto.getMarca().equals(aeronave.getMarca())) {
            validateManufacturer(dto.getMarca());
        }
        
        aeronave.setNome(dto.getNome());
        aeronave.setMarca(dto.getMarca());
        aeronave.setAno(dto.getAno());
        aeronave.setDescricao(dto.getDescricao());
        if (dto.getVendido() != null) {
            aeronave.setVendido(dto.getVendido());
        }
        
        Aeronave updated = aeronaveRepository.save(aeronave);
        return AeronaveResponseDTO.fromEntity(updated);
    }

    @Transactional
    public void delete(Long id) {
        if (!aeronaveRepository.existsById(id)) {
            throw new ResourceNotFoundException("Aeronave não encontrada com id: " + id);
        }
        aeronaveRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Long countNaoVendidos() {
        return aeronaveRepository.countByVendido(false);
    }

    @Transactional(readOnly = true)
    public List<AeronaveResponseDTO> findLastWeek() {
        LocalDateTime oneWeekAgo = LocalDateTime.now().minusWeeks(1);
        return aeronaveRepository.findByCreatedAfter(oneWeekAgo).stream()
            .map(AeronaveResponseDTO::fromEntity)
            .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<DecadaStatisticsDTO> getDecadaStatistics() {
        List<Object[]> results = aeronaveRepository.countByDecada();
        return results.stream()
            .map(row -> new DecadaStatisticsDTO("Década " + row[0], (Long) row[1]))
            .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<MarcaStatisticsDTO> getMarcaStatistics() {
        List<Object[]> results = aeronaveRepository.countByMarca();
        return results.stream()
            .map(row -> new MarcaStatisticsDTO((String) row[0], (Long) row[1]))
            .collect(Collectors.toList());
    }

    public Set<String> getValidManufacturers() {
        return new TreeSet<>(VALID_MANUFACTURERS);
    }

    private void validateManufacturer(String marca) {
        if (marca == null || marca.trim().isEmpty()) {
            throw new InvalidManufacturerException("Marca não pode ser vazia");
        }
        
        boolean isValid = VALID_MANUFACTURERS.stream()
            .anyMatch(valid -> valid.equalsIgnoreCase(marca.trim()));
        
        if (!isValid) {
            throw new InvalidManufacturerException(
                String.format("Marca inválida: '%s'. Marcas válidas: %s", 
                    marca, String.join(", ", getValidManufacturers()))
            );
        }
    }
}
