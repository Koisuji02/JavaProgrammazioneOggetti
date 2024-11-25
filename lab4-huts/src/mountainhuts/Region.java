package mountainhuts;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

/**
 * Class {@code Region} represents the main facade
 * class for the mountains hut system.
 * 
 * It allows defining and retrieving information about
 * municipalities and mountain huts.
 *
 */
public class Region {
	// ATTRIBUTI
	private String name;
	// si itera di 2 in 2, perchè ho 2 valori (estremi) per una singola altitudine
	protected ArrayList<Integer> altitudini = new ArrayList<>();
	private Collection<Municipality> comuni = new ArrayList<>();
	private Collection<MountainHut> rifugi = new ArrayList<>();
	// Mappa di province; ogni tupla è (provincia, mappa di comuni per provincia); nella mappa di comuni per provincia, ho ogni tupla (comune, lista di rifugi per comune)
	private Map<String, Map<Municipality, ArrayList<MountainHut>>> province = new TreeMap<>();

	/**
	 * Create a region with the given name.
	 * 
	 * @param name
	 *            the name of the region
	 */
	public Region(String name) {
		this.name = name;
	}

	/**
	 * Return the name of the region.
	 * 
	 * @return the name of the region
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Create the ranges given their textual representation in the format
	 * "[minValue]-[maxValue]".
	 * 
	 * @param ranges
	 *            an array of textual ranges
	 */
	public void setAltitudeRanges(String... ranges) {
		String[] parts = new String[2];
		for(String s : ranges){
			parts = s.split("-");
			this.altitudini.add(Integer.parseInt(parts[0]));
			this.altitudini.add(Integer.parseInt(parts[1]));
		}
	}

	/**
	 * Return the textual representation in the format "[minValue]-[maxValue]" of
	 * the range including the given altitude or return the default range "0-INF".
	 * 
	 * @param altitude
	 *            the geographical altitude
	 * @return a string representing the range
	 */
	public String getAltitudeRange(Integer altitude) {
		String s = "0-INF";
		for(int i = 0; i+1 < this.altitudini.size(); i+=2){
			if(altitude > this.altitudini.get(i) && altitude <= this.altitudini.get(i+1)) s = this.altitudini.get(i) + "-" + this.altitudini.get(i+1);
		}
		return s;
	}

	/**
	 * Return the altitude ranges
	 * @return
	 */
	public String getAltitudeRanges() {
		if(this.altitudini.isEmpty()) return "0-INF";
		String s = "";
		for(int i = 0; i+1 < this.altitudini.size(); i+=2){
			s += this.altitudini.get(i) + "-" + this.altitudini.get(i+1) + " ";
		}
		return s;
	}

	/**
	 * Return all the municipalities available.
	 * 
	 * The returned collection is unmodifiable
	 * 
	 * @return a collection of municipalities
	 */
	public Collection<Municipality> getMunicipalities() {
		return this.comuni;
	}

	/**
	 * Return all the mountain huts available.
	 * 
	 * The returned collection is unmodifiable
	 * 
	 * @return a collection of mountain huts
	 */
	public Collection<MountainHut> getMountainHuts() {
		return this.rifugi;
	}

	/**
	 * Create a new municipality if it is not already available or find it.
	 * Duplicates must be detected by comparing the municipality names.
	 * 
	 * @param name
	 *            the municipality name
	 * @param province
	 *            the municipality province
	 * @param altitude
	 *            the municipality altitude
	 * @return the municipality
	 */
	public Municipality createOrGetMunicipality(String name, String province, Integer altitude) {
		for(Municipality m : this.comuni){
			if(m.getName().equals(name)) return m;
		}
		Municipality c = new Municipality(name, province, altitude);
		// se ho già provincia nelle province, aggiungo il comune alla provincia
		if(this.province.containsKey(province)){
			Map<Municipality, ArrayList<MountainHut>> comunePerProvincia = this.province.get(province);
			comunePerProvincia.put(c, new ArrayList<>());
		} else { // se non ho già provincia, creo una riga per la provincia nuova
			Map<Municipality, ArrayList<MountainHut>> tmpComuni = new HashMap<>();
			tmpComuni.put(c, new ArrayList<>());
			this.province.put(province, tmpComuni);
		}
		this.comuni.add(c);
		return c;
	}

	/**
	 * Create a new mountain hut if it is not already available or find it.
	 * Duplicates must be detected by comparing the mountain hut names.
	 *
	 * @param name
	 *            the mountain hut name
	 * @param category
	 *            the mountain hut category
	 * @param bedsNumber
	 *            the number of beds in the mountain hut
	 * @param municipality
	 *            the municipality in which the mountain hut is located
	 * @return the mountain hut
	 */
	public MountainHut createOrGetMountainHut(String name, String category, Integer bedsNumber, Municipality municipality) {
		for(MountainHut m : this.rifugi){
			if(m.getName().equals(name)) return m;
		}
		MountainHut c = new MountainHut(name, null, category, bedsNumber, municipality);
		// prendo i comuni della provincia
		Map<Municipality, ArrayList<MountainHut>> comuniPerProvincia = this.province.get(municipality.getProvince());
		// se ho già il comune nella mappa, aggiungo solo il rifugio
		if(comuniPerProvincia.containsKey(municipality)){
			ArrayList<MountainHut> tmp = comuniPerProvincia.get(municipality);
			tmp.add(c);
		}
		else { // non ho comune, quindi lo aggiungo
			ArrayList<MountainHut> tmp = new ArrayList<>();
			tmp.add(c);
			comuniPerProvincia.put(municipality, tmp);
			this.comuni.add(municipality);
		}
		this.rifugi.add(c);
		return c;
	}

	/**
	 * Create a new mountain hut if it is not already available or find it.
	 * Duplicates must be detected by comparing the mountain hut names.
	 * 
	 * @param name
	 *            the mountain hut name
	 * @param altitude
	 *            the mountain hut altitude
	 * @param category
	 *            the mountain hut category
	 * @param bedsNumber
	 *            the number of beds in the mountain hut
	 * @param municipality
	 *            the municipality in which the mountain hut is located
	 * @return a mountain hut
	 */
	public MountainHut createOrGetMountainHut(String name, Integer altitude, String category, Integer bedsNumber, Municipality municipality) {
		for(MountainHut m : this.rifugi){
			if(m.getName().equals(name)) return m;
		}
		MountainHut c = new MountainHut(name, altitude, category, bedsNumber, municipality);
		// prendo i comuni della provincia
		Map<Municipality, ArrayList<MountainHut>> comuniPerProvincia = this.province.get(municipality.getProvince());
		// se ho già il comune nella mappa, aggiungo solo il rifugio
		if(comuniPerProvincia.containsKey(municipality)){
			ArrayList<MountainHut> tmp = comuniPerProvincia.get(municipality);
			tmp.add(c);
		}
		else { // non ho comune, quindi lo aggiungo
			ArrayList<MountainHut> tmp = new ArrayList<>();
			tmp.add(c);
			comuniPerProvincia.put(municipality, tmp);
			this.comuni.add(municipality);
		}
		this.rifugi.add(c);
		return c;
	}

	/**
	 * Creates a new region and loads its data from a file.
	 * 
	 * The file must be a CSV file and it must contain the following fields:
	 * <ul>
	 * <li>{@code "Province"},
	 * <li>{@code "Municipality"},
	 * <li>{@code "MunicipalityAltitude"},
	 * <li>{@code "Name"},
	 * <li>{@code "Altitude"},
	 * <li>{@code "Category"},
	 * <li>{@code "BedsNumber"}
	 * </ul>
	 * 
	 * The fields are separated by a semicolon (';'). The field {@code "Altitude"}
	 * may be empty.
	 * 
	 * @param name
	 *            the name of the region
	 * @param file
	 *            the path of the file
	 */
	public static Region fromFile(String name, String file) {
		List<String> righe = readData(file);
		String[] parts = new String[7];
		Region r = new Region(name);
		int counter = 0;
		for(String s : righe){
			if(counter != 0){
				parts = s.split(";");
				Municipality m = r.createOrGetMunicipality(parts[1], parts[0], Integer.parseInt(parts[2]));
				if(!parts[4].isEmpty())
					r.createOrGetMountainHut(parts[3], Integer.parseInt(parts[4]), parts[5], Integer.parseInt(parts[6]), m);
				else
					r.createOrGetMountainHut(parts[3], null, parts[5], Integer.parseInt(parts[6]), m);
			}
			counter += 1; // skip prima riga di intestazione
		}
		return r;
	}

	/**
	 * Reads the lines of a text file.
	 *
	 * @param file path of the file
	 * @return a list with one element per line
	 */
	public static List<String> readData(String file) {
		try (BufferedReader in = new BufferedReader(new FileReader(file))) {
			return in.lines().collect(toList());
		} catch (IOException e) {
			System.err.println(e.getMessage());
			return new ArrayList<>();
		}
	}

	/**
	 * Count the number of municipalities with at least a mountain hut per each
	 * province.
	 * 
	 * @return a map with the province as key and the number of municipalities as
	 *         value
	 */
	//! QUESTO SENZA STREAM
	/* public Map<String, Long> countMunicipalitiesPerProvince() {
		Map<String, Long> mappa = new HashMap<>();
		long counter = 0;
		Map<Municipality, ArrayList<MountainHut>> tmp = new HashMap<>();
		for(String s : this.province.keySet()){
			tmp = this.province.get(s);
			for(Municipality m : tmp.keySet()) counter++;
			mappa.put(s, counter);
			counter = 0;

		}
		return mappa;
	} */
	//! QUESTO CON STREAM
	public Map<String, Long> countMunicipalitiesPerProvince() {
		return this.province.entrySet().stream()
				.collect(Collectors.toMap(Map.Entry::getKey,
						entry -> (long) entry.getValue().size()
				)); // la chiave sarebbe la getKey di this.province, ovvero la provincia, mentre il valore diventa da entry alla dimensione della mappa dei comuni per provincia
	}


	/**
	 * Count the number of mountain huts per each municipality within each province.
	 * 
	 * @return a map with the province as key and, as value, a map with the
	 *         municipality as key and the number of mountain huts as value
	 */
	public Map<String, Map<String, Long>> countMountainHutsPerMunicipalityPerProvince() {
		return this.province.entrySet().stream()
			.collect(Collectors.toMap(Map.Entry :: getKey, 
				entry -> entry.getValue().entrySet().stream()
					.collect(Collectors.toMap(e -> e.getKey().getName(), 
						e -> (long) e.getValue().size()
					)
				)
			));
	}

	/**
	 * Count the number of mountain huts per altitude range. If the altitude of the
	 * mountain hut is not available, use the altitude of its municipality.
	 * 
	 * @return a map with the altitude range as key and the number of mountain huts
	 *         as value
	 */
	public Map<String, Long> countMountainHutsPerAltitudeRange() {
		List<String> intervalli = new ArrayList<>();
		String s = this.getAltitudeRanges();
		intervalli = Arrays.asList(s.split(" "));
		long counter = 0;
		Integer h = 0;
		Map<String, Long> mappa = new HashMap<>();
		for(String x : intervalli){
			for(MountainHut m : this.rifugi){
					if(m.getAltitude().isPresent()){
						h = m.getAltitude().orElse(0); // per passare da Optional<Integer> a Integer
					}
					else{
						h = m.getMunicipality().getAltitude();
					}
					if(this.getAltitudeRange(h).equals(x)) counter++;
			}
			mappa.put(x, counter);
			counter = 0;
		}
		return mappa;
	}

	/**
	 * Compute the total number of beds available in the mountain huts per each
	 * province.
	 * 
	 * @return a map with the province as key and the total number of beds as value
	 */
	public Map<String, Integer> totalBedsNumberPerProvince() {
		return this.province.entrySet().stream()
			.collect(Collectors.toMap(Map.Entry::getKey, 
				entry -> entry.getValue().values().stream() // prendo la lista dei rifugi dalla mappa comune-rifugi
					.flatMap(List::stream) // trasformo tutti gli arraylist in un unico arraylist che trasformo in stream, visto che mi serve solo contare per provincia
					.mapToInt(MountainHut::getBedsNumber).sum())); // uso mapToInt per ottenere l'intero getBedsNumber dalla mappa per rifugio e li sommo
	}

	/**
	 * Compute the maximum number of beds available in a single mountain hut per
	 * altitude range. If the altitude of the mountain hut is not available, use the
	 * altitude of its municipality.
	 * 
	 * @return a map with the altitude range as key and the maximum number of beds
	 *         as value
	 */
	public Map<String, Optional<Integer>> maximumBedsNumberPerAltitudeRange() {
		// intervalli
		List<String> intervalli = new ArrayList<>();
		String s = this.getAltitudeRanges();
		intervalli = Arrays.asList(s.split(" "));

		Map<String, Optional<Integer>> mappa = new HashMap<>();

		for(String x : intervalli){
			Integer max_beds = null;
			for(MountainHut m : this.rifugi){
				Integer h = 0;
				if(m.getAltitude().isPresent()){
					h = m.getAltitude().get(); // per passare da Optional<Integer> a Integer
				}
				else{
					h = m.getMunicipality().getAltitude();
				}

				if(this.getAltitudeRange(h).equals(x)){
					if(max_beds == null || m.getBedsNumber() > max_beds){
						max_beds = m.getBedsNumber();
					}
				}
			}
			mappa.put(x, Optional.ofNullable(max_beds)); // uso Optional.ofNullable per castare Integer a Optional<Integer>
			max_beds = 0;
		}
		return mappa;
	}

	/**
	 * Compute the municipality names per number of mountain huts in a municipality.
	 * The lists of municipality names must be in alphabetical order.
	 * 
	 * @return a map with the number of mountain huts in a municipality as key and a
	 *         list of municipality names as value
	 */
	public Map<Long, List<String>> municipalityNamesPerCountOfMountainHuts() {
		Map<String, Long> countRifugiPerComune = this.rifugi.stream()
			.collect(Collectors.groupingBy( // facciamo la group by sul nome dei comuni dei rifugi e facciamo il counting (quindi avremo una mappa con chiave nome comune e valore il counting sui rifugi per comune)
				rifugio -> rifugio.getMunicipality().getName(),
				Collectors.counting()
			));
		
		Map<Long, List<String>> comuniPerCountRifugi = countRifugiPerComune
			.entrySet().stream().collect(Collectors.groupingBy(
				// dalla mappa precedente, facciamo la group by dove mettiamo come chiave il valore della mappa precedente, ovvero il numero di rifugi per comune, e come value usiamo mapping per creare una lista dei comuni prendendoli dalla mappa precedente con getKey (prima erano la chiave dell'altra mappa) e facendo toList per farne una lista
				Map.Entry::getValue, Collectors.mapping(
					Map.Entry::getKey, Collectors.toList()
				)
			));

		// ordinamento della mappa per ordine alfabetico sui valori (ovvero sulle liste dei comuni)
		comuniPerCountRifugi.values().forEach(Collections::sort);
		return comuniPerCountRifugi;
	}

}
