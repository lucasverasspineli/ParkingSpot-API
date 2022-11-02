package br.com.lucas.api.parking.parkingcontrol.services;

import br.com.lucas.api.parking.parkingcontrol.models.ParkingSpotModel;
import br.com.lucas.api.parking.parkingcontrol.repositories.ParkingSpotRepository;
import br.com.lucas.api.parking.parkingcontrol.services.resourceinterfaces.ParkingSpotInterface;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

@Service
public class ParkingSpotService implements ParkingSpotInterface {
    @Autowired
    private ParkingSpotRepository parkingSpotRepository;

    @Override
    @Transactional
    public ParkingSpotModel save(ParkingSpotModel parkingModel){
        return parkingSpotRepository.save(parkingModel);
    }

    @Override
    public boolean existsByLicensePlateCar(String licensePlateCar) {
        return parkingSpotRepository.existsByLicensePlateCar(licensePlateCar);
    }

    @Override
    public boolean existsByParkingSpotNumber(String number) {
        return parkingSpotRepository.existsByParkingSpotNumber(number);
    }

    @Override
    public boolean existsByApartmentAndBlock(String ap, String bl) {
        return parkingSpotRepository.existsByApartmentAndBlock(ap , bl);
    }

   @Override
    public Page<ParkingSpotModel> findAll(Pageable pageable){
        return parkingSpotRepository.findAll(pageable);
    }

    @Override
    public Optional<ParkingSpotModel> findById(UUID id) {
        return parkingSpotRepository.findById(id);
    }

    @Override
    @Transactional
    public void deleteParkingSpot(UUID id){parkingSpotRepository.deleteById(id);}
}
