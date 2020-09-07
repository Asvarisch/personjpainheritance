package telran.ashkelon2020.person.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import telran.ashkelon2020.person.dto.CityPopulationDto;
import telran.ashkelon2020.person.dto.PersonDto;
import telran.ashkelon2020.person.service.PersonService;

@RestController
@RequestMapping("/person")
public class PersonController {

	@Autowired
	PersonService personService;

	@PostMapping
	public boolean addPerson(@RequestBody PersonDto personDto) {
		return personService.addPerson(personDto);
	}

	@GetMapping("/{id}")
	public PersonDto findPersonById(@PathVariable Integer id) {
		return personService.findPersonById(id);
	}

	@PutMapping("/{id}/name/{name}")
	public PersonDto editPersonName(@PathVariable Integer id, @PathVariable String name) {
		return personService.editPerson(id, name);
	}

	@DeleteMapping("/{id}")
	public PersonDto deletePerson(@PathVariable Integer id) {
		return personService.removePerson(id);
	}

	@GetMapping("/name/{name}")
	public Iterable<PersonDto> findSameNamePersons(@PathVariable String name) {
		return personService.findPersonsByName(name);
	}

	@GetMapping("/ages/{min}/{max}")
	public Iterable<PersonDto> findPersonsByAges(@PathVariable int min, @PathVariable int max) {
		return personService.findPersonsByAges(min, max);
	}

	@GetMapping("/city/{city}")
	public Iterable<PersonDto> findPersonsByCity(@PathVariable String city) {
		return personService.findPersonsByCity(city);
	}

	@GetMapping("/population/city")
	public Iterable<CityPopulationDto> getCityPopulation() {
		return personService.getCityPopulation();
	}
	
	@PutMapping("/salary/min/{min}/max/{max}")
	public Iterable<PersonDto> findEmployeeBySalary(@PathVariable int min, @PathVariable int max){
		return personService.findEmployeeBySalary(min, max);
	}
	
	@GetMapping("/children")
	public Iterable<PersonDto> getChildren(){
		return personService.getChildren();
	}

	
	
	
	
	
	
	
}
