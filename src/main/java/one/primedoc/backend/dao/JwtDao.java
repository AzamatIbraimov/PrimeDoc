package one.primedoc.backend.dao;

import one.primedoc.backend.enums.Role;
import one.primedoc.backend.model.ClientShortModel;
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
public class JwtDao {
    private final DataSource dataSource;

    @Autowired
    public JwtDao(ApplicationContext applicationContext, DataSource dataSource) {
        this.dataSource = dataSource;
        AutowireCapableBeanFactory autowireCapableBeanFactory = applicationContext.getAutowireCapableBeanFactory();
        if (autowireCapableBeanFactory.containsBean("jdbcDataSource")) {
            dataSource = (DataSource) autowireCapableBeanFactory.getBean("jdbcDataSource");
        } else {
            dataSource = (DataSource) autowireCapableBeanFactory.getBean("dataSource");
        }
    }

    public Long getIdByRole(Long id, Role role) {
        Connection connection = null;
        Statement stmt = null;
        String prepared = role.equals(Role.CUSTOMER) ? "FROM doctor AS ob " : role.equals(Role.USER) ? "FROM client AS ob " : ""  ;
        if (prepared.isEmpty()) return id;
        try {
            connection = this.dataSource.getConnection();
            stmt = connection.createStatement();
            String sql = "SELECT ob.id " + prepared +
                    "WHERE ob.user_id = ";
            ResultSet resultSet = stmt.executeQuery(sql + id);

            if (resultSet.next()) {
                return resultSet.getLong(1);
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
        return id;
    }


}
