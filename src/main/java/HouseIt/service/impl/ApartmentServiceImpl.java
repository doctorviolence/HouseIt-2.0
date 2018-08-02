package HouseIt.service.impl;

import HouseIt.dal.IApartmentDao;
import HouseIt.entities.Apartment;
import HouseIt.exception.MyEntityNotFoundException;
import HouseIt.service.IApartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service("apartmentService")
@Transactional
public class ApartmentServiceImpl implements IApartmentService {

    @Autowired
    private IApartmentDao apartmentDao;

    public List<Apartment> getAllApartments() {
        return apartmentDao.getEntities(Apartment.class);
    }

    public List<Apartment> getApartmentsInBuilding(long buildingId) {
        return apartmentDao.getApartmentsInBuilding(buildingId);
    }

    public Apartment findApartment(long apartmentId) throws MyEntityNotFoundException {
        Apartment a = apartmentDao.findEntityById(Apartment.class, apartmentId);
        if (a == null) {
            throw new MyEntityNotFoundException(String.format("Apartment with ID %s not found.", apartmentId));
        }
        return a;
    }

    public void createApartment(Apartment apartment) {
        apartmentDao.createEntity(apartment);
    }

    public void updateApartment(Apartment apartment) throws MyEntityNotFoundException {
        Apartment a = findApartment(apartment.getApartmentId());
        if (a != null) {
            apartmentDao.updateEntity(apartment);
        }
    }

    public void deleteApartment(long id) throws MyEntityNotFoundException {
        Apartment a = findApartment(id);
        if (a != null) {
            apartmentDao.deleteEntity(Apartment.class, id);
        }
    }

}