package one.primedoc.backend.dao;


import one.primedoc.backend.enums.InfoType;
import one.primedoc.backend.exception.RecordNotFoundException;
import one.primedoc.backend.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.*;

@Repository
public class DoctorDao {
    private final DataSource dataSource;

    @Autowired
    public DoctorDao(ApplicationContext applicationContext, DataSource dataSource) {
        this.dataSource = dataSource;
        AutowireCapableBeanFactory autowireCapableBeanFactory = applicationContext.getAutowireCapableBeanFactory();
        if (autowireCapableBeanFactory.containsBean("jdbcDataSource")) {
            dataSource = (DataSource) autowireCapableBeanFactory.getBean("jdbcDataSource");
        } else {
            dataSource = (DataSource) autowireCapableBeanFactory.getBean("dataSource");
        }
    }

    public List<DoctorPersonalInfoModel> getAllDoctorsData() {
        List<DoctorPersonalInfoModel> doctorList = new ArrayList<DoctorPersonalInfoModel>();
        Set<Long> ids = new HashSet<>();
        Connection connection = null;
        Statement stmt = null;
        try {
            connection = this.dataSource.getConnection();
            stmt = connection.createStatement();
            String sql = "SELECT d.id, u.username, u.firstname, u.lastname, u.patronymic, d.position, c.id, c.name " +
                    "FROM users AS u " +
                    "         JOIN doctor d on u.id = d.user_id " +
                    "        LEFT OUTER JOIN doctor_category dc ON d.id = dc.doctor_id " +
                    "        LEFT OUTER JOIN category c on c.id = dc.category_id ";
            ResultSet resultSet = stmt.executeQuery(sql);

            if (resultSet.next()) {
                while (resultSet.next()) {
                    DoctorPersonalInfoModel doctorDataModel = DoctorPersonalInfoModel.builder()
                            .id(resultSet.getLong(1))
                            .username(resultSet.getString(2))
                            .firstName(resultSet.getString(3))
                            .lastName(resultSet.getString(4))
                            .patronymic(resultSet.getString(5))
                            .position(resultSet.getString(6)).build();
                    if (ids.contains(doctorDataModel.getId())) {
                        DoctorPersonalInfoModel doctor = doctorList.get(doctorList.size() - 1);
                        if (doctor.getCategories() == null) {
                            doctor.setCategories(new ArrayList<CategoryNameModel>());
                        }
                        doctor.getCategories()
                                .add(CategoryNameModel.builder().id(resultSet.getLong(7)).name(resultSet.getString(8)).build());
                    } else {
                        String determinant = resultSet.getString(8);
                        doctorDataModel.setCategories(new ArrayList<CategoryNameModel>());
                        if (!resultSet.wasNull()) {
                            doctorDataModel.getCategories()
                                    .add(CategoryNameModel.builder()
                                            .id(resultSet.getLong(7))
                                            .name(resultSet.getString(8)).build());
                        }
                        doctorList.add(doctorDataModel);
                        ids.add(doctorDataModel.getId());
                    }
                    System.out.println(doctorList);
                }
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
        return doctorList;
    }

    public DoctorDetailsModel getDoctorDetails(Long id) {
        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = this.dataSource.getConnection();
            stmt = connection.prepareStatement(
                    "SELECT d.id," +
                            "       u.username," +
                            "       u.firstname," +
                            "       u.lastname," +
                            "       u.patronymic," +
                            "       d.position," +
                            "       d.user_id," +
                            "       d.image," +
                            "       di.name," +
                            "       di.organization_name," +
                            "       di.info_type," +
                            "       di.start_date," +
                            "       di.end_date " +
                            "FROM users AS u" +
                            "    JOIN doctor d on u.id = d.user_id" +
                            "    JOIN doctor_info di on d.id = di.doctor_id WHERE d.id = ?");
            stmt.setInt(1, id.intValue());

            ResultSet resultSet = stmt.executeQuery();

            if (resultSet.next()) {
                DoctorDetailsModel doctorDataModel = DoctorDetailsModel.builder()
                        .id(resultSet.getLong("id"))
                        .username(resultSet.getString("username"))
                        .firstName(resultSet.getString("firstname"))
                        .lastName(resultSet.getString("lastname"))
                        .patronymic(resultSet.getString("patronymic"))
                        .user_id(resultSet.getLong("user_id"))
                        .position(resultSet.getString("position"))
                        .image(resultSet.getString("image"))
                        .information(new ArrayList<DoctorInfoModel>(Arrays.asList(DoctorInfoModel.builder()
                                .name(resultSet.getString("name"))
                                .organizationName(resultSet.getString("organization_name"))
                                .infoType(InfoType.valueOf(resultSet.getString("info_type")))
                                .start(resultSet.getDate("start_date"))
                                .end(resultSet.getDate("end_date")).build())))
                        .build();
                while (resultSet.next()) {
                    doctorDataModel.getInformation().add(DoctorInfoModel.builder()
                            .name(resultSet.getString("name"))
                            .organizationName(resultSet.getString("organization_name"))
                            .infoType(InfoType.valueOf(resultSet.getString("info_type")))
                            .start(resultSet.getDate("start_date"))
                            .end(resultSet.getDate("end_date")).build());
                }
                return doctorDataModel;
            } else {
                resultSet.close();
                throw new RecordNotFoundException("(Doctor) Record not found with id: " + id);
            }
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
        throw new RecordNotFoundException("(Doctor) Record not found with id: " + id);
    }

    public DoctorReservationModel getDoctorDataById(Long id) {
        List<Long> categoryIds = new ArrayList<>();
        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = this.dataSource.getConnection();
            stmt = connection.prepareStatement(
                    "SELECT d.id," +
                            "u.username," +
                            "u.firstname," +
                            "u.lastname," +
                            "u.patronymic," +
                            "d.position," +
                            "c.id as catid," +
                            "c.name as catname" +
                            " FROM users AS u " +
                            "   JOIN doctor d on u.id = d.user_id" +
                            "   LEFT OUTER JOIN doctor_category dc ON d.id = dc.doctor_id" +
                            "   LEFT OUTER JOIN category c on c.id = dc.category_id" +
                            "   WHERE d.id = ?;");
            stmt.setInt(1, id.intValue());
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                DoctorReservationModel doctorReservationModel = DoctorReservationModel.builder()
                        .id(resultSet.getLong("id"))
                        .username(resultSet.getString("username"))
                        .firstName(resultSet.getString("firstname"))
                        .lastName(resultSet.getString("lastname"))
                        .patronymic(resultSet.getString("patronymic"))
                        .position(resultSet.getString("position"))
                        .categories(new ArrayList<CategorySmallModel>(Arrays.asList(
                                CategorySmallModel.builder()
                                        .id(resultSet.getLong("catid"))
                                        .name(resultSet.getString("catname"))
                                        .build()
                        )))
                        .build();
                categoryIds.add(resultSet.getLong("catid"));
                while (resultSet.next()) {
                    long catId;
                    if (!categoryIds.contains(catId = resultSet.getLong("catid"))) {
                        doctorReservationModel.getCategories().add(CategorySmallModel.builder()
                                .id(resultSet.getLong("catid"))
                                .name(resultSet.getString("catname"))
                                .build());
                        categoryIds.add(catId);
                    }
                }
                return doctorReservationModel;
            } else {
                resultSet.close();
                throw new RecordNotFoundException("(Doctor) Record not found with id: " + id);
            }
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
        throw new RecordNotFoundException("(Doctor) Record not found with id: " + id);
    }

    public DoctorFullInfoModel getDoctorFullInfoById(Long id) {
        List<Long> categoryIds = new ArrayList<>();
        List<Long> infoIds = new ArrayList<>();
        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = this.dataSource.getConnection();
            String sql = "SELECT d.id, " +
                    "       u.username, " +
                    "       u.firstname, " +
                    "       u.lastname, " +
                    "       u.patronymic, " +
                    "       d.position, " +
                    "       d.bio, " +
                    "       d.image, " +
                    "       di.id as infoid, " +
                    "       di.name, " +
                    "       di.organization_name, " +
                    "       di.info_type, " +
                    "       di.start_date, " +
                    "       di.end_date, " +
                    "       c.id as catid, " +
                    "       c.name as catname, " +
                    "       c.description " +
                    "FROM users AS u " +
                    "         JOIN doctor d on u.id = d.user_id " +
                    "         JOIN doctor_info di on d.id = di.doctor_id " +
                    "         LEFT OUTER JOIN doctor_category dc ON d.id = dc.doctor_id " +
                    "         LEFT OUTER JOIN category c on c.id = dc.category_id " +
                    "WHERE d.id = ?";
            stmt = connection.prepareStatement(sql);
            stmt.setInt(1, id.intValue());

            ResultSet resultSet = stmt.executeQuery();

            if (resultSet.next()) {
                DoctorFullInfoModel doctorDataModel = DoctorFullInfoModel.builder()
                        .id(resultSet.getLong("id"))
                        .username(resultSet.getString("username"))
                        .firstName(resultSet.getString("firstname"))
                        .lastName(resultSet.getString("lastname"))
                        .patronymic(resultSet.getString("patronymic"))
                        .position(resultSet.getString("position"))
                        .image(resultSet.getString("image"))
                        .bio(resultSet.getString("bio"))
                        .information(new ArrayList<DoctorInfoModel>(Arrays.asList(
                                DoctorInfoModel.builder()
                                        .name(resultSet.getString("name"))
                                        .organizationName(resultSet.getString("organization_name"))
                                        .infoType(InfoType.valueOf(resultSet.getString("info_type")))
                                        .start(resultSet.getDate("start_date"))
                                        .end(resultSet.getDate("end_date")).build()
                        )))
                        .categories(new ArrayList<CategoryInfoModel>(Arrays.asList(
                                CategoryInfoModel.builder()
                                        .id(resultSet.getLong("catid"))
                                        .name(resultSet.getString("catname"))
                                        .description(resultSet.getString("description")).build()
                        )))
                        .build();
                categoryIds.add(resultSet.getLong("catid"));
                infoIds.add(resultSet.getLong("infoid"));
                while (resultSet.next()) {
                    long infoId;
                    long catId;
                    if (!infoIds.contains(infoId = resultSet.getLong("infoid"))) {
                        doctorDataModel.getInformation().add(DoctorInfoModel.builder()
                                .name(resultSet.getString("name"))
                                .organizationName(resultSet.getString("organization_name"))
                                .infoType(InfoType.valueOf(resultSet.getString("info_type")))
                                .start(resultSet.getDate("start_date"))
                                .end(resultSet.getDate("end_date")).build());
                        infoIds.add(infoId);
                    }
                    if (!categoryIds.contains(catId = resultSet.getLong("catid"))) {
                        doctorDataModel.getCategories().add(CategoryInfoModel.builder()
                                .id(resultSet.getLong("catid"))
                                .name(resultSet.getString("catname"))
                                .description(resultSet.getString("description")).build());
                        categoryIds.add(catId);
                    }
                }
                return doctorDataModel;
            } else {
                resultSet.close();
                throw new RecordNotFoundException("(Doctor) Record not found with id: " + id);
            }
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
        throw new RecordNotFoundException("(Doctor) Record not found with id: " + id);
    }

    public Long getIdByReservationId(Long reservationId) {
        Connection connection = null;
        Statement stmt = null;
        Long id = null;
        try {
            connection = this.dataSource.getConnection();
            stmt = connection.createStatement();
            String sql = "SELECT d.id FROM reservation r" +
                    "        JOIN work_time wt on wt.id = r.work_time_id" +
                    "        JOIN doctor d on d.id = wt.doctor_id" +
                    "        WHERE r.id =  ";
            ResultSet resultSet = stmt.executeQuery(sql + reservationId);

            if (resultSet.next()) {
                System.out.println(resultSet);
                id = resultSet.getLong(1);
            } else {
                return null;
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

    public List<Long> getCategoryIdByDoctorId(Long doctorId) {
        Connection connection = null;
        Statement stmt = null;
        List<Long> ids = new ArrayList<>();
        try {
            connection = this.dataSource.getConnection();
            stmt = connection.createStatement();
            String sql = "SELECT dc.category_id FROM doctor_category dc " +
                    "        WHERE dc.doctor_id = ";
            ResultSet resultSet = stmt.executeQuery(sql + doctorId);

            while (resultSet.next()) {
                ids.add(resultSet.getLong(1));
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
        return ids;
    }


    public Integer detachByDoctorId(Long id) {
        Integer result = 0;
        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = this.dataSource.getConnection();
            String sql = "DELETE FROM" +
                    " doctor_category dc" +
                    "        WHERE dc.doctor_id = ?";
            stmt = connection.prepareStatement(sql);
            stmt.setLong(1, id);
            result = stmt.executeUpdate();
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
        return result;
    }

    public Integer attachByDoctorId(Long doctorId, List<Long> categoryIds) {
        Integer result = 0;
        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = this.dataSource.getConnection();
            String sql = "INSERT INTO doctor_category VALUES (?, ?)";
            stmt = connection.prepareStatement(sql);
            for (Long catId : categoryIds) {
                stmt.setLong(1, catId);
                stmt.setLong(2, doctorId);
                stmt.addBatch();
            }
            result = Arrays.stream(stmt.executeBatch()).sum();
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
        return result;
    }

}
