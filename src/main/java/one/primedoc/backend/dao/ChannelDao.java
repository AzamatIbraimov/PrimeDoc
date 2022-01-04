package one.primedoc.backend.dao;

import one.primedoc.backend.enums.Role;
import one.primedoc.backend.model.ClientShortModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public class ChannelDao {
    private final DataSource dataSource;
    private final Logger LOGGER = LoggerFactory.getLogger(ChannelDao.class);

    @Autowired
    public ChannelDao(ApplicationContext applicationContext, DataSource dataSource) {
        this.dataSource = dataSource;
        AutowireCapableBeanFactory autowireCapableBeanFactory = applicationContext.getAutowireCapableBeanFactory();
        if (autowireCapableBeanFactory.containsBean("jdbcDataSource")) {
            dataSource = (DataSource) autowireCapableBeanFactory.getBean("jdbcDataSource");
        } else {
            dataSource = (DataSource) autowireCapableBeanFactory.getBean("dataSource");
        }
    }

    public void createChannel(String sid, String clientIdentity, String doctorIdentity, Long clientId, Long doctorId) {
        Connection connection = null;
        Statement stmt = null;
        try {
            connection = this.dataSource.getConnection();
            stmt = connection.createStatement();
            String sql = "INSERT INTO channel VALUES (" +
                    " (SELECT nextval('channel_seq')), " +
                    "'" + new Timestamp(new Date().getTime()) + "', " +
                    "'" + new Timestamp(new Date().getTime()) + "', " +
                    " false, " +
                    "'" + clientIdentity                      + "', " +
                    "'" + doctorIdentity                      + "', " +
                    "'" + sid                                 + "', "
                        + clientId                      + " , "
                        + doctorId
                    + ")";
            System.out.println(sql);
            int resultSet = stmt.executeUpdate(sql);
            LOGGER.info("Amount of record: " + resultSet + " successfully created in Channel.");
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

    public void changeActiveStatus(String sid, Boolean status) {
        Connection connection = null;
        Statement stmt = null;
        try {
            connection = this.dataSource.getConnection();
            stmt = connection.createStatement();
            String sql = "UPDATE channel SET active = " + status + " WHERE sid = " + sid;
            System.out.println(sql);
            int resultSet = stmt.executeUpdate(sql);
            LOGGER.info("Amount of record: " + resultSet + " successfully changed in Channel.");
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

}