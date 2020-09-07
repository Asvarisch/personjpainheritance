package telran.ashkelon2020.person.service;

import java.time.LocalDate;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import telran.ashkelon2020.person.dao.PersonRepository;
import telran.ashkelon2020.person.dto.ChildDto;
import telran.ashkelon2020.person.dto.CityPopulationDto;
import telran.ashkelon2020.person.dto.EmployeeDto;
import telran.ashkelon2020.person.dto.PersonDto;
import telran.ashkelon2020.person.dto.PersonNotFoundException;
import telran.ashkelon2020.person.model.Child;
import telran.ashkelon2020.person.model.Employee;
import telran.ashkelon2020.person.model.Person;

@Service
public class PersonServiceImpl implements PersonService {

	@Autowired
	PersonRepository personRepository;

	@Autowired
	ModelMapper modelMapper;
	
//	@Override
//	@Transactional
//	public boolean addPerson(PersonDto personDto) {
//		if (personRepository.existsById(personDto.getId())) {
//			return false;
//		}
//
//		if (personDto instanceof EmployeeDto) {
//			Employee person = modelMapper.map(personDto, Employee.class);
//			personRepository.save(person);
//		} else if (personDto instanceof ChildDto) {
//			Child person = modelMapper.map(personDto, Child.class);
//			personRepository.save(person);
//		} else {
//			Person person = modelMapper.map(personDto, Person.class);
//			personRepository.save(person);
//		}
//		return true;
//	}
	@Override
	@Transactional
	public boolean addPerson(PersonDto personDto) {
		if (personRepository.existsById(personDto.getId())) {
			return false;
		}
		if (personDto instanceof ChildDto) {
			Child child = modelMapper.map(personDto, Child.class);
			personRepository.save(child);
		} else if (personDto instanceof EmployeeDto) {
			Employee employee = modelMapper.map(personDto, Employee.class);
			personRepository.save(employee);
		} else {
			Person person = modelMapper.map(personDto, Person.class);
			personRepository.save(person);
		}
		return true;

	}

	@Override
	public PersonDto findPersonById(Integer id) {
		if (!personRepository.existsById(id)) {
			throw new PersonNotFoundException();
		}
		if (personRepository.findById(id).get() instanceof Child) {
			return modelMapper.map(personRepository.findById(id).get(), ChildDto.class);
		} else if (personRepository.findById(id).get() instanceof Employee) {
			return modelMapper.map(personRepository.findById(id).get(), EmployeeDto.class);
		}else return modelMapper.map(personRepository.findById(id).get(), PersonDto.class);
	}

	@Override
	@Transactional
	public PersonDto editPerson(Integer id, String name) {
		if (!personRepository.existsById(id)) {
			throw new PersonNotFoundException();
		}
		if (personRepository.findById(id).get() instanceof Child) {
			Child child = (Child) personRepository.findById(id).get();
			child.setName(name);
			personRepository.save(child);
			return modelMapper.map(child, ChildDto.class);
		} else if (personRepository.findById(id).get() instanceof Employee) {
			Employee employee = (Employee) personRepository.findById(id).get();
			employee.setName(name);
			personRepository.save(employee);
			return modelMapper.map(employee, EmployeeDto.class);
		}else{
			Person person = personRepository.findById(id).get();
			person.setName(name);
			personRepository.save(person);
			return modelMapper.map(person, PersonDto.class);
		}
	}

	@Override
	@Transactional
	public PersonDto removePerson(Integer id) {
		if (!personRepository.existsById(id)) {
			throw new PersonNotFoundException();
		}
		if (personRepository.findById(id).get() instanceof Child) {
			Child child = (Child) personRepository.findById(id).get();
			personRepository.delete(child);
			return modelMapper.map(child, ChildDto.class);
		} else if (personRepository.findById(id).get() instanceof Employee) {
			Employee employee = (Employee) personRepository.findById(id).get();
			personRepository.delete(employee);
			return modelMapper.map(employee, EmployeeDto.class);
		}else{
			Person person = personRepository.findById(id).get();
			personRepository.delete(person);
			return modelMapper.map(person, PersonDto.class);
		}
	}

	@Override
	@Transactional(readOnly = true)
	public Iterable<PersonDto> findPersonsByName(String name) {
		return personRepository.findByName(name)
				.map(p -> modelMapper.map(p, PersonDto.class))
				.collect(Collectors.toList());
	}

	@Override
	@Transactional(readOnly = true)
	public Iterable<PersonDto> findPersonsByAges(int min, int max) {
		LocalDate from = LocalDate.now().minusYears(max);
		LocalDate to = LocalDate.now().minusYears(min);
		return personRepository.findByBirthDateBetween(from, to)
				.map(p -> modelMapper.map(p, PersonDto.class))
				.collect(Collectors.toList());
	}

	@Override
	@Transactional(readOnly = true)
	public Iterable<PersonDto> findPersonsByCity(String city) {
		return personRepository.findByAddressCity(city)
				.map(p -> modelMapper.map(p, PersonDto.class))
				.collect(Collectors.toList());
	}

	@Override
	public Iterable<CityPopulationDto> getCityPopulation() {
		return personRepository.getCityPopulation();
	}

	@Override
	@Transactional(readOnly = true)
	public Iterable<PersonDto> findEmployeeBySalary(int min, int max) {
		
		return personRepository.findEmployeeBySalary(min, max)
				.map(e -> modelMapper.map(e, EmployeeDto.class))
				.collect(Collectors.toList());
	}

	@Override
	@Transactional(readOnly = true)
	public Iterable<PersonDto> getChildren() {
		return personRepository.getChildren()
				.map(c -> modelMapper.map(c, ChildDto.class))
				.collect(Collectors.toList());
	}

}
