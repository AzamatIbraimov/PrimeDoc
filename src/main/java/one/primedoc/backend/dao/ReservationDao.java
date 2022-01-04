package one.primedoc.backend.dao;

import one.primedoc.backend.model.ReservationClientModel;
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
public class ReservationDao {
    private final DataSource dataSource;

    @Autowired
    public ReservationDao(ApplicationContext applicationContext, DataSource dataSource) {
        this.dataSource = dataSource;
        AutowireCapableBeanFactory autowireCapableBeanFactory = applicationContext.getAutowireCapableBeanFactory();
        if (autowireCapableBeanFactory.containsBean("jdbcDataSource")) {
            dataSource = (DataSource) autowireCapableBeanFactory.getBean("jdbcDataSource");
        } else {
            dataSource = (DataSource) autowireCapableBeanFactory.getBean("dataSource");
        }
    }

    public List<ReservationClientModel> getReservationByWorkTimeId(Long id) {
        List<ReservationClientModel> reservation = new ArrayList<ReservationClientModel>();
        Connection connection = null;
        Statement stmt = null;
        try {
            connection = this.dataSource.getConnection();
            stmt = connection.createStatement();
            String sql = "SELECT  r.id, r.comment, r.phone_number, r.start_time, r.end_time,  r.client_id, u.firstname, u.lastname, u.patronymic " +
                    "FROM reservation AS r " +
                    "JOIN client AS c ON c.id = r.client_id " +
                    "JOIN users AS u ON u.id = c.user_id " +
                    "WHERE r.is_paid is true AND r.work_time_id = ";
            ResultSet resultSet = stmt.executeQuery(sql + id);

            if (resultSet.next()) {
                do {
                    reservation.add(
                            ReservationClientModel.builder()
                                    .id(resultSet.getLong(1))
                                    .comment(resultSet.getString(2))
                                    .phoneNumber(resultSet.getString(3))
                                    .start(resultSet.getTime(4))
                                    .end(resultSet.getTime(5))
                                    .client(ClientShortModel.builder()
                                            .id(resultSet.getLong(6))
                                            .firstname(resultSet.getString(7))
                                            .lastname(resultSet.getString(8))
                                            .patronymic(resultSet.getString(9))
                                            .build())
                                    .build());
                } while (resultSet.next());
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
        return reservation;
    }

    public String getReservationTime(Long id) {
        String notifyDate = "";
        Connection connection = null;
        Statement stmt = null;
        try {
            connection = this.dataSource.getConnection();
            String sql = "SELECT to_char(wt.start_time, 'YYYY MM DD') as reservation_date, to_char(r.start_time - INTERVAL '10 min', 'HH24:MI:SS') as reservation_time " +
                    "FROM reservation AS r " +
                    "JOIN work_time AS wt ON wt.id = r.work_time_id " +
                    "WHERE r.id = ";
            stmt = connection.createStatement();
            ResultSet resultSet = stmt.executeQuery(sql + id);

            if (resultSet.next()) {
                notifyDate = resultSet.getString(1) + " " + resultSet.getString(2);
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
        return notifyDate;
    }

    public List<ReservationClientModel> getAllReservationByWorkTimeId(Long id) {
        List<ReservationClientModel> reservation = new ArrayList<ReservationClientModel>();
        Connection connection = null;
        Statement stmt = null;
        try {
            connection = this.dataSource.getConnection();
            stmt = connection.createStatement();
            String sql = "SELECT r.id, u.firstname, u.lastname, u.patronymic, r.phone_number,  DATE(wt.start_time), r.start_time, r.end_time, r.is_paid FROM reservation r JOIN work_time wt ON wt.id = r.work_time_id JOIN client c on c.id = r.client_id JOIN users u on u.id = c.user_id";
            ResultSet resultSet = stmt.executeQuery(sql + id);

            if (resultSet.next()) {
                do {
                    reservation.add(
                            ReservationClientModel.builder()
                                    .id(resultSet.getLong(1))
                                    .comment(resultSet.getString(2))
                                    .phoneNumber(resultSet.getString(3))
                                    .start(resultSet.getTime(4))
                                    .end(resultSet.getTime(5))
                                    .client(ClientShortModel.builder()
                                            .id(resultSet.getLong(6))
                                            .firstname(resultSet.getString(7))
                                            .lastname(resultSet.getString(8))
                                            .patronymic(resultSet.getString(9))
                                            .build())
                                    .build());
                } while (resultSet.next());
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
        return reservation;
    }
}
