package com.felix.basic_projects.mini_market.exception;

import com.felix.basic_projects.mini_market.exception.customer.CustomerNotFoundException;
import com.felix.basic_projects.mini_market.exception.customer.DuplicateCustomerException;
import com.felix.basic_projects.mini_market.exception.customer.InvalidCustomerDetailsException;
import com.felix.basic_projects.mini_market.exception.product.DuplicateProductException;
import com.felix.basic_projects.mini_market.exception.product.InvalidStockQuantityException;
import com.felix.basic_projects.mini_market.exception.product.ProductNotFoundException;
import com.felix.basic_projects.mini_market.exception.sales_report.EmptySalesReportException;
import com.felix.basic_projects.mini_market.exception.sales_report.InvalidSalesReportDateRangeException;
import com.felix.basic_projects.mini_market.exception.stock_entry.DuplicateStockEntryException;
import com.felix.basic_projects.mini_market.exception.stock_entry.StockEntryNotFoundException;
import com.felix.basic_projects.mini_market.exception.transaction.DuplicateTransactionException;
import com.felix.basic_projects.mini_market.exception.transaction.TransactionNotFoundException;
import com.felix.basic_projects.mini_market.exception.user.UserAlreadyExistException;
import com.felix.basic_projects.mini_market.exception.user.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> handleGenericException(Exception ex, WebRequest request) {
    ErrorResponse errorResponse = new ErrorResponse(
      HttpStatus.BAD_REQUEST.value(),
      LocalDateTime.now(),
      ex.getClass().getSimpleName(),
      ex.getMessage(),
      request.getDescription(false).replace("uri=", "")
    );

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
  }


  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex, WebRequest request) {
    // Getting all validation error messages
    List<String> errorMessages = ex.getBindingResult()
      .getFieldErrors()
      .stream()
      .map(error -> error.getField() + ": " + error.getDefaultMessage())
      .toList();

    ErrorResponse errorResponse = new ErrorResponse(
      HttpStatus.BAD_REQUEST.value(),
      LocalDateTime.now(),
      "Validation Exception",
      String.join(", ", errorMessages),
      request.getDescription(false).replace("uri=", "")
    );

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
  }

  // -------------------------------------------------------------
  // ------ -> User Exception Section <- -------------------------
  // -------------------------------------------------------------

  @ExceptionHandler(UserNotFoundException.class)
  public ResponseEntity<ErrorResponse> handleUserNotFound(UserNotFoundException ex, WebRequest request) {
    ErrorResponse errorResponse = new ErrorResponse(
      HttpStatus.NOT_FOUND.value(),
      LocalDateTime.now(),
      ex.getClass().getSimpleName(),
      ex.getMessage(),
      request.getDescription(false).replace("uri=", "")
    );

    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
  }

  @ExceptionHandler(UserAlreadyExistException.class)
  public ResponseEntity<ErrorResponse> handleUserAlreadyExists(UserAlreadyExistException ex, WebRequest request) {
    ErrorResponse errorResponse = new ErrorResponse(
      HttpStatus.CONFLICT.value(),
      LocalDateTime.now(),
      ex.getClass().getSimpleName(),
      ex.getMessage(),
      request.getDescription(false).replace("uri=", "")
    );

    return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
  }

  // -------------------------------------------------------------
  // ------ -> Customer Exception Section <- ---------------------
  // -------------------------------------------------------------

  @ExceptionHandler(CustomerNotFoundException.class)
  public ResponseEntity<ErrorResponse> handleCustomerNotFound(CustomerNotFoundException ex, WebRequest request) {
    ErrorResponse errorResponse = new ErrorResponse(
      HttpStatus.NOT_FOUND.value(),
      LocalDateTime.now(),
      ex.getClass().getSimpleName(),
      ex.getMessage(),
      request.getDescription(false).replace("uri=", "")
    );

    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
  }

  @ExceptionHandler(DuplicateCustomerException.class)
  public ResponseEntity<ErrorResponse> handleDuplicateCustomer(DuplicateCustomerException ex, WebRequest request) {
    ErrorResponse errorResponse = new ErrorResponse(
      HttpStatus.CONFLICT.value(),
      LocalDateTime.now(),
      ex.getClass().getSimpleName(),
      ex.getMessage(),
      request.getDescription(false).replace("uri=", "")
    );

    return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
  }

  @ExceptionHandler(InvalidCustomerDetailsException.class)
  public ResponseEntity<ErrorResponse> handleInvalidCustomerDetails(InvalidCustomerDetailsException ex, WebRequest request) {
    ErrorResponse errorResponse = new ErrorResponse(
      HttpStatus.BAD_REQUEST.value(),
      LocalDateTime.now(),
      ex.getClass().getSimpleName(),
      ex.getMessage(),
      request.getDescription(false).replace("uri=", "")
    );

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
  }

  // -------------------------------------------------------------
  // ------ -> Product Exception Section <- ----------------------
  // -------------------------------------------------------------

  @ExceptionHandler(ProductNotFoundException.class)
  public ResponseEntity<ErrorResponse> handleProductNotFound(ProductNotFoundException ex, WebRequest request) {
    ErrorResponse errorResponse = new ErrorResponse(
      HttpStatus.NOT_FOUND.value(),
      LocalDateTime.now(),
      ex.getClass().getSimpleName(),
      ex.getMessage(),
      request.getDescription(false).replace("uri=", "")
    );

    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
  }

  @ExceptionHandler(DuplicateProductException.class)
  public ResponseEntity<ErrorResponse> handleDuplicateProduct(DuplicateProductException ex, WebRequest request) {
    ErrorResponse errorResponse = new ErrorResponse(
      HttpStatus.CONFLICT.value(),
      LocalDateTime.now(),
      ex.getClass().getSimpleName(),
      ex.getMessage(),
      request.getDescription(false).replace("uri=", "")
    );

    return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
  }

  @ExceptionHandler(InvalidStockQuantityException.class)
  public ResponseEntity<ErrorResponse> handleInvalidStockQuantity(InvalidStockQuantityException ex, WebRequest request) {
    ErrorResponse errorResponse = new ErrorResponse(
      HttpStatus.UNPROCESSABLE_ENTITY.value(),
      LocalDateTime.now(),
      ex.getClass().getSimpleName(),
      ex.getMessage(),
      request.getDescription(false).replace("uri=", "")
    );

    return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(errorResponse);
  }

  // -------------------------------------------------------------
  // ------ -> Transaction Exception Section <- ------------------
  // -------------------------------------------------------------

  @ExceptionHandler(TransactionNotFoundException.class)
  public ResponseEntity<ErrorResponse> handleTransactionNotFound(TransactionNotFoundException ex, WebRequest request) {
    ErrorResponse errorResponse = new ErrorResponse(
      HttpStatus.NOT_FOUND.value(),
      LocalDateTime.now(),
      ex.getClass().getSimpleName(),
      ex.getMessage(),
      request.getDescription(false).replace("uri=", "")
    );

    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
  }

  @ExceptionHandler(DuplicateTransactionException.class)
  public ResponseEntity<ErrorResponse> handleDuplicateTransaction(DuplicateTransactionException ex, WebRequest request) {
    ErrorResponse errorResponse = new ErrorResponse(
      HttpStatus.CONFLICT.value(),
      LocalDateTime.now(),
      ex.getClass().getSimpleName(),
      ex.getMessage(),
      request.getDescription(false).replace("uri=", "")
    );

    return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
  }

  // -------------------------------------------------------------
  // ------ -> Transaction Item Exception Section <- -------------
  // -------------------------------------------------------------



  // -------------------------------------------------------------
  // ------ -> Stock Entry Exception Section <- ------------------
  // -------------------------------------------------------------

  @ExceptionHandler(StockEntryNotFoundException.class)
  public ResponseEntity<ErrorResponse> handleStockEntryNotFound(StockEntryNotFoundException ex, WebRequest request) {
    ErrorResponse errorResponse = new ErrorResponse(
      HttpStatus.NOT_FOUND.value(),
      LocalDateTime.now(),
      ex.getClass().getSimpleName(),
      ex.getMessage(),
      request.getDescription(false).replace("uri=", "")
    );

    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
  }

  @ExceptionHandler(DuplicateStockEntryException.class)
  public ResponseEntity<ErrorResponse> handleDuplicateStockEntry(DuplicateStockEntryException ex, WebRequest request) {
    ErrorResponse errorResponse = new ErrorResponse(
      HttpStatus.CONFLICT.value(),
      LocalDateTime.now(),
      ex.getClass().getSimpleName(),
      ex.getMessage(),
      request.getDescription(false).replace("uri=", "")
    );

    return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
  }

  // -------------------------------------------------------------
  // ------ -> Sales Report Exception Section <- -----------------
  // -------------------------------------------------------------

  @ExceptionHandler(EmptySalesReportException.class)
  public ResponseEntity<ErrorResponse> handleEmptySales(EmptySalesReportException ex, WebRequest request) {
    ErrorResponse errorResponse = new ErrorResponse(
      HttpStatus.NOT_FOUND.value(),
      LocalDateTime.now(),
      ex.getClass().getSimpleName(),
      ex.getMessage(),
      request.getDescription(false).replace("uri=", "")
    );

    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
  }

  @ExceptionHandler(InvalidSalesReportDateRangeException.class)
  public ResponseEntity<ErrorResponse> handleInvalidDateRange(InvalidSalesReportDateRangeException ex, WebRequest request) {
    ErrorResponse errorResponse = new ErrorResponse(
      HttpStatus.UNPROCESSABLE_ENTITY.value(),
      LocalDateTime.now(),
      ex.getClass().getSimpleName(),
      ex.getMessage(),
      request.getDescription(false).replace("uri=", "")
    );

    return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(errorResponse);
  }
}
