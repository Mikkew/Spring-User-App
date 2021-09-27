package mx.com.mms.users.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Data
@Entity
@Table(name = "USERS")
public class User implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "user_id", updatable = false, nullable = false, columnDefinition = "VARCHAR(255)")
	@JsonProperty(value = "id", index = 0)
	private String userId;
	
	@Column(nullable = false)
	private String username;
	
	@Column(nullable = false)
	private String password;
	
	@OneToOne(cascade = CascadeType.ALL, targetEntity = Profile.class, mappedBy = "user")
	@JsonIgnore
	private Profile profile;
	
	@OneToMany(cascade = CascadeType.ALL, targetEntity = UserInRole.class, mappedBy = "user")
	@JsonIgnore
	private List<UserInRole> roleUsers;
	
}
