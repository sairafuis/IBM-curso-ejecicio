
Se realizaron los cambios al codigo para las siguientes funciones:
-Consultar la lista de paises de un continente.
-Guardar un pais
-Consultar un pais con su Id.

Para esto, se modifico el archivo CountryJpaRepository.java quedando de la siguiente manera:

```java
@Repository
public interface CountryJpaRepository extends CrudRepository<CountryEntity, Integer>{
	public List<CountryEntity> findByContinent(String continent);
	public List<CountryEntity> findByCountryId(Integer countryId);
}
```

Posteriormente se modificaron los archivos del servicio para realizar las acciones de buscar y guardar.

### Buscar paises por continente

```java
@Autowired
CountryJpaRepository countriesRepository;
@Override
public List<CountryEntity> findCountriesByContinentName(String continentName) {
  Continent continent = Continent.continentByName(continentName);
  switch (continent) {
  case EUROPE:
  case ASIA:
  case AFRICA:
  case NORTH_AMERICA:
    return countriesRepository.findByContinent(continent.getContinentName());
  default:
    throw new InvalidContinentException("Continent: " + continentName + " does not exist.");
  }
}
```

### Buscar país por id 
```java
@Autowired
CountryJpaRepository countriesRepository;
@Override
public CountryEntity findByCountryId(Integer countryId) {
  Optional<CountryEntity> countryOpt = countriesRepository.findById(countryId);
  if (countryOpt.isPresent()) {
    return countryOpt.get();
  } else {
    throw new InvalidCountryException("Country with id: " + countryId + " does not exist.");
  }
}

```

### Guardar nuevo país

```java
@Override
public CountryEntity SaveNewCountry(CountryEntity country) {
  Continent continent = Continent.continentByName(country.getContinent());
  switch (continent) {
  case EUROPE:
  case ASIA:
  case AFRICA:
  case NORTH_AMERICA:
    countriesRepository.save(country);
    break;
  default:
    throw new InvalidContinentException("Continent: " + country.getContinent() + " does not exist.");
  }
  return country;	
}

```
La ruta para guardar un país quedó definida como:
``` java 
@PostMapping(path = "/api/countries/country/")
public ResponseEntity<CountryEntity> saveCountry(@RequestBody CountryEntity country) {
  return new ResponseEntity<CountryEntity>(
      countriesService.SaveNewCountry(country), HttpStatus.OK);
}  
```

![POST request](/img/API-POST-Country.png)
