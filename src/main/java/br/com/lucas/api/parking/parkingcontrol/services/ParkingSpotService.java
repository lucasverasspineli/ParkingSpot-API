package br.com.lucas.api.parking.parkingcontrol.services;

import br.com.lucas.api.parking.parkingcontrol.models.ParkingSpotModel;
import br.com.lucas.api.parking.parkingcontrol.repositories.ParkingSpotRepository;
import br.com.lucas.api.parking.parkingcontrol.services.resourceinterfaces.ParkingSpotInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class ParkingSpotService implements ParkingSpotInterface {
    @Autowired
    private ParkingSpotRepository parkingSpotRepository;

    @Override
    @Transactional
    public ParkingSpotModel save(ParkingSpotModel parkingModel){
        return parkingSpotRepository.save(parkingModel);
    }
}
