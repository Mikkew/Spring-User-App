package mx.com.mms.users.entities;

import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.*;

@Data
@Entity
@Table(name = "ADDRESS")
public class Address implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "address_id", updatable = false, nullable = false, columnDefinition = "VARCHAR(255)")
	@JsonProperty(value = "id", index = 0)
	@Getter
	private String addressId;
	
	@Column(nullable = true)
	private String street;
	
	@Column(nullable = true)
	private String number;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(
			name = "profile_id", 
			referencedColumnName = "profile_id",
			nullable = true,
			foreignKey = @ForeignKey(name = "FK_ADDRESS_PROFILE"))
	@JsonProperty(access = Access.READ_ONLY)
	private Profile profile;
	
	@Transient
	@JsonProperty(value = "profile_id", access = Access.WRITE_ONLY)
	private String profileId;
	
}
