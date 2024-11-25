package jobOffers; 
import java.util.*;
import java.util.stream.Collectors;


public class JobOffers  {

	public class Skill{
		private String skill;
		public Skill(String skill){
			this.skill = skill;
		}
		public String getSkill(){
			return this.skill;
		}
	}

	public class SkillLevel{
		private Skill s;
		private int level;
		public SkillLevel(Skill s, int level){
			this.s = s;
			this.level = level;
		}
		public Skill getSkill(){
			return this.s;
		}
		public int getLevel(){
			return this.level;
		}
	}

	public class Position{
		private String role;
		private Collection<SkillLevel> skillLevels = new ArrayList<>();
		private int media;
		public Position(String role, Collection<SkillLevel> skillLevels, int media){
			this.role = role;
			this.skillLevels = skillLevels;
		}
		public String getRole(){
			return this.role;
		}
		public Collection<SkillLevel> getSkillLevels(){
			return this.skillLevels;
		}
		public int getMedia(){
			return this.media;
		}
	}

	public class Candidate{
		private String name;
		private Collection<Skill> skills = new ArrayList<>();
		public Candidate(String name, Collection<Skill> skills){
			this.name = name;
			this.skills = skills;
		}
		public String getName(){
			return this.name;
		}
		public Collection<Skill> getSkills(){
			return this.skills;
		}
	}

	public class Application{
		private Candidate c;
		private Position p;
		public Application(Candidate c, Position p){
			this.c = c;
			this.p = p;
		}
		public Candidate getCandidate(){
			return this.c;
		}
		public Position getPosition(){
			return this.p;
		}
		@Override
		public String toString(){
			return this.c.getName()+":"+this.p.getRole();
		}
	}

	public class Consultant{
		private String name;
		private Collection<Skill> skills = new ArrayList<>();
		public Consultant(String name, Collection<Skill> skills){
			this.name = name;
			this.skills = skills;
		}
		public String getName(){
			return this.name;
		}
		public Collection<Skill> getSkills(){
			return this.skills;
		}
	}

	public class Valutation{
		private Consultant consulente;
		private Candidate candidato;
		private Collection<SkillLevel> skillLevels = new ArrayList<>();
		private int media;
		public Valutation(Consultant consulente, Candidate candidato, Collection<SkillLevel> skillLevels, int media){
			this.consulente = consulente;
			this.candidato = candidato;
			this.skillLevels = skillLevels;
			this.media = media;
		}
		public Consultant getConsultant(){
			return this.consulente;
		}
		public Candidate getCandidate(){
			return this.candidato;
		}
		public Collection<SkillLevel> getSkillLevels(){
			return this.skillLevels;
		}
		public int getMedia(){
			return this.media;
		}
	}

	public Collection<Skill> skills = new ArrayList<>();
	public Collection<Position> posizioni = new ArrayList<>();
	public Collection<Candidate> candidati = new ArrayList<>();
	public Collection<Application> candidature = new ArrayList<>();
	public Collection<Consultant> consulenti = new ArrayList<>();
	public Collection<Valutation> valutazioni = new ArrayList<>();
	public Collection<Application> eligibili = new ArrayList<>();
	

//R1
	public int addSkills (String... skills) {
		for(String s: skills){
			if(!this.skills.stream().map(entry->entry.getSkill()).collect(Collectors.toList()).contains(s)){
				Skill nuovo = new Skill(s);
				this.skills.add(nuovo);
			}
		}
		return this.skills.size();
	}
	
	public int addPosition (String position, String... skillLevels) throws JOException {
		if(this.posizioni.stream().map(entry->entry.getRole()).collect(Collectors.toList()).contains(position)){
			throw new JOException();
		}
		Collection<SkillLevel> tmp = new ArrayList<>();
		int counter = 0;
		for(String s: skillLevels){
			String[] parti = s.split(":");
			parti[1] = parti[1].trim();
			if(!this.skills.stream().map(entry->entry.getSkill()).collect(Collectors.toList()).contains(parti[0])){
				throw new JOException();
			}
			int valore = Integer.parseInt(parti[1]);
			if(valore<4 || valore >8){
				throw new JOException();
			}
			// se tutto bene
			counter += valore;
			Skill tSkill = null;
			for(Skill skill: this.skills){
				if(skill.getSkill().equals(parti[0])) tSkill = skill;
			}
			SkillLevel sl = new SkillLevel(tSkill, valore);
			tmp.add(sl);
		}
		int media = counter/skillLevels.length;
		Position nuovo = new Position(position, tmp, media);
		this.posizioni.add(nuovo);
		return media;
	}
	
//R2	
	public int addCandidate (String name, String... skills) throws JOException {
		if(this.candidati.stream().map(entry->entry.getName()).collect(Collectors.toList()).contains(name)){
			throw new JOException();
		}
		Collection<Skill> tmp = new ArrayList<>();
		for(String s: skills){
			if(!this.skills.stream().map(entry->entry.getSkill()).collect(Collectors.toList()).contains(s)){
				throw new JOException();
			}
			for(Skill skill: this.skills){
				if(skill.getSkill().equals(s)) tmp.add(skill);
			}
		}
		Candidate nuovo = new Candidate(name, tmp);
		this.candidati.add(nuovo);
		return skills.length;
	}
	
	public List<String> addApplications (String candidate, String... positions) throws JOException {
		if(!this.candidati.stream().map(entry->entry.getName()).collect(Collectors.toList()).contains(candidate)){
			throw new JOException();
		}
		Candidate cTmp = null;
		for(Candidate c: this.candidati){
			if(c.getName().equals(candidate)) cTmp = c;
		}

		Collection<Position> selezionate = new ArrayList<>();
		for(String s: positions){
			if(!this.posizioni.stream().map(entry->entry.getRole()).collect(Collectors.toList()).contains(s)){
				throw new JOException();
			}
			for(Position p: this.posizioni){
				if(p.getRole().equals(s)) selezionate.add(p);
			}
		}
		for(Position p: selezionate){
			for(SkillLevel sl: p.getSkillLevels()){
				boolean flag = false;
				for(Skill s: cTmp.getSkills()){
					if(s.getSkill().equals(sl.getSkill().getSkill())){
						flag = true;
					}
				}
				if(flag == false){
					throw new JOException();
				}
			}
		}
		for(Position p: selezionate){
			Application nuovo = new Application(cTmp, p);
			this.candidature.add(nuovo);
		}
		List<String> finale = new ArrayList<>();
		for(Application a: this.candidature){
			if(a.getCandidate().getName().equals(cTmp.getName())) finale.add(a.toString());
		}
		finale.sort((s1,s2)->s1.compareTo(s2));
		return finale;
	} 
	
	public TreeMap<String, List<String>> getCandidatesForPositions() {
		return this.candidature.stream().sorted((s1,s2)->s1.getCandidate().getName().compareTo(s2.getCandidate().getName())).sorted((s1,s2)->s1.getPosition().getRole().compareTo(s2.getPosition().getRole())).collect((Collectors.groupingBy(entry->entry.getPosition().getRole(), TreeMap::new,Collectors.mapping(entry->entry.getCandidate().getName(), Collectors.toList()))));
	}
	
	
//R3
	public int addConsultant (String name, String... skills) throws JOException {
		if(this.consulenti.stream().map(entry->entry.getName()).collect(Collectors.toList()).contains(name)){
			throw new JOException();
		}
		Collection<Skill> tmp = new ArrayList<>();
		for(String s: skills){
			if(!this.skills.stream().map(entry->entry.getSkill()).collect(Collectors.toList()).contains(s)){
				throw new JOException();
			}
			for(Skill skill: this.skills){
				if(skill.getSkill().equals(s)) tmp.add(skill);
			}
		}
		Consultant nuovo = new Consultant(name, tmp);
		this.consulenti.add(nuovo);
		return skills.length;
	}
	
	public Integer addRatings (String consultant, String candidate, String... skillRatings)  throws JOException {
		if(!this.consulenti.stream().map(entry->entry.getName()).collect(Collectors.toList()).contains(consultant)){
			throw new JOException();
		}
		if(!this.candidati.stream().map(entry->entry.getName()).collect(Collectors.toList()).contains(candidate)){
			throw new JOException();
		}
		Consultant cTmp = null;
		for(Consultant c: this.consulenti){
			if(c.getName().equals(consultant)) cTmp = c;
		}
		Candidate candTmp = null;
		for(Candidate c: this.candidati){
			if(c.getName().equals(candidate)) candTmp = c;
		}
		Collection<SkillLevel> tmp = new ArrayList<>();
		int counter = 0;
		for(String s: skillRatings){
			String[] parti = s.split(":");
			parti[1] = parti[1].trim();
			if(!this.skills.stream().map(entry->entry.getSkill()).collect(Collectors.toList()).contains(parti[0])){
				throw new JOException();
			}
			for(Skill skill: candTmp.getSkills()){
				if(!cTmp.getSkills().stream().map(entry->entry.getSkill()).collect(Collectors.toList()).contains(skill.getSkill())){
					throw new JOException();
				}
			}
			int valore = Integer.parseInt(parti[1]);
			if(valore<4 || valore >10){
				throw new JOException();
			}
			// se tutto bene
			counter += valore;
			Skill tSkill = null;
			for(Skill skill: this.skills){
				if(skill.getSkill().equals(parti[0])) tSkill = skill;
			}
			SkillLevel sl = new SkillLevel(tSkill, valore);
			tmp.add(sl);
		}
		int media = counter/skillRatings.length;
		Valutation nuovo = new Valutation(cTmp, candTmp, tmp, media);
		this.valutazioni.add(nuovo);
		return media;
	}
	
//R4
	public List<String> discardApplications() {
		List<String> finale = new ArrayList<>();
		for(Application a: this.candidature){
			for(Valutation v: this.valutazioni){
				if(a.getCandidate().getName().equals(v.getCandidate().getName())){
					boolean flag = false;
					for(SkillLevel sl1: a.getPosition().getSkillLevels()){
						for(SkillLevel sl2: v.getSkillLevels()){
							if(sl1.getSkill().getSkill().equals(sl2.getSkill().getSkill())){
								if(sl1.getLevel() > sl2.getLevel()){
									finale.add(a.toString());
									flag = true;
								}
							}
						}
					}
					if(flag == false && this.valutazioni.stream().map(entry->entry.getCandidate().getName()).collect(Collectors.toList()).contains(a.getCandidate().getName())) this.eligibili.add(a);
				}
			}
		}
		for(Application a: this.candidature){
			if(!this.valutazioni.stream().map(entry->entry.getCandidate().getName()).collect(Collectors.toList()).contains(a.getCandidate().getName())){
				finale.add(a.toString());
			}
		}
		finale.sort((s1,s2)->s1.compareTo(s2));
		return finale;
	}
	
	 
	public List<String> getEligibleCandidates(String position) {
		return this.eligibili.stream().map(entry->entry.getCandidate().getName()).sorted((s1,s2)->s1.compareTo(s2)).distinct().collect(Collectors.toList());
	}
	

	
}

		
