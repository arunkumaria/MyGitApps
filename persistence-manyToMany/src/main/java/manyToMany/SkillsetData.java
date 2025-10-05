package manyToMany;

public class SkillsetData {
	private int id;
	private String skillName;

	public SkillsetData() {
	}

	public SkillsetData(String skillName) {
		this.skillName = skillName;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSkillName() {
		return skillName;
	}

	public void setSkillName(String skillName) {
		this.skillName = skillName;
	}

	public boolean equals(Object obj) {
		// This method is definitely required in order to check whether any two
		// elements/objects are equal
		if (obj == null)
			return false;
		if (!this.getClass().equals(obj.getClass()))
			return false;

		SkillsetData obj2 = (SkillsetData) obj;
		if ((this.id == obj2.getId()) && (this.skillName.equals(obj2.getSkillName()))) {
			return true;
		}
		return false;
	}

	// collections calculate the hash value for a given key using the hashCode()
	// method.
	public int hashCode() {

		// This method is definitely required in order to check whether any two
		// elements/objects are equal
		int tmp = 0;
		tmp = (id + skillName).hashCode();
		return tmp;
	}
}