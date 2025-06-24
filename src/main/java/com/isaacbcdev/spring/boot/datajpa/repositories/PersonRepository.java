package com.isaacbcdev.spring.boot.datajpa.repositories;

import com.isaacbcdev.spring.boot.datajpa.dto.PersonDTO;
import com.isaacbcdev.spring.boot.datajpa.entities.Person;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PersonRepository extends CrudRepository<Person, Long> {

    @Query("SELECT p FROM Person p WHERE p.id NOT IN ?1")
    List<Person> getPersonsByIds(List<Long> ids);

    @Query("SELECT p FROM Person p WHERE p.id = (SELECT MAX(p.id) FROM Person p)")
    Optional<Person> getLastRegister();

    @Query("SELECT p.name, LENGTH(p.name) FROM Person p WHERE LENGTH(p.name)=(SELECT MAX(LENGTH(p.name)) FROM Person p)")
    List<Object[]> findLargerName();

    @Query("SELECT p.name, LENGTH(p.name) FROM Person p WHERE LENGTH(p.name)=(SELECT MIN(LENGTH(p.name)) FROM Person p)")
    List<Object[]> findSmallerName();

    @Query("SELECT MAX(p.id), MIN(p.id), AVG(LENGTH(p.lastname)), SUM(p.id) FROM Person p")
    Object getResumeGroupedFunctions();

    @Query("SELECT p.name, LENGTH(p.name) FROM Person p")
    List<Object[]> findAllPersonNameData();

    @Query("SELECT MAX(LENGTH(p.name)) FROM Person p")
    Integer findMaxPersonName();

    @Query("SELECT MIN(LENGTH(p.name)) FROM Person p")
    Integer findMinPersonName();

    @Query("SELECT COUNT(p) FROM Person p")
    Long totalPerson();

    @Query("SELECT MIN(p.id) FROM Person p")
    Long minId();

    @Query("SELECT MAX(p.id) FROM Person p")
    Long maxId();

    List<Person> findAllByOrderByNameDesc();

    List<Person> findAllByOrderByLastnameDesc();

    @Query("SELECT p FROM Person p ORDER BY p.lastname DESC")
    List<Person> getAllOrderedPerson();

    List<Person> findByIdBetweenOrderByNameDesc(Long id, Long id2);

    List<Person> findByNameBetweenOrderByProgrammingLanguageAscLastnameAsc(String first, String end);

    @Query("SELECT p FROM Person p WHERE p.name BETWEEN ?1 AND ?2 ORDER BY (p.name) DESC")
    List<Person> findByNameBetweenQuery(String start, String end);

    @Query("SELECT p FROM Person p WHERE p.id BETWEEN ?1 AND ?2 ORDER BY p.programmingLanguage ASC, p.name DESC")
    List<Person> findByIdBetweenQuery(Long start, Long end);

    @Query("SELECT p.name FROM Person p")
    List<String> findAllNames();

    //    @Query("SELECT CONCAT(p.name, ' ', p.lastname) AS fullname FROM Person p")
    @Query("SELECT p.name || ' ' || p.lastname AS fullname FROM Person p")
    List<String> findAllNameConcat();

    @Query("SELECT UPPER(CONCAT(p.name, ' ', p.lastname)) AS fullname FROM Person p")
    List<String> findAllNameConcatUpperCase();

    @Query("SELECT LOWER(CONCAT(p.name, ' ', p.lastname)) AS fullname FROM Person p")
    List<String> findAllNameConcatLowerCase();

    @Query("SELECT p.id, UPPER(p.name), p.lastname, LOWER(p.programmingLanguage) FROM Person p")
    List<Object[]> findAllPersonCaseFormatted();

    @Query("SELECT DISTINCT(p.lastname) FROM Person p")
    List<String> findAllLastnamesDistinct();

    @Query("SELECT DISTINCT(p.programmingLanguage) FROM Person p")
    List<String> findAllProgrammingLanguageDistinct();

    @Query("SELECT COUNT(DISTINCT(p.programmingLanguage)) FROM Person p")
    Long findAllProgrammingLanguageDistinctCount();

    @Query("SELECT new com.isaacbcdev.spring.boot.datajpa.dto.PersonDTO(p.name, p.lastname) FROM Person p")
    List<PersonDTO> findCustomPersonDTOInstance();

    @Query("SELECT new Person(p.name, p.lastname) FROM Person p")
    List<Person> findCustomPersonEntityInstance();

    @Query("SELECT p.name FROM Person p WHERE p.id = ?1")
    String getNameById(Long id);

    @Query("SELECT p.id FROM Person p WHERE p.id = ?1")
    Long getIdById(Long id);

    @Query("SELECT CONCAT(p.name, ' ', p.lastname) AS fullname FROM Person p WHERE p.id = ?1")
    String getFullNameById(Long id);

    @Query("SELECT p FROM Person p WHERE p.id = ?1")
    Optional<Person> findOne(Long id);

    @Query("SELECT p FROM Person p WHERE p.name = ?1")
    Optional<Person> findOneName(String name);

    @Query("SELECT p FROM Person p WHERE p.name like %?1%")
    Optional<Person> findOneLikeName(String name);

    Optional<Person> findByNameContaining(String name);

    List<Person> findByProgrammingLanguageIgnoreCase(String programmingLanguage);

    //    @Query("SELECT p FROM Person p WHERE p.programmingLanguage = ?1 and p.name = ?2")
    List<Person> findByProgrammingLanguageAndName(String programmingLanguage, String name);

    @Query("SELECT p.name, p.programmingLanguage FROM Person p")
    List<Object[]> findNameAndProgrammingLanguageData();

    @Query("SELECT p, p.programmingLanguage FROM Person p")
    List<Object[]> findMixPerson();

    @Query("SELECT p.id, p.name, p.lastname, p.programmingLanguage FROM Person p")
    List<Object[]> findPersonDataList();

    @Query("SELECT p.id, p.name, p.lastname, p.programmingLanguage FROM Person p WHERE p.id = ?1")
    Object findPersonDataListById(Long id);
}