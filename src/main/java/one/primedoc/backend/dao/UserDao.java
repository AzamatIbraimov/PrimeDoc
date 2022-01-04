package one.primedoc.backend.dao;

import one.primedoc.backend.enums.Role;
import one.primedoc.backend.exception.InvalidValueException;
import one.primedoc.backend.model.ClientShortModel;
import one.primedoc.backend.model.DoctorDataModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UserDao {
    private final DataSource dataSource;
    private final Logger LOGGER = LoggerFactory.getLogger(UserDao.class);

    @Autowired
    public UserDao(ApplicationContext applicationContext, DataSource dataSource) {
        this.dataSource = dataSource;
        AutowireCapableBeanFactory autowireCapableBeanFactory = applicationContext.getAutowireCapableBeanFactory();
        if (autowireCapableBeanFactory.containsBean("jdbcDataSource")) {
            dataSource = (DataSource) autowireCapableBeanFactory.getBean("jdbcDataSource");
        } else {
            dataSource = (DataSource) autowireCapableBeanFactory.getBean("dataSource");
        }
    }

    public void updateIdentity(Long id, String identity) {
        Connection connection = null;
        Statement stmt = null;
        try {
            connection = this.dataSource.getConnection();
            stmt = connection.createStatement();
            String sql = "UPDATE users SET identity = '" + identity + "' WHERE id = " + id;
            System.out.println(sql);
            int resultSet = stmt.executeUpdate(sql);
            LOGGER.info("User identity changed: {} with id: {}. Number of rows changed: {}", identity, id, resultSet);
        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            try {
                if (stmt != null || connection != null)
                    connection.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }

    public String getIdentityByIdAndRole(Long id, Role role) {
        Connection connection = null;
        Statement stmt = null;
        String identity = null;
        String prepared = role.equals(Role.CUSTOMER) ? " doctor ob" : role.equals(Role.USER) ? " client ob " : ""  ;
        if (prepared.isEmpty())
            throw new InvalidValueException("(UserDao) Invalid value for role argument: " + role);
        try {
            connection = this.dataSource.getConnection();
            stmt = connection.createStatement();
            String sql = "SELECT u.identity FROM " + prepared +
                    " JOIN users AS u ON u.id = ob.user_id WHERE ob.id = ";
            ResultSet resultSet = stmt.executeQuery(sql + id);

            if (resultSet.next()) {
                identity = resultSet.getString("identity");
            } else {
                System.out.println("result set is empty");
            }
            resultSet.close();
        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            try {
                if (stmt != null || connection != null)
                    connection.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        return identity;
    }


}
