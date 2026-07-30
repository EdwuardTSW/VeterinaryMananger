package com.mananger_veterinary.vetman.web.controller;

import com.mananger_veterinary.vetman.domain.Pet;
import com.mananger_veterinary.vetman.domain.service.PetService;
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
import com.mananger_veterinary.vetman.domain.exception.DuplicatePetException;

import java.util.List;

@Tag(
        name = "Pets",
        description = "Endpoints para registrar, consultar, buscar y eliminar mascotas de la veterinaria."
)
@RestController
@RequestMapping("/pets")
public class PetController {

    private final PetService petService;

    public PetController(PetService petService) {
        this.petService = petService;
    }

    @GetMapping
    @Operation(
            summary = "Listar mascotas",
            description = ""
    )
    @ApiResponse(responseCode = "200", description = "Lista de mascotas obtenida correctamente")
    public ResponseEntity<List<Pet>> findAll() {
        return ResponseEntity.ok(petService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Buscar mascota por ID",
            description = ""
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Mascota encontrada"),
            @ApiResponse(responseCode = "404", description = "Mascota no encontrada")
    })
    public ResponseEntity<Pet> findById(
            @Parameter(description = "ID de la mascota", example = "5")
            @PathVariable Integer id
    ) {
        return petService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/search")
    @Operation(
            summary = "Buscar mascotas por nombre",
            description = ""
    )
    @ApiResponse(responseCode = "200", description = "Búsqueda realizada correctamente")
    public ResponseEntity<List<Pet>> findByName(
            @Parameter(description = "Nombre o parte del nombre de la mascota", example = "Simb")
            @RequestParam String name
    ) {
        return ResponseEntity.ok(petService.findByNameContaining(name));
    }

    @PostMapping
    @Operation(
            summary = "Registrar una mascota",
            description = "Crea una mascota y la relaciona con un dueño existente.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(
                            examples = @ExampleObject(
                                    name = "Nueva mascota",
                                    value = """
                                        {
                                          "name": "Max",
                                          "species": "Perro",
                                          "breed": "Labrador",
                                          "age": 3,
                                          "owner": {
                                            "id": 5
                                          }
                                        }
                                        """
                            )
                    )
            )
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Mascota creada correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos o dueño inexistente"),
            @ApiResponse(responseCode = "409", description = "El dueño ya tiene una mascota con ese nombre")
    })
    public ResponseEntity<Pet> save(@RequestBody Pet pet) {
        try {
            Pet savedPet = petService.save(pet);

            return ResponseEntity.status(HttpStatus.CREATED).body(savedPet);
        } catch (DuplicatePetException exception) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } catch (IllegalArgumentException exception) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Eliminar mascota",
            description = ""
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Mascota eliminada correctamente"),
            @ApiResponse(responseCode = "404", description = "Mascota no encontrada")
    })
    public ResponseEntity<Void> deleteById(
            @Parameter(description = "ID de la mascota", example = "5")
            @PathVariable Integer id
    ) {
        if (!petService.deleteById(id)) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}