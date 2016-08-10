package taskmanage.comm;

public class PersonBean extends Persistence {
	protected String personID;
	protected String personName;
	protected String password;

	public String getPersonID() {
		return personID;
	}
	
	public void setPersonID(String personID) {
		this.personID = personID;
	}
	
	public String getPersonName() {
		return personName;
	}
	
	public void setPersonName(String personName) {
		this.personName = personName;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}

	public boolean read(String key) throws CommException {
		return true;
	}
	
	public boolean write() throws CommException {
		return true;
	}
	
	public boolean inset() throws CommException {
		return true;
	}
	
	public boolean delete(String key) throws CommException {
		return true;
	}
}
