package HouseIt.service.impl;

import HouseIt.dal.IApartmentDao;
import HouseIt.exception.HouseItServiceException;
import HouseIt.model.Apartment;
import HouseIt.service.IApartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("apartmentService")
public class ApartmentServiceImpl implements IApartmentService {

    private IApartmentDao apartmentDao;

    @Autowired
    public ApartmentServiceImpl(IApartmentDao apartmentDao) {
        this.apartmentDao = apartmentDao;
    }

    public List<Apartment> getApartments() throws HouseItServiceException {
        try {
            return apartmentDao.getEntities(Apartment.class);
        } catch (Exception e) {
            throw new HouseItServiceException("Could not get apartments.", e);
        }
    }

    public List<Apartment> getApartmentsInBuilding(long buildingId) throws HouseItServiceException {
        try {
            return apartmentDao.getApartmentsInBuilding(buildingId);
        } catch (Exception e) {
            throw new HouseItServiceException("Could not get the building's apartments.", e);
        }
    }

    public Apartment findApartment(Apartment apartment) throws HouseItServiceException {
        try {
            long id = apartment.getApartmentId();
            return apartmentDao.findEntityById(Apartment.class, id);
        } catch (Exception e) {
            throw new HouseItServiceException("Could not find apartment.", e);
        }
    }

    public boolean createApartment(Apartment apartment) throws HouseItServiceException {
        boolean inserted = false;
        try {
            apartmentDao.createEntity(apartment);
            inserted = true;
        } catch (Exception e) {
            throw new HouseItServiceException("Failed to add apartment.", e);
        }
        return inserted;
    }

    public boolean updateApartment(Apartment apartment) throws HouseItServiceException {
        boolean updated = false;
        try {
            apartmentDao.updateEntity(apartment);
            updated = true;
        } catch (Exception e) {
            throw new HouseItServiceException("Unable to update apartment.", e);
        }
        return updated;
    }

    public boolean deleteApartment(Apartment apartment) throws HouseItServiceException {
        try {
            Apartment a = findApartment(apartment);
            if (a != null) {
                long id = a.getApartmentId();
                apartmentDao.deleteEntity(Apartment.class, id);
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            throw new HouseItServiceException("Failed to delete apartment.", e);
        }
    }

}
