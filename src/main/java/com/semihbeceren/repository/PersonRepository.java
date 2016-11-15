package com.semihbeceren.repository;

import com.semihbeceren.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Semih Beceren on 11.11.2016.
 */
@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
}
