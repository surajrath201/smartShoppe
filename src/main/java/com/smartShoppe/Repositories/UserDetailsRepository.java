package com.smartShoppe.Repositories;

import com.smartShoppe.Entity.UserDetailsEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDetailsRepository extends CrudRepository<UserDetailsEntity, Long> {

    public List<UserDetailsEntity> findByFirstName(String name);
}
