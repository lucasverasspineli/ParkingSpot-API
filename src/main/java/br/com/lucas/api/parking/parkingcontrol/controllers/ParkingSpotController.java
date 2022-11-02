package br.com.lucas.api.parking.parkingcontrol.controllers;

import br.com.lucas.api.parking.parkingcontrol.dtos.ParkingSpotDTO;
import br.com.lucas.api.parking.parkingcontrol.models.ParkingSpotModel;
import br.com.lucas.api.parking.parkingcontrol.services.ParkingSpotService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/parking-spot")
public class ParkingSpotController {

    @Autowired
    private ParkingSpotService parkingSpotService;

    @PostMapping
    public ResponseEntity<Object> saveParkingSpot(@RequestBody @Valid ParkingSpotDTO parkingDTO){
        if(parkingSpotService.existsByLicensePlateCar(parkingDTO.getLicensePlateCar())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict: License Plate Car is already in use!");
        }
        if(parkingSpotService.existsByParkingSpotNumber(parkingDTO.getParkingSpotNumber())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict: Parking Spot is already in use!");
        }
        if(parkingSpotService.existsByApartmentAndBlock(parkingDTO.getApartment(), parkingDTO.getBlock())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict: Parking Spot already registered for this apartment/block!");
        }
        ParkingSpotModel parkingSpotModel = new ParkingSpotModel();
        BeanUtils.copyProperties(parkingDTO, parkingSpotModel);
        parkingSpotModel.setRegistrationDate(LocalDateTime.now(ZoneId.of("UTC")));
        return ResponseEntity.status(HttpStatus.CREATED).body(parkingSpotService.save(parkingSpotModel));
    }

//    private List<ParkingSpotDTO> convertModelByDTO(List<ParkingSpotModel> listModel){
//        List<ParkingSpotDTO> lsDTO = new ArrayList<ParkingSpotDTO>();
//
//        for(ParkingSpotModel pkModel : listModel ) {
//            lsDTO.add(new ParkingSpotDTO(pkModel.getParkingSpotNumber(),pkModel.getLicensePlateCar(),
//                    pkModel.getBrandCar(),pkModel.getModelCar(),pkModel.getColorCar(),
//                    pkModel.getResponsibleName(),pkModel.getApartment(),pkModel.getBlock()));
//        }
//        return lsDTO;
//    }

//    @GetMapping
//    public ResponseEntity<List<ParkingSpotDTO>> listAll(){
//        List<ParkingSpotModel> lista = parkingSpotService.findAll();
//        return ResponseEntity.status(HttpStatus.OK).body(convertModelByDTO(lista));
//    }

    @GetMapping
    public ResponseEntity<Page<ParkingSpotModel>> getAllParkingSpot(@PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC)Pageable pageable){
        return ResponseEntity.status(HttpStatus.OK).body(parkingSpotService.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getByOneParkingSpot(@PathVariable(value = "id") UUID id){
        Optional<ParkingSpotModel> opModel = parkingSpotService.findById(id);
        if(!opModel.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Parking spot not found!");
        }
        return ResponseEntity.status(HttpStatus.OK).body(opModel.get());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteParkingSpot(@PathVariable(value = "id") UUID id){
        Optional<ParkingSpotModel> idParking = parkingSpotService.findById(id);
        if(!idParking.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Parking spot not found, bls verification whit calm");
        }
        parkingSpotService.deleteParkingSpot(idParking.get().getId());
        return ResponseEntity.status(HttpStatus.OK).body("Parking Spot deleted successfully");
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateParkingSpot(@PathVariable(value = "id") UUID id,
                                                    @RequestBody @Valid ParkingSpotDTO parkingSpotDTO){
        Optional<ParkingSpotModel> opModel = parkingSpotService.findById(id);
        if(!opModel.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Parking spot not found!");
        }
        ParkingSpotModel pkModel = new ParkingSpotModel();
//        ParkingSpotModel pkModel = opModel.get();
//        pkModel.setParkingSpotNumber(parkingSpotDTO.getParkingSpotNumber());
//        pkModel.setLicensePlateCar(parkingSpotDTO.getLicensePlateCar());
//        pkModel.setModelCar(parkingSpotDTO.getModelCar());
//        pkModel.setBrandCar(parkingSpotDTO.getBrandCar());
//        pkModel.setColorCar(parkingSpotDTO.getColorCar());
//        pkModel.setResponsibleName(parkingSpotDTO.getResponsibleName());
//        pkModel.setApartment(parkingSpotDTO.getApartment());
//        pkModel.setBlock(parkingSpotDTO.getBlock());
        BeanUtils.copyProperties(parkingSpotDTO, pkModel);
        pkModel.setId(opModel.get().getId());
        pkModel.setRegistrationDate(opModel.get().getRegistrationDate());
        return ResponseEntity.status(HttpStatus.OK).body(parkingSpotService.save(pkModel));

    }

}
