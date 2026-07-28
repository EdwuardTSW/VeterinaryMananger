package com.mananger_veterinary.vetman.web.controller;

import com.mananger_veterinary.vetman.domain.Treatment;
import com.mananger_veterinary.vetman.domain.service.TreatmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(
        name = "Treatments",
        description = "Endpoints para registrar, consultar, buscar y eliminar tratamientos médicos."
)
@RestController
@RequestMapping("/treatments")
public class TreatmentController {

    private final TreatmentService treatmentService;

    public TreatmentController(TreatmentService treatmentService) {
        this.treatmentService = treatmentService;
    }

    @GetMapping
    @Operation(summary = "Listar tratamientos")
    @ApiResponse(responseCode = "200", description = "Lista de tratamientos obtenida correctamente")
    public ResponseEntity<List<Treatment>> findAll() {
        return ResponseEntity.ok(treatmentService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar tratamiento por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Tratamiento encontrado"),
            @ApiResponse(responseCode = "404", description = "Tratamiento no encontrado")
    })
    public ResponseEntity<Treatment> findById(
            @Parameter(description = "ID del tratamiento", example = "1")
            @PathVariable Integer id
    ) {
        return treatmentService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/search")
    @Operation(summary = "Buscar tratamientos por descripción")
    @ApiResponse(responseCode = "200", description = "Búsqueda realizada correctamente")
    public ResponseEntity<List<Treatment>> findByDescription(
            @Parameter(description = "Descripción o parte de la descripción", example = "Vacuna")
            @RequestParam String description
    ) {
        return ResponseEntity.ok(treatmentService.findByDescriptionContaining(description));
    }

    @PostMapping
    @Operation(
            summary = "Registrar un tratamiento",
            description = "Crea un tratamiento y lo relaciona con una cita existente.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(
                            examples = @ExampleObject(
                                    name = "Nuevo tratamiento",
                                    value = """
                                            {
                                              "description": "Aplicación de vacuna antirrábica",
                                              "cost": 350.00,
                                              "appointment": {
                                                "id": 1
                                              }
                                            }
                                            """
                            )
                    )
            )
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Tratamiento creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos del tratamiento inválidos")
    })
    public ResponseEntity<Treatment> save(@RequestBody Treatment treatment) {
        Treatment savedTreatment = treatmentService.save(treatment);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedTreatment);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar tratamiento")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Tratamiento eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Tratamiento no encontrado")
    })
    public ResponseEntity<Void> deleteById(
            @Parameter(description = "ID del tratamiento", example = "1")
            @PathVariable Integer id
    ) {
        if (!treatmentService.deleteById(id)) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}