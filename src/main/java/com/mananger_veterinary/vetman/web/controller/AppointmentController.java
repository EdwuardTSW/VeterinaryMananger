package com.mananger_veterinary.vetman.web.controller;

import com.mananger_veterinary.vetman.domain.Appointment;
import com.mananger_veterinary.vetman.domain.service.AppointmentService;
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
        name = "Appointments",
        description = "Endpoints para registrar, consultar, buscar y eliminar citas médicas."
)
@RestController
@RequestMapping("/appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @GetMapping
    @Operation(
            summary = "Listar citas médicas",
            description = "Obtiene todas las citas médicas registradas."
    )
    @ApiResponse(responseCode = "200", description = "Lista de citas obtenida correctamente")
    public ResponseEntity<List<Appointment>> findAll() {
        return ResponseEntity.ok(appointmentService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Buscar cita por ID",
            description = "Obtiene la información de una cita usando su identificador."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cita encontrada"),
            @ApiResponse(responseCode = "404", description = "Cita no encontrada")
    })
    public ResponseEntity<Appointment> findById(
            @Parameter(description = "ID de la cita", example = "1")
            @PathVariable Integer id
    ) {
        return appointmentService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/search")
    @Operation(
            summary = "Buscar citas por motivo",
            description = "Busca citas cuyo motivo contenga el texto indicado."
    )
    @ApiResponse(responseCode = "200", description = "Búsqueda realizada correctamente")
    public ResponseEntity<List<Appointment>> findByReason(
            @Parameter(description = "Motivo o parte del motivo de la cita", example = "Vacunación")
            @RequestParam String reason
    ) {
        return ResponseEntity.ok(appointmentService.findByReasonContaining(reason));
    }

    @PostMapping
    @Operation(
            summary = "Registrar una cita médica",
            description = "Crea una cita y la relaciona con una mascota existente.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(
                            examples = @ExampleObject(
                                    name = "Nueva cita",
                                    value = """
                                            {
                                              "appointmentDate": "2026-08-01T10:30:00",
                                              "reason": "Vacunación anual",
                                              "pet": {
                                                "id": 5
                                              }
                                            }
                                            """
                            )
                    )
            )
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Cita creada correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos de la cita inválidos")
    })
    public ResponseEntity<Appointment> save(@RequestBody Appointment appointment) {
        Appointment savedAppointment = appointmentService.save(appointment);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedAppointment);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Eliminar cita médica",
            description = "Elimina una cita usando su identificador."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Cita eliminada correctamente"),
            @ApiResponse(responseCode = "404", description = "Cita no encontrada")
    })
    public ResponseEntity<Void> deleteById(
            @Parameter(description = "ID de la cita", example = "1")
            @PathVariable Integer id
    ) {
        if (!appointmentService.deleteById(id)) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}