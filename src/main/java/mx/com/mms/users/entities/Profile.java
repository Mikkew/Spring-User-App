package mx.com.mms.users.entities;

import java.io.Serializable;
import java.util.*;

import javax.persistence.*;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.*;

@Data
@Entity
@Table(name = "PROFILE")
public class Profile implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "profile_id", updatable = false, nullable = false, columnDefinition = "VARCHAR(255)")
	@JsonProperty(value = "id", index = 0)
	private String profileId;
	
	@Column(name = "first_name", nullable = false)
	@JsonProperty(value = "first_name")
	private String firstName;
	
	@Column(name = "last_name", nullable = false)
	@JsonProperty(value = "last_name")
	private String lastName;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(pattern="yyyy-MM-dd")
	@Column(name = "birth_date", nullable = false)
	@Temporal(TemporalType.DATE)
	@JsonProperty(value = "birth_date")
	private Date birthDate;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(
			name = "user_id", 
			referencedColumnName = "user_id",
			foreignKey = @ForeignKey(name="FK PROFILE_USER"))
	@JsonProperty(access = Access.READ_ONLY)
	private User user;
	
	@OneToMany(mappedBy = "profile")
	@JsonIgnore
	private List<Address> addresses;	
	
	@Transient
	@JsonProperty(access = Access.WRITE_ONLY)
	private String userId;
}
