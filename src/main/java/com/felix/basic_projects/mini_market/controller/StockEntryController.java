package com.felix.basic_projects.mini_market.controller;

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
  public ResponseEntity<List<StockEntry>> retrieveAllStockEntry() {
    List<StockEntry> stockEntries = stockEntryService.retrieveAllStockEntry();
    return ResponseEntity.ok(stockEntries);
  }

  @GetMapping("/stockEntries/{id}")
  public ResponseEntity<StockEntry> findStockEntryById(@PathVariable Long id) {
    StockEntry stockEntry = stockEntryService.findStockEntryById(id);
    return ResponseEntity.ok(stockEntry);
  }

  @PostMapping("/stockEntries")
  public ResponseEntity<StockEntry> saveStockEntry(@Valid @RequestBody StockEntry stockEntry) {
    StockEntry newStockEntry = stockEntryService.saveStockEntry(stockEntry);

    URI location = ServletUriComponentsBuilder.fromCurrentRequest()
      .path("/{id}")
      .buildAndExpand(newStockEntry.getId())
      .toUri();

    return ResponseEntity.created(location).body(newStockEntry);
  }

  @DeleteMapping("/stockEntries/{id}")
  public ResponseEntity<StockEntry> deleteStockEntryById(@PathVariable Long id) {
    StockEntry stockEntry  = stockEntryService.deleteStockEntryById(id);
    return ResponseEntity.ok(stockEntry);
  }

  @PatchMapping("/stockEntries/{id}")
  public ResponseEntity<StockEntry> updateStockEntryById(@PathVariable Long id, @Valid @RequestBody StockEntry stockEntry) {
    StockEntry updatedStockEntry  = stockEntryService.updateStockEntryById(id, stockEntry);
    return ResponseEntity.ok(updatedStockEntry);
  }

}
