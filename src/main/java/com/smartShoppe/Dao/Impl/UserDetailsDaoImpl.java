package com.smartShoppe.Dao.Impl;

import com.smartShoppe.Dao.UserDetailsDao;
import com.smartShoppe.Entity.UserDetailsEntity;
import com.smartShoppe.Dto.UserDetailsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.Objects;
import java.util.Optional;

@Repository
public class UserDetailsDaoImpl implements UserDetailsDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserDetailsDaoImpl(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    @Override
    public Optional<UserDetailsDto> getUserDetails(Integer userId) {
        String sql = "SELECT * FROM userDetails WHERE id = ?";

        try {
            UserDetailsDto userDetailsDto = jdbcTemplate.queryForObject(
                    sql,
                    new Object[]{userId},
                    BeanPropertyRowMapper.newInstance(UserDetailsDto.class)
            );
            return Optional.ofNullable(userDetailsDto);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Integer insertUserDetails(UserDetailsEntity userDetailsEntity) {
        try {
            String sql = "INSERT INTO userDetails (first_name, m_name, last_name, email, country_code, mobile_number, password, type, dob) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(con -> {
                PreparedStatement ps = con.prepareStatement(sql, new String[]{"id"});
                ps.setString(1, userDetailsEntity.getFirstName());
                ps.setString(2, userDetailsEntity.getMName());
                ps.setString(3, userDetailsEntity.getLastName());
                ps.setString(4, userDetailsEntity.getEmail());
                ps.setInt(5, userDetailsEntity.getCountryCode());
                ps.setString(6, userDetailsEntity.getMobileNumber());
                ps.setString(7, userDetailsEntity.getPassword());
                ps.setString(8, userDetailsEntity.getType().name());
                ps.setDate(9, userDetailsEntity.getDob());
                return ps;
            }, keyHolder);

            return Objects.requireNonNull(keyHolder.getKey()).intValue();
        } catch (Exception e) {
            return null;
        }
    }
}
