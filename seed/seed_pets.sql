CREATE EXTENSION IF NOT EXISTS pgcrypto;

DO $$
DECLARE
    current_pet_count INTEGER;
BEGIN
    SELECT COUNT(*) INTO current_pet_count FROM pets;

    IF current_pet_count < 10 THEN
        INSERT INTO owners (name, phone, email, password)
        SELECT seed_owner.name,
               seed_owner.phone,
               seed_owner.email,
               crypt('SeedPets2026!', gen_salt('bf', 12))
        FROM (
            VALUES
                ('Ana Torres', '5551000001', 'ana.torres.seed@veterinaria.local'),
                ('Bruno Salas', '5551000002', 'bruno.salas.seed@veterinaria.local'),
                ('Carla Reyes', '5551000003', 'carla.reyes.seed@veterinaria.local')
        ) AS seed_owner(name, phone, email)
        WHERE NOT EXISTS (
            SELECT 1
            FROM owners owner
            WHERE owner.email = seed_owner.email
        );

        INSERT INTO pets (name, species, breed, age, owner_id)
        SELECT seed_pet.name,
               seed_pet.species,
               seed_pet.breed,
               seed_pet.age,
               owner.id
        FROM (
            VALUES
                ('Seed Luna', 'Gato', 'Siamés', 2, 'ana.torres.seed@veterinaria.local'),
                ('Seed Rocky', 'Perro', 'Pastor Alemán', 5, 'bruno.salas.seed@veterinaria.local'),
                ('Seed Nube', 'Conejo', 'Mini Lop', 1, 'carla.reyes.seed@veterinaria.local'),
                ('Seed Kiwi', 'Ave', 'Periquito', 2, 'ana.torres.seed@veterinaria.local'),
                ('Seed Copito', 'Hámster', 'Sirio', 1, 'bruno.salas.seed@veterinaria.local'),
                ('Seed Maya', 'Perro', 'Golden Retriever', 4, 'carla.reyes.seed@veterinaria.local'),
                ('Seed Misha', 'Gato', 'Mestizo', 3, 'ana.torres.seed@veterinaria.local'),
                ('Seed Tito', 'Tortuga', 'Orejas Rojas', 6, 'bruno.salas.seed@veterinaria.local'),
                ('Seed Paco', 'Ave', 'Canario', 2, 'carla.reyes.seed@veterinaria.local'),
                ('Seed Lola', 'Perro', 'Beagle', 3, 'ana.torres.seed@veterinaria.local'),
                ('Seed Pelusa', 'Conejo', 'Angora', 2, 'bruno.salas.seed@veterinaria.local'),
                ('Seed Simba', 'Gato', 'Persa', 4, 'carla.reyes.seed@veterinaria.local')
        ) AS seed_pet(name, species, breed, age, owner_email)
        JOIN owners owner ON owner.email = seed_pet.owner_email
        WHERE NOT EXISTS (
            SELECT 1
            FROM pets pet
            WHERE pet.name = seed_pet.name
              AND pet.owner_id = owner.id
        )
        ORDER BY seed_pet.name
        LIMIT 12 - current_pet_count;
    END IF;
END $$;
