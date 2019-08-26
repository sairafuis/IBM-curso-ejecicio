## Ejercicio de la sesion del 23/08/19 (en Mysql)

Se realizaron cambios al codigo para realizar las siguientes funciones:
- Consultar la lista de paises de un continente.
- Guardar un pais
- Consultar un pais con su Id.

Primero se modifico el archivo CountryJpaRepository el cual quedo de la siguiente manera:

```java
@Repository
public interface CountryJpaRepository extends CrudRepository<CountryEntity, Integer>{

	List<CountryEntity> findByContinent (String continentName);
	
}

```

Despues se modifico el archivo del servicio agregando las nuevas funcionalidades.

```Java
@Service
public class CountriesService implements ICountriesService {

  @Autowired
  CountryJpaRepository countriesRepository;
  
  public List<CountryEntity> findCountriesByContinentName(String continentName) {
    Continent continent = Continent.continentByName(continentName);
    if (continent.getContinentName().contentEquals(continentName.toLowerCase())){
    	return countriesRepository.findByContinent(continentName);
    }
    throw new InvalidContinentException("Continent: " + continentName + " does not exist.");

  }


  public Optional<CountryEntity> findCountriesById(Integer countryId) {
	  Optional<CountryEntity> countryOpt = countriesRepository.findById(countryId);
	  if (countryOpt.isPresent()) {
		  return countryOpt;
	  }
       throw new CountryNotFoundException("Country id: " + countryId + " does not exist.");

  }

	
	public CountryEntity createCountry(CountryEntity country) {
		CountryEntity retsave = countriesRepository.save(country);
		if(retsave.equals(null)) {
			throw new CountryNotCreatedException("Country not created.");	
		}
		return countriesRepository.save(country);
	}
	  
}
```

y por ultimo se modifico el archivo del controlador

```Java
@RestController
public class CountriesController {

  @Autowired
  ICountriesService countriesService;

  @GetMapping(path = "/api/countries/continent/name/{continentName}")
  public ResponseEntity<List<CountryEntity>> findCountryByContinent(@PathVariable String continentName) {
    return new ResponseEntity<List<CountryEntity>>(
        countriesService.findCountriesByContinentName(continentName), HttpStatus.OK);
  }
  
  @GetMapping(path = "/api/countries/continent/id/{countryId}")
  public ResponseEntity<Optional<CountryEntity>> findCountryById(@PathVariable Integer countryId) {
    return new ResponseEntity<Optional<CountryEntity>>(
        countriesService.findCountriesById(countryId), HttpStatus.OK);
  }
  
  @PostMapping(path = "/api/countries/continent")
  public ResponseEntity<Object> createCountry(@RequestBody CountryEntity country) {
	CountryEntity savedCountry = countriesService.createCountry(country);
	URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/id/{id}").buildAndExpand(savedCountry.getCountryId()).toUri();
	System.out.print(location);
	return ResponseEntity.created(location).build(); 
	
  }

}

```
- para el servicio de la lista de paises por continente la ruta es http://localhost:8080/api/countries/continent/name/{nombredelcontinente} o el nombre del continente que se desee, el nombre puede ser mayusculas o minisculas.
- para el servicio de encontrar por ID un pais la ruta es http://localhost:8080/api/countries/continent/id/{id} el id para los paises es un numero
- para el  servicio post la ruta es http://localhost:8080/api/countries/continent, al ser usado este devuelve la URL para la consulta por Id y resive un json de la siguiente forma

```Json
{
    "name": "quiroz",
    "capital": "otro",
    "continent":"Asia"
}

```
