package com.tobi.employeeplsqlpractice.repository;

import com.tobi.employeeplsqlpractice.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import java.util.Map;

@Repository
public class EmployeeRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    public List<Employee> getEmployeeList(Employee employee) {
        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withSchemaName("TESTDB")
                .withProcedureName("CustomerProcedure")
                .declareParameters(new SqlParameter[]{
                        new SqlParameter("empid", Types.INTEGER),
                        new SqlParameter("c_cursor", Types.REF_CURSOR),
                        new SqlParameter("o_sqlcode", Types.INTEGER),
                        new SqlParameter("o_sqlmsg", Types.VARCHAR)
                })
                .returningResultSet("c_cursor", new RowMapper<Employee>() {
                    @Override
                    public Employee mapRow(ResultSet rs, int rowNum) throws SQLException {
                        Employee employee1 = new Employee();
                        employee1.setEmployeeId(rs.getInt(1));
                        employee1.setSalary(rs.getFloat(2));
                        employee1.setOrderId(rs.getInt(3));
                        employee1.setOrderDate(rs.getDate(4));
                        employee1.setShipName(rs.getString("SHIPNAME"));
                        return employee1;
                    }
                });
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("emplid", "1234");

        Map<String,Object> results = simpleJdbcCall.execute(mapSqlParameterSource);
        return (List<Employee>) results.get("c_cursor");
    }

}
