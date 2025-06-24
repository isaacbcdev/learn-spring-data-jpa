package com.isaacbcdev.spring.boot.datajpa;

import com.isaacbcdev.spring.boot.datajpa.dto.PersonDTO;
import com.isaacbcdev.spring.boot.datajpa.entities.Person;
import com.isaacbcdev.spring.boot.datajpa.repositories.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@SpringBootApplication
@RequiredArgsConstructor
public class DataJpaApplication implements CommandLineRunner {

    private final PersonRepository personRepository;

    public static void main(String[] args) {
        SpringApplication.run(DataJpaApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        create();
    }

    @Transactional(readOnly = true)
    public void whereIn() {
        System.out.println("Consulta que devuelve una lista de personas por ids: ");
        List<Long> ids = List.of(1L, 2L, 3L);
        List<Person> persons = personRepository.getPersonsByIds(ids);
        persons.forEach(System.out::println);
    }

    @Transactional(readOnly = true)
    public void customQuerySubQueries() {
        System.out.println("Subquery that returns a list of persons with the longest name: ");
        List<Object[]> largerNames = personRepository.findLargerName();
        largerNames.forEach(data -> System.out.println("Name: " + data[0] + ", Length: " + data[1]));

        System.out.println("Subquery that returns a list of persons with the shortest name: ");
        List<Object[]> smallerNames = personRepository.findSmallerName();
        smallerNames.forEach(data -> System.out.println("Name: " + data[0] + ", Length: " + data[1]));

        System.out.println("Subquery that returns the last registered person: ");
        Optional<Person> lastRegister = personRepository.getLastRegister();
        lastRegister.ifPresent(person -> System.out.println("Last registered person: " + person));
    }

    @Transactional(readOnly = true)
    public void customQueryGrouped() {
        System.out.println("Consulta que devuelve el total de personas: ");
        Long totalPersons = personRepository.totalPerson();
        System.out.println("Total persons: " + totalPersons);

        System.out.println("Consulta que devuelve el id minimo: ");
        Long minId = personRepository.minId();
        System.out.println("Min id: " + minId);

        System.out.println("Consulta que devuelve el id maximo: ");
        Long maxId = personRepository.maxId();
        System.out.println("Min id: " + maxId);

        System.out.println("Consulta que devuelve una lista de personas por nombre con su longitud: ");
        List<Object[]> formattedPersons = personRepository.findAllPersonNameData();
        formattedPersons.forEach(data -> System.out.println("Name: " + data[0] + ", Name Length: " + data[1]));

        System.out.println("Consulta que devuelve el nombre con la longitud máxima: ");
        Integer maxName = personRepository.findMaxPersonName();
        System.out.println("Max name length: " + maxName);

        System.out.println("Consulta que devuelve el nombre con la longitud mínima: ");
        Integer minName = personRepository.findMinPersonName();
        System.out.println("Min name length: " + minName);

        System.out.println("Consulta que devuelve el resumen de funciones agrupadas: ");
        Object[] groupedData = (Object[]) personRepository.getResumeGroupedFunctions();
        System.out.println("Max ID: " + groupedData[0]);
        System.out.println("Min ID: " + groupedData[1]);
        System.out.println("Average Length of Lastname: " + groupedData[2]);
        System.out.println("Sum of IDs: " + groupedData[3]);
    }

    @Transactional(readOnly = true)
    public void customQueryBetween() {
        System.out.println("Consulta que devuelve una lista de personas con id entre 2 y 5: ");
        List<Person> persons = personRepository.findByIdBetweenOrderByNameDesc(2L, 5L);
        persons.forEach(System.out::println);

        System.out.println("Consulta que devuelve una lista de personas con nombre entre A y I: ");
        List<Person> personsByName = personRepository.findByNameBetweenOrderByProgrammingLanguageAscLastnameAsc("A", "I");
        personsByName.forEach(System.out::println);

        System.out.println("Consulta que devuelve una lista de personas: ");
        List<Person> personsByNameQuery = personRepository.getAllOrderedPerson();
        personsByNameQuery.forEach(System.out::println);


        System.out.println("Consulta que devuelve una lista de personas: ");
        List<Person> findAllOrderByNameDesc = personRepository.findAllByOrderByLastnameDesc();
        findAllOrderByNameDesc.forEach(System.out::println);
    }

    @Transactional(readOnly = true)
    public void customQueriesUpperAndLowerCase() {
        System.out.println("Consulta que devuelve una lista de nombres concatenados: ");
        List<String> fullNames = personRepository.findAllNameConcat();
        fullNames.forEach(System.out::println);

        System.out.println("Consulta que devuelve una lista de nombres concatenados en mayúsculas: ");
        List<String> fullNamesUpperCase = personRepository.findAllNameConcatUpperCase();
        fullNamesUpperCase.forEach(System.out::println);

        System.out.println("Consulta que devuelve una lista de nombres concatenados en minúsculas: ");
        List<String> fullNamesLowerCase = personRepository.findAllNameConcatLowerCase();
        fullNamesLowerCase.forEach(System.out::println);

        System.out.println("Consulta que devuelve una lista de personas con formato de mayúsculas y minúsculas: ");
        List<Object[]> formattedPersons = personRepository.findAllPersonCaseFormatted();
        formattedPersons.forEach(data -> System.out.println("ID: " + data[0] + ", Name: " + data[1] + ", Lastname: " + data[2] + ", Programming Language: " + data[3]));
    }

    @Transactional(readOnly = true)
    public void customQueriesDistinct() {
        System.out.println("Consulta que devuelve una lista de nombres: ");
        List<String> names = personRepository.findAllNames();
        names.forEach(System.out::println);

        System.out.println("Consulta que devuelve una lista de apellidos sin repetidos: ");
        List<String> uniqueNames = personRepository.findAllLastnamesDistinct();
        uniqueNames.forEach(System.out::println);

        System.out.println("Consulta que devuelve una lista de lenguajes de programación sin repetidos: ");
        List<String> uniqueProgrammingLanguages = personRepository.findAllProgrammingLanguageDistinct();
        uniqueProgrammingLanguages.forEach(System.out::println);

        System.out.println("Consulta que devuelve el número de lenguajes de programación sin repetidos: ");
        Long countProgrammingLanguages = personRepository.findAllProgrammingLanguageDistinctCount();
        System.out.println("Number of distinct programming languages: " + countProgrammingLanguages);
    }

    @Transactional
    public void customQueries2() {
        System.out.println("Consulta mixta: ");
        List<Object[]> mix = personRepository.findMixPerson();
        mix.forEach(data -> System.out.println("Programming language: " + data[1] + ", for person: " + data[0]));

        System.out.println("Consulta que devuelve un objeto person de una instancia personalizada: ");
        List<Person> persons = personRepository.findCustomPersonEntityInstance();
        persons.forEach(System.out::println);

        System.out.println("Consulta que devuelve un objeto person de una instancia personalizada: ");
        List<PersonDTO> personsDto = personRepository.findCustomPersonDTOInstance();
        personsDto.forEach(System.out::println);
    }

    @Transactional
    public void customQueries() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the ID of the person to find: ");
        Long id = sc.nextLong();
        sc.close();

        String name = personRepository.getNameById(id);
        String fullname = personRepository.getFullNameById(id);
        System.out.println("Name of the person with ID " + id + ": " + name);
        System.out.println("Fullname of the person with ID " + id + ": " + fullname);

        System.out.println("Consulta personalizada por el id: ");
        Object[] person = (Object[]) personRepository.findPersonDataListById(id);
        System.out.println("id: " + person[0] + ", name: " + person[1] + ", lastname: " + person[2] + "programming language: " + person[3]);

        System.out.println("Consulta de objetos pero personalizada: ");
        List<Object[]> info = personRepository.findPersonDataList();
        info.forEach(data -> System.out.println("id: " + data[0] + ", name: " + data[1] + ", lastname: " + data[2] + "programming language: " + data[3]));
    }

    @Transactional // Is used to manage transactions, ensuring that the operations are atomic
    public void create() {
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter the name of the person:");
        String name = sc.nextLine();

        System.out.println("Enter the last name of the person:");
        String lastName = sc.nextLine();

        System.out.println("Enter the programming language of the person:");
        String programmingLanguage = sc.nextLine();

        Person person = new Person(null, name, lastName, programmingLanguage); // ID is null because it will be generated by the database
        Person newPerson = personRepository.save(person);
        System.out.println("New person created: " + newPerson);

        personRepository.findById(newPerson.getId()).ifPresent(System.out::println);
    }

    @Transactional
    public void update() {
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter the ID of the person to update:");
        Long id = sc.nextLong();
        sc.nextLine(); // Consume the newline character

        Optional<Person> person = personRepository.findById(id);
//        person.ifPresent(person1 -> {
//            System.out.println(person1);
//            System.out.println("Current programming language: " + person1.getProgrammingLanguage());
//            System.out.println("Enter the new programming language:");
//            String newProgrammingLanguage = sc.nextLine();
//            person1.setProgrammingLanguage(newProgrammingLanguage);
//
//            personRepository.save(person1);
//            System.out.println("Person updated: " + person1);
//        });

        if (person.isPresent()) {
            Person person1 = person.orElseThrow();
            System.out.println(person1);
            System.out.println("Current programming language: " + person1.getProgrammingLanguage());
            System.out.println("Enter the new programming language:");
            String newProgrammingLanguage = sc.nextLine();
            person1.setProgrammingLanguage(newProgrammingLanguage);

            personRepository.save(person1);
            System.out.println("Person updated: " + person1);
        } else {
            System.out.println("Person with ID " + id + " not found.");
        }

        sc.close();
    }

    @Transactional
    public void delete() {
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter the ID of the person to delete:");
        Long id = sc.nextLong();
        list();
        personRepository.deleteById(id);
        list();

        sc.close();
    }

    @Transactional
    public void delete2() {
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter the ID of the person to delete:");
        Long id = sc.nextLong();
        list();
        System.out.println("------------------");

        Optional<Person> optionalPerson = personRepository.findById(id);
        optionalPerson.ifPresentOrElse(personRepository::delete,
            () -> System.out.println("Person with ID " + id + " not found.")
        );

        System.out.println("------------------");
        list();

        sc.close();
    }

    @Transactional(readOnly = true)
    // If the method is read-only, it can optimize performance by not locking the database
    public void findOne() {
//        Person person = null;
//        Optional<Person> optionalPerson = personRepository.findOne(1L);
//        if (!optionalPerson.isEmpty()) {
//        if (optionalPerson.isPresent()) {
//            person = optionalPerson.get();
//        }
//        System.out.println("person = " + person);
        personRepository.findByNameContaining("Isa").ifPresent(System.out::println);
    }

    @Transactional(readOnly = true)
    public void list() {
        List<Person> persons = (List<Person>) personRepository.findAll();
//        List<Object[]> persons = personRepository.findNameAndProgrammingLanguageData("Java");
        persons.forEach(person -> {
            System.out.println("With ID:" + person.getId() + ", " + person.getName() + " " +person.getLastname() + " is an expert in: " + person.getProgrammingLanguage());
        });
    }
}