package com.felix.basic_projects.mini_market.controller;

import com.felix.basic_projects.mini_market.model.dto.request.CreateStockEntryRequestDTO;
import com.felix.basic_projects.mini_market.model.dto.request.UpdateStockEntryRequestDTO;
import com.felix.basic_projects.mini_market.model.dto.response.StockEntryResponseDTO;
import com.felix.basic_projects.mini_market.model.entity.StockEntry;
import com.felix.basic_projects.mini_market.service.StockEntryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/v1")
public class StockEntryController {

  @Autowired
  StockEntryService stockEntryService;

  @GetMapping("/stockEntries")
  public ResponseEntity<List<StockEntryResponseDTO>> retrieveAllStockEntry() {
    return ResponseEntity.ok(stockEntryService.retrieveAllStockEntry());
  }

  @GetMapping("/stockEntries/{id}")
  public ResponseEntity<StockEntryResponseDTO> findStockEntryById(@PathVariable Long id) {
    return ResponseEntity.ok(stockEntryService.findStockEntryById(id));
  }

  @PostMapping("/stockEntries")
  public ResponseEntity<StockEntryResponseDTO> saveStockEntry(
    @Valid @RequestBody CreateStockEntryRequestDTO stockEntry
  ) {
    StockEntryResponseDTO newStockEntry = stockEntryService.saveStockEntry(stockEntry);

    URI location = ServletUriComponentsBuilder.fromCurrentRequest()
      .path("/{id}")
      .buildAndExpand(newStockEntry.getId())
      .toUri();

    return ResponseEntity.created(location).body(newStockEntry);
  }

  @DeleteMapping("/stockEntries/{id}")
  public ResponseEntity<StockEntryResponseDTO> deleteStockEntryById(@PathVariable Long id) {
    return ResponseEntity.ok(stockEntryService.deleteStockEntryById(id));
  }

  @PatchMapping("/stockEntries/{id}")
  public ResponseEntity<StockEntryResponseDTO> updateStockEntryById(
    @PathVariable Long id,
    @Valid @RequestBody UpdateStockEntryRequestDTO stockEntryRequest
  ) {
    return ResponseEntity.ok(stockEntryService.updateStockEntryById(id, stockEntryRequest));
  }

}
