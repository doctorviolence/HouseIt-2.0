package HouseIt.config;

import HouseIt.dal.*;
import HouseIt.dal.impl.*;
import org.springframework.context.annotation.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@PropertySource("classpath:application-local.properties")
@ComponentScans(value = {
        @ComponentScan("HouseIt.dal"),
        @ComponentScan("HouseIt.model"),
        @ComponentScan("HouseIt.service"),
        @ComponentScan("HouseIt.service.impl"),
        @ComponentScan("HouseIt.controller"),
})
public class ApplicationConfig {

    public ApplicationConfig() {
        super();
    }

    @Bean
    public IApartmentDao apartmentDao() {
        return new ApartmentDaoImplImpl();
    }

    @Bean
    public IBuildingDao buildingDao() {
        return new BuildingDaoImpl();
    }

    @Bean
    public ICaseDao caseDao() {
        return new CaseDaoImpl();
    }

    @Bean
    public ICaseMessageDao caseMessageDao() {
        return new CaseMessageDaoImpl();
    }

    @Bean
    public IManagerDao managerDao() {
        return new ManagerDaoImpl();
    }

    @Bean
    public ITenantDao tenantDao() {
        return new TenantDaoImpl();
    }

    @Bean
    public IUserDao userDao() {
        return new UserDaoImpl();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

}