package HouseIt.service;

import HouseIt.exception.FileUploadException;
import HouseIt.exception.MyEntityNotFoundException;
import HouseIt.entities.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface IBuildingService {

    List<Building> getBuildings();

    Building findBuilding(long buildingId) throws MyEntityNotFoundException;

    Building createBuilding(Building building);

    void updateBuilding(Building building) throws MyEntityNotFoundException;

    void deleteBuilding(long buildingId) throws MyEntityNotFoundException;

    void uploadImage(MultipartFile file) throws FileUploadException;

}