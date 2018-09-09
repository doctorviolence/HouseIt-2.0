package HouseIt.service.impl;

import HouseIt.dal.*;
import HouseIt.exception.FileUploadException;
import HouseIt.exception.MyEntityNotFoundException;
import HouseIt.entities.*;
import HouseIt.service.IBuildingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.*;
import java.util.Arrays;
import java.util.List;

@Service("buildingService")
@Transactional
public class BuildingServiceImpl implements IBuildingService {

    @Autowired
    private IBuildingDao buildingDao;

    @Value("${file.upload.url}")
    private String url;

    public List<Building> getBuildings() {
        return buildingDao.getEntities(Building.class);
    }

    public Building findBuilding(long buildingId) throws MyEntityNotFoundException {
        Building b = buildingDao.findEntityById(Building.class, buildingId);
        if (b == null) {
            throw new MyEntityNotFoundException(String.format("Building with ID %s not found.", buildingId));
        }
        return b;
    }

    public Building createBuilding(Building building) {
        return buildingDao.createBuilding(building);
    }

    public void updateBuilding(Building building) throws MyEntityNotFoundException {
        Building b = findBuilding(building.getBuildingId());
        if (b != null) {
            buildingDao.updateEntity(building);
        }
    }

    public void deleteBuilding(long id) throws MyEntityNotFoundException {
        Building b = findBuilding(id);
        if (b != null) {
            buildingDao.deleteEntity(Building.class, id);
        }
    }

    public void uploadImage(MultipartFile file) throws FileUploadException {
        String[] fileTypesAllowed = {"image/jpg", "image/jpeg", "image/png"};

        if (!file.isEmpty()) {
            String fileName = file.getOriginalFilename();
            if (Arrays.stream(fileTypesAllowed).parallel().anyMatch(file.getContentType()::contains)) {
                double fileSize = file.getSize();
                double megaBytes = (fileSize / 1024) / 1024;
                if (megaBytes < 2) {
                    try {
                        byte[] fileBytes = file.getBytes();
                        OutputStream stream = new FileOutputStream(new File(url + fileName + ".png"));
                        stream.write(fileBytes);
                        stream.close();
                    } catch (IOException e) {
                        throw new FileUploadException("Failed...", e);
                    }
                }
            } else {
                throw new FileUploadException("The file you're trying to upload is not allowed...");
            }
        }
    }

}