package mx.com.mms.users.entities;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.*;

import lombok.*;

@Data
@Entity
@Table(name = "ROLES")
@NoArgsConstructor
public class Role implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "role_id", updatable = false, nullable = false, columnDefinition = "VARCHAR(255)")
	@JsonProperty(value = "id", index = 0)
	private String roleId;
	
	@Column(nullable = true)
	private String name;
	
	@OneToMany(mappedBy = "role", cascade = CascadeType.ALL)
	@JsonIgnore
	private Set<UserInRole> usersInRole;

	public Role(String name) {
		this.name = name;
	}
	
}
