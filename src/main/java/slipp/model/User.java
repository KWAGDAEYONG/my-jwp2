package slipp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
public class User {
	@Id
	@GeneratedValue
	@JsonProperty
	private Long id;
	
	@JsonProperty
	@Column(unique=true, length=20, nullable=false)
	private String userId;
	
	@Column(length=20, nullable=false)
	private String password;
	
	@Column(length=20, nullable=false)
	@JsonProperty
	private String name;
	
	@Column(length=30)
	private String email;
	
	public boolean matchPassword(String newPassword) {
		if (this.password == null) {
			return false;
		}
		
		return this.password.equals(newPassword);
	}
	
	public void update(User user) {
		if(!matchPassword(user.password)) {
			throw new IllegalArgumentException("비밀번호가 다릅니다.");
		}
			
		this.name = user.name;
		this.email = user.email;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public boolean matchId(Long id) {
		return this.id.equals(id);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", password=" + password + ", name=" + name + ", email=" + email + "]";
	}
}
