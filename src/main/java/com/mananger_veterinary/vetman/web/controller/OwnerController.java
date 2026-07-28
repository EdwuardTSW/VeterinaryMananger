package com.mananger_veterinary.vetman.web.controller;

import com.mananger_veterinary.vetman.domain.Owner;
import com.mananger_veterinary.vetman.domain.service.OwnerService;
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
        name = "Owners",
        description = "Endpoints para registrar, consultar, buscar y eliminar dueños de mascotas."
)
@RestController
@RequestMapping("/owners")
public class OwnerController {

    private final OwnerService ownerService;

    public OwnerController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @GetMapping
    @Operation(
            summary = "Listar dueños",
            description = ""
    )
    @ApiResponse(responseCode = "200", description = "Lista de dueños obtenida correctamente")
    public ResponseEntity<List<Owner>> findAll() {
        return ResponseEntity.ok(ownerService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Buscar dueño por ID",
            description = ""
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Dueño encontrado"),
            @ApiResponse(responseCode = "404", description = "Dueño no encontrado")
    })
    public ResponseEntity<Owner> findById(
            @Parameter(description = "ID del dueño", example = "5")
            @PathVariable Integer id
    ) {
        return ownerService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/search")
    @Operation(
            summary = "Buscar dueños por nombre",
            description = ""
    )
    @ApiResponse(responseCode = "200", description = "Búsqueda realizada correctamente")
    public ResponseEntity<List<Owner>> findByName(
            @Parameter(description = "Nombre o parte del nombre del dueño", example = "Carlos")
            @RequestParam String name
    ) {
        return ResponseEntity.ok(ownerService.findByNameContaining(name));
    }

    @PostMapping
    @Operation(
            summary = "Registrar un dueño",
            description = "",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(
                            examples = @ExampleObject(
                                    name = "Nuevo dueño",
                                    value = """
                                            {
                                              "name": "Carlos Medina",
                                              "phone": "9991234567",
                                              "email": "carlos@veterinaria.com"
                                            }
                                            """
                            )
                    )
            )
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Dueño creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos del dueño inválidos")
    })
    public ResponseEntity<Owner> save(@RequestBody Owner owner) {
        Owner savedOwner = ownerService.save(owner);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedOwner);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Eliminar dueño",
            description = ""
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Dueño eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Dueño no encontrado")
    })
    public ResponseEntity<Void> deleteById(
            @Parameter(description = "ID del dueño", example = "5")
            @PathVariable Integer id
    ) {
        if (!ownerService.deleteById(id)) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}