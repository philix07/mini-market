package com.felix.basic_projects.mini_market.controller;

import com.felix.basic_projects.mini_market.model.dto.request.CreateTransactionRequestDTO;
import com.felix.basic_projects.mini_market.model.dto.request.UpdateTransactionRequestDTO;
import com.felix.basic_projects.mini_market.model.dto.response.TransactionResponseDTO;
import com.felix.basic_projects.mini_market.model.entity.Transaction;
import com.felix.basic_projects.mini_market.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/v1")
public class TransactionController {

  private final TransactionService service;

  public TransactionController(TransactionService service) {
    this.service = service;
  }

  @GetMapping("transactions")
  ResponseEntity<List<TransactionResponseDTO>> retrieveAllTransaction() {
    return ResponseEntity.ok(service.retrieveAllTransaction());
  }

  @GetMapping("transactions/{id}")
  ResponseEntity<TransactionResponseDTO> findTransactionById(@PathVariable Long id) {
    return ResponseEntity.ok(service.findTransactionById(id));
  }

  @PostMapping("transactions")
  ResponseEntity<TransactionResponseDTO> saveTransaction(@Valid @RequestBody CreateTransactionRequestDTO transaction) {
    TransactionResponseDTO newTransaction = service.saveTransaction(transaction);

    URI location = ServletUriComponentsBuilder.fromCurrentRequest()
      .path("/{id}")
      .buildAndExpand(newTransaction.getId())
      .toUri();

    return ResponseEntity.created(location).body(newTransaction);
  }

  @DeleteMapping("transactions/{id}")
  ResponseEntity<TransactionResponseDTO> deleteTransactionById(@PathVariable Long id) {
    return ResponseEntity.ok(service.deleteTransactionById(id));
  }

  @PatchMapping("transactions/{id}")
  ResponseEntity<TransactionResponseDTO> updateTransactionById(
    @PathVariable Long id,
    @Valid @RequestBody UpdateTransactionRequestDTO transaction
  ) {
    return ResponseEntity.ok(service.updateTransactionById(id, transaction));
  }
}
