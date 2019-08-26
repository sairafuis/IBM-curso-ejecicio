## Ejercicio de la sesion del 23/08/19

Se realizaron cambios al codigo para realizar las siguientes funciones:
- Consultar la lista de paises de un continente.
- Guardar un pais
- Consultar un pais con su Id.

Primero se modifico el archivo CountriesMongoRepository el cual quedo de la siguiente manera:

```java
@Repository
public interface CountriesMongoRepository extends CrudRepository<CountryDocument, String> {

	List<CountryDocument> findCountriesByContinent(String continentName);
	Optional<CountryDocument> findByName(String name);
}

```

Despues se modifico el archivo del servicio agregando las nuevas funcionalidades.

```
@Service
public class CountriesService implements ICountriesService {

  @Autowired
  CountriesMongoRepository countriesRepository;
  
  public List<CountryDocument> findCountriesByContinentName(String continentName) {
    Continent continent = Continent.continentByName(continentName);
    if((continentName.toLowerCase()).equals(continent.getContinentName()))
    {
    return countriesRepository.findCountriesByContinent(continentName.toUpperCase());
    }
    throw new InvalidContinentException("Continent: " + continentName + " does not exist.");
  }
  
  public Optional<CountryDocument> findCountryById(String countryId) {
	Optional<CountryDocument> countryOpt =  countriesRepository.findById(countryId);
	if (countryOpt.isPresent()) {
		return countryOpt;
	}
	throw new RuntimeException("Country with ID: " + countryId + " does not exist.");
	}
	

  public CountryDocument createCountry(CountryDocument country) {
	    return countriesRepository.save(country);
	  }
}
```


y por ultimo se modifico el archivo del controlador

```
@RestController
public class CountriesController {

  @Autowired
  ICountriesService countriesService;

  @GetMapping(path = "/api/countries/continent/name/{continentName}")
  public ResponseEntity<List<CountryDocument>> findCountriesByContinentName(@PathVariable String continentName) {
    return new ResponseEntity<List<CountryDocument>>(
        countriesService.findCountriesByContinentName(continentName), HttpStatus.OK);
  }
  
  @GetMapping(path = "/api/countries/continent/id/{countryId}")
  public ResponseEntity<Optional<CountryDocument>> findCountryById(@PathVariable String countryId) {
    return new ResponseEntity<Optional<CountryDocument>>(
        countriesService.findCountryById(countryId), HttpStatus.OK);
  }

  @PostMapping("/api/countries/continent/id")
	public ResponseEntity<Object> createCountry(@RequestBody CountryDocument country) {
	  CountryDocument savedcountry = countriesService.createCountry(country);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedcountry.getId()).toUri();
		System.out.print(location);
		return ResponseEntity.created(location).build();
	}
  
}
```
- para el servicio de la lista de paises por continente la ruta es http://localhost:8080/api/countries/continent/name/{nombredelcontinente} o el nombre del continente que se desee, el nombre puede ser mayusculas o minisculas.
- para el servicio de encontrar por ID un pais la ruta es http://localhost:8080/api/countries/continent/id/{id} el id para los paises es un numero
- para el  servicio post la ruta es http://localhost:8080/api/countries/continent/id, al ser usado este devuelve la URL para la consulta por Id y resive un json de la siguiente forma

```
{
    "name": "quiroz",
    "capital": "otro",
    "continent":"Asia"
}

```
