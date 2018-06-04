package HouseIt.config;

import HouseIt.dal.*;
import HouseIt.dal.impl.*;
import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@PropertySource({"classpath:db.properties"})
@ComponentScans(value = {
        @ComponentScan("HouseIt.dal"),
        @ComponentScan("HouseIt.service")
})
public class ApplicationConfig {

    @Autowired
    private Environment env;

    public ApplicationConfig() {
        super();
    }

    // To-do: Add service beans

    @Bean
    public IApartmentDao apartmentDao() {
        return new ApartmentDao();
    }

    @Bean
    public IBuildingDao buildingDao() {
        return new BuildingDao();
    }

    @Bean
    public ICaseDao caseDao() {
        return new CaseDao();
    }

    @Bean
    public ICaseMessageDao caseMessageDao() {
        return new CaseMessageDao();
    }

    @Bean
    public IManagerDao managerDao() {
        return new ManagerDao();
    }

    @Bean
    public ITenantDao tenantDao() {
        return new TenantDao();
    }

    @Bean
    public IUserDao userDao() {
        return new UserDao();
    }

    @Bean
    public LocalSessionFactoryBean getSessionFactory() {
        LocalSessionFactoryBean fb = new LocalSessionFactoryBean();
        fb.setDataSource(getDataSource());
        fb.setHibernateProperties(getHibernateProperties());
        return fb;
    }

    @Bean
    public DataSource getDataSource() {
        BasicDataSource ds = new BasicDataSource();
        ds.setDriverClassName(env.getProperty("jdbc.driverClassName"));
        ds.setUrl(env.getProperty("jdbc.url"));
        ds.setUsername(env.getProperty("jdbc.user"));
        ds.setPassword(env.getProperty("jdbc.pass"));
        return ds;
    }

    private Properties getHibernateProperties() {
        Properties properties = new Properties();
        properties.setProperty("hibernate.dialect", env.getProperty("hibernate.dialect"));
        properties.setProperty("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));
        properties.setProperty("hibernate.show_sql", env.getProperty("hibernate.show_sql"));
        properties.setProperty("hibernate.format_sql", env.getProperty("hibernate.format_sql"));
        return properties;
    }

}
