package mx.com.mms.users.entities;

import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.*;

@Data
@Entity
@Table(name = "USER_IN_ROLE")
public class UserInRole implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "VARCHAR(255)")
	private String id;
	
	@ManyToOne(cascade = CascadeType.ALL, targetEntity = User.class)
	@JoinColumn(
			name = "user_id", 
			referencedColumnName = "user_id",
			foreignKey = @ForeignKey(name="FK_USERINROLE_USER"))
	@JsonProperty(access = Access.READ_ONLY )
	private User user;
	
	@ManyToOne(cascade = CascadeType.ALL, targetEntity = Role.class)
	@JoinColumn(
			name = "role_id", 
			referencedColumnName = "role_id",
			foreignKey = @ForeignKey(name="FK_USERINROLE_ROLE"))
	@JsonProperty(access = Access.READ_ONLY)
	private Role role;
	
	@Transient
	@JsonProperty(value = "user_id", access = Access.WRITE_ONLY)
	private String userId;
	
	@Transient
	@JsonProperty(value = "role_id", access = Access.WRITE_ONLY)
	private String roleId;
	
}
