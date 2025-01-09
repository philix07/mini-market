package com.felix.basic_projects.mini_market.controller;

import com.felix.basic_projects.mini_market.model.Transaction;
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

  @Autowired
  TransactionService service;

  @GetMapping("transactions")
  ResponseEntity<List<Transaction>> retrieveAllTransaction() {
    List<Transaction> transactions = service.retrieveAllTransaction();
    return ResponseEntity.ok(transactions);
  }

  @GetMapping("transactions/{id}")
  ResponseEntity<Transaction> findTransactionById(@PathVariable Long id) {
    Transaction transaction = service.findTransactionById(id);
    return ResponseEntity.ok(transaction);
  }

  @PostMapping(path = "transactions", consumes = "application/json")
  ResponseEntity<Transaction> saveTransaction(@Valid @RequestBody Transaction transaction) {
    Transaction newTransaction = service.saveTransaction(transaction);

    URI location = ServletUriComponentsBuilder.fromCurrentRequest()
      .path("/{id}")
      .buildAndExpand(newTransaction.getId())
      .toUri();

    return ResponseEntity.created(location).body(newTransaction);
  }

  @DeleteMapping("transactions/{id}")
  ResponseEntity<Transaction> deleteTransactionById(@PathVariable Long id) {
    Transaction deletedTransaction = service.deleteTransactionById(id);
    return ResponseEntity.ok(deletedTransaction);
  }

  @PatchMapping("transactions/{id}")
  ResponseEntity<Transaction> updateTransactionById(@PathVariable Long id, @Valid @RequestBody Transaction transaction) {
    Transaction updatedTransaction = service.updateTransactionById(id, transaction);
    return ResponseEntity.ok(updatedTransaction);
  }
}
