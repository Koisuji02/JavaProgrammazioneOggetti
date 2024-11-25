package jobOffers; 
import java.util.*;
import java.util.stream.Collectors;

import javax.swing.RowFilter.Entry;


public class JobOffers  {

	public class JobOffer{
		private Map<String,Integer> skillsRequired = new HashMap<>();
		private String position;
		public JobOffer(String position, Map<String,Integer> skillsRequired){
			this.position = position;
			this.skillsRequired = skillsRequired;
		}
		public String getPosition(){
			return this.position;
		}
		public Map<String,Integer> getSkills(){
			return this.skillsRequired;
		}
	}

	public class Candidate{
		private String name;
		private Collection<String> skills = new ArrayList<>();
		public Candidate(String name, Collection<String> skills){
			this.name = name;
			this.skills = skills;
		}
		public String getName(){
			return this.name;
		}
		public Collection<String> getSkills(){
			return this.skills;
		}
	}

	public class Consultant{
		private String name;
		private Collection<String> skills = new ArrayList<>();
		public Consultant(String name, Collection<String> skills){
			this.name = name;
			this.skills = skills;
		}
		public String getName(){
			return this.name;
		}
		public Collection<String> getSkills(){
			return this.skills;
		}
	}

	public class Rating{
		private Consultant consultant;
		private Candidate candidate;
		private Map<String,Integer> skillRatings = new HashMap<>();
		public Rating(Consultant consulente, Candidate candidate, Map<String,Integer> skillRatings){
			this.candidate = candidate;
			this.consultant = consulente;
			this.skillRatings = skillRatings;
		}
		public Consultant getConsultant(){
			return this.consultant;
		}
		public Candidate getCandidate(){
			return this.candidate;
		}
		public Map<String,Integer> getSkillsRatings(){
			return this.skillRatings;
		}

	}

	public Collection<JobOffer> offerte = new ArrayList<>();
	public Collection<String> skills = new ArrayList<>();
	public Collection<Candidate> candidati = new ArrayList<>();
	public TreeMap<String,List<String>> postoCandidati = new TreeMap<>(); // già ordinato per posto di lavoro per TreeMap
	public Collection<Consultant> consulenti = new ArrayList<>();
	public Collection<Rating> ratings = new ArrayList<>();
	public TreeMap<String,List<String>> postoCandidatiAdatti = new TreeMap<>(); // quelli ammissibili
	public List<String> scartate = new ArrayList<>();

//R1
	public int addSkills (String... skills) {
		for(String s: skills){
			if(!this.skills.contains(s)) this.skills.add(s);
		}
		return this.skills.size();
	}
	
	public int addPosition (String position, String... skillLevels) throws JOException {
		if(this.offerte.stream().map(entry->entry.getPosition()).collect(Collectors.toList()).contains(position)) throw new JOException("Lavoro già presente!\n");
		for(String s: skillLevels){
			String[] tmp = s.split(":");
			if(!this.skills.contains(tmp[0])) throw new JOException("Capacità non presente!\n");
			if(Integer.parseInt(tmp[1]) < 4 || Integer.parseInt(tmp[1]) > 8) throw new JOException("Valore non adatto!\n");
		}

		Map<String,Integer> mappa = new HashMap<>();
		int totale = 0;
		for(String s: skillLevels){
			String[] tmp = s.split(":");
			totale += Integer.parseInt(tmp[1]);
			mappa.put(tmp[0],Integer.parseInt(tmp[1]));
		}
		JobOffer nuovo = new JobOffer(position, mappa);
		this.offerte.add(nuovo);
		int finale = Math.round(totale/skillLevels.length);
		return finale;
	}
	
//R2	
	public int addCandidate (String name, String... skills) throws JOException {
		if(this.candidati.stream().map(entry->entry.getName()).collect(Collectors.toList()).contains(name)) throw new JOException("Candidato già presente!\n");
		for(String s: skills){
			if(!this.skills.contains(s)) throw new JOException("Capacità non definita!\n");
		}

		Collection<String> tmp = new ArrayList<>();
		for(String s: skills){
			tmp.add(s);
		}
		Candidate nuovo = new Candidate(name, tmp);
		this.candidati.add(nuovo);
		return tmp.size();
	}
	
	public List<String> addApplications (String candidate, String... positions) throws JOException {
		if(!this.candidati.stream().map(entry->entry.getName()).collect(Collectors.toList()).contains(candidate)) throw new JOException("Candidato non esistente!\n");
		for(String s: positions){
			if(!this.offerte.stream().map(entry->entry.getPosition()).collect(Collectors.toList()).contains(s)) throw new JOException("Posizione non esistente!\n");
		}

		List<String> finale = new ArrayList<>();
		for(String p: positions){
			for(JobOffer k: this.offerte){
				if(p.equals(k.getPosition())){
					for(Candidate c: this.candidati){
						if(c.getName().equals(candidate)){
							int counter = 0;
							for(String h: c.getSkills()){
								for(String n : k.getSkills().keySet()){
									if(h.equals(n)) counter += 1;
								}
							}
							if(counter == k.getSkills().keySet().size()){
								if(this.postoCandidati.containsKey(k.getPosition())){
									this.postoCandidati.get(k.getPosition()).add(c.getName());
								} else{
									List<String> tmp = new ArrayList<>();
									tmp.add(c.getName());
									this.postoCandidati.put(k.getPosition(),tmp);
								}
								finale.add(c.getName()+":"+k.getPosition());
							} else{
								throw new JOException("Candidato non ha requisiti!\n");
							}
						}
					}
				}
			}
		}
		finale.sort((s1,s2)->s1.compareTo(s2));
		return finale;
	} 
	
	public TreeMap<String, List<String>> getCandidatesForPositions() {
		for(Map.Entry<String,List<String>> entry: this.postoCandidati.entrySet()){
			entry.getValue().sort((s1,s2)->s1.compareTo(s2));
		}
		return this.postoCandidati;
	}
	
	
//R3
	public int addConsultant (String name, String... skills) throws JOException {
		if(this.consulenti.stream().map(entry->entry.getName()).collect(Collectors.toList()).contains(name)) throw new JOException("Consulente già presente!\n");
		for(String s: skills){
			if(!this.skills.contains(s)) throw new JOException("Capacità non definita!\n");
		}
		
		Collection<String> tmp = new ArrayList<>();
		for(String s: skills){
			tmp.add(s);
		}
		Consultant nuovo = new Consultant(name, tmp);
		this.consulenti.add(nuovo);
		return tmp.size();
	}
	
	public Integer addRatings (String consultant, String candidate, String... skillRatings)  throws JOException {
		if(!this.consulenti.stream().map(entry->entry.getName()).collect(Collectors.toList()).contains(consultant)) throw new JOException("Consulente non presente!\n");
		if(!this.candidati.stream().map(entry->entry.getName()).collect(Collectors.toList()).contains(candidate)) throw new JOException("Candidato non presente!\n");
		for(String s: skillRatings){
			String[] tmp = s.split(":");
			if(!this.skills.contains(tmp[0])) throw new JOException("Capacità non presente!\n");
			if(Integer.parseInt(tmp[1]) < 4 || Integer.parseInt(tmp[1]) > 10) throw new JOException("Valore non adatto!\n");
		}
		Consultant tmp1 = null;
		Candidate tmp2 = null;
		for(Consultant c: this.consulenti){
			if(c.getName().equals(consultant)){
				tmp1 = c;
				for(Candidate a: this.candidati){
					if(a.getName().equals(candidate)){
						tmp2 = a;
						for(String h: a.getSkills()){
							if(!c.getSkills().contains(h)) throw new JOException("Capacità non contenuta!\n");
						}
						break;
					}
				}
			}
		}

		Map<String,Integer> mappa = new HashMap<>();
		int totale = 0;
		for(String s: skillRatings){
			String[] tmp = s.split(":");
			totale += Integer.parseInt(tmp[1]);
			mappa.put(tmp[0],Integer.parseInt(tmp[1]));
		}
		Rating nuovo = new Rating(tmp1, tmp2, mappa);
		this.ratings.add(nuovo);
		int finale = Math.round(totale/skillRatings.length);
		return finale;
	}
	
//R4
	public List<String> discardApplications() {
		for(Map.Entry<String,List<String>> entry: this.postoCandidati.entrySet()){
			for(JobOffer j: this.offerte){
				if(j.getPosition().equals(entry.getKey())){ // lavoro cercato
					for(String e: entry.getValue()){
						for(Candidate c: this.candidati){
							if(c.getName().equals(e)){ // candidato cercato
								for(Rating r: this.ratings){
									if(r.getCandidate().getName().equals(e)){ // rating cercato
										for(Map.Entry<String,Integer> entry2: r.getSkillsRatings().entrySet()){
											for(Map.Entry<String,Integer> entry3: j.getSkills().entrySet()){
												if(entry2.getKey().equals(entry3.getKey())){
													if(entry2.getValue().compareTo(entry3.getValue()) < 0){
														String s = e+":"+entry.getKey();
														this.scartate.add(s);
													}else {
														if(this.postoCandidatiAdatti.containsKey(j.getPosition())){
															this.postoCandidatiAdatti.get(j.getPosition()).add(c.getName());
														} else{
															List<String> tmp = new ArrayList<>();
															tmp.add(c.getName());
															this.postoCandidatiAdatti.put(j.getPosition(),tmp);
														}
													}
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
		List<String> candidatiDaTogliere = new ArrayList<>();
		for(Candidate c: this.candidati){
			if(!this.ratings.stream().map(entry->entry.getCandidate().getName()).collect(Collectors.toList()).contains(c.getName())){
				candidatiDaTogliere.add(c.getName());
			}
		}
		for(Map.Entry<String,List<String>> entry: this.postoCandidati.entrySet()){
			for(String c: candidatiDaTogliere){
				for(String h: entry.getValue()){
					if(c.equals(h)) this.scartate.add(c+":"+entry.getKey());
				}
			}
		}
		this.scartate.sort((s1,s2)->s1.compareTo(s2));
		return this.scartate;
	}
	
	 
	public List<String> getEligibleCandidates(String position) {
		List<String> finale = this.postoCandidatiAdatti.get(position);

		for(String s: this.scartate){
			String[] tmp = s.split(":");
			if(tmp[1].equals(position)){
				finale.remove(tmp[0]);
			}
		}
		return finale;
	}
	

	
}

		
