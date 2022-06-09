package com.cumulocity.pkiintegration.rest;

import javax.validation.constraints.NotBlank;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cumulocity.pkiintegration.service.OperationBuidingService;
import com.cumulocity.pkiintegration.service.OperationTrackingService;
import com.cumulocity.rest.representation.operation.OperationRepresentation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@RequestMapping("/operations")
@Slf4j
public class OperationResource {
	
	private final OperationTrackingService operationTrackingService;
	
	@PostMapping("/createkeys")
	public ResponseEntity<?> createKeys(@RequestBody @NotBlank String deviceId) {
		OperationRepresentation operation = OperationBuidingService.createKeyPairCreationOpeation(deviceId);
		operationTrackingService.createOperation(operation);
		return ResponseEntity.status(HttpStatus.OK).body(null);
	}
	
}
