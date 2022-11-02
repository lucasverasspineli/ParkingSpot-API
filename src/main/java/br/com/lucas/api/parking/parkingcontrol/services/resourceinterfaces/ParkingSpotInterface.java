package br.com.lucas.api.parking.parkingcontrol.services.resourceinterfaces;

import br.com.lucas.api.parking.parkingcontrol.dtos.ParkingSpotDTO;
import br.com.lucas.api.parking.parkingcontrol.models.ParkingSpotModel;
import br.com.lucas.api.parking.parkingcontrol.services.ParkingSpotService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ParkingSpotInterface  {

    ParkingSpotModel save(ParkingSpotModel parkingModel);

    boolean existsByLicensePlateCar(String LicensePlateCar);

    boolean existsByParkingSpotNumber(String ParkingSpotNumber);

    boolean existsByApartmentAndBlock(String apartmen, String block);

    Page<ParkingSpotModel> findAll(Pageable pageable);

    Optional<ParkingSpotModel> findById(UUID id);

    void deleteParkingSpot(UUID id);

}
