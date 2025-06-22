package com.senla.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.senla.dto.tier.TierCreateDto;
import com.senla.dto.tier.TierResponseDto;
import com.senla.dto.tier.TierUpdateDto;
import com.senla.exception.DaoException;
import com.senla.model.tier.Tier;
import com.senla.service.tier.TierService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/tiers")
@RequiredArgsConstructor
public class TierController {

    private final TierService tierService;
    private final ModelMapper modelMapper;

    @GetMapping("/all")
    @PreAuthorize("hasAuthority({'TIER_CRUD'})")
    public ResponseEntity<List<TierResponseDto>> getAllTiers() throws JsonProcessingException {
        List<Tier> tiers = tierService.findAll();
        return new ResponseEntity<>(tiers.stream().map(this::convertToResponseDto).collect(Collectors.toList()), HttpStatus.OK);
    }

    @PostMapping()
    @PreAuthorize("hasAuthority({'TIER_CRUD'})")
    public ResponseEntity<TierResponseDto> createTier(@Valid @RequestBody TierCreateDto tierCreateDto) throws JsonProcessingException {
        Tier tier = tierService.createTier(tierCreateDto);
        return new ResponseEntity<>(convertToResponseDto(tier), HttpStatus.CREATED);
    }

    @PutMapping("/{tierId}")
    @PreAuthorize("hasAuthority({'TIER_CRUD'})")
    public ResponseEntity<TierResponseDto> updateTier(@Valid @RequestBody TierUpdateDto tierUpdateDto, @PathVariable("tierId") Long tierId) throws JsonProcessingException {
        Tier tier = tierService.updateTier(tierId, tierUpdateDto);
        return new ResponseEntity<>(convertToResponseDto(tier), HttpStatus.CREATED);
    }
    @GetMapping("/{tierId}")
    @PreAuthorize("hasAuthority({'TIER_CRUD'})")
    public ResponseEntity<TierResponseDto> getTierById(@PathVariable("tierId") Long tierId) throws JsonProcessingException {
        Tier tier = tierService.findById(tierId).orElseThrow(()->new DaoException("Tier not found"));
        return new ResponseEntity<>(convertToResponseDto(tier), HttpStatus.OK);
    }

    @DeleteMapping("/{tierId}")
    @PreAuthorize("hasAuthority({'TIER_CRUD'})")
    public ResponseEntity<?> deleteTier(@PathVariable("tierId") Long tierId) {
        tierService.deleteById(tierId);
        return ResponseEntity.noContent().build();
    }

    private TierResponseDto convertToResponseDto(Tier tier) {
        return modelMapper.map(tier, TierResponseDto.class);
    }
}
