package top.ashaxm.service.system;

import java.util.Collection;

import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class LoginUser extends User implements LoginUserDetails,CredentialsContainer {

	private static final long serialVersionUID = 1L;
	
	private long id;			// Y ----
	private int role;
	
	public LoginUser(long id,String username, String password, boolean enabled,
			boolean accountNonExpired, boolean credentialsNonExpired,
			boolean accountNonLocked,
			Collection<? extends GrantedAuthority> authorities) {
		super(username, password, enabled, accountNonExpired, credentialsNonExpired,
				accountNonLocked, authorities);
		this.id=id;
	}
    public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	public int getRole() {
		return role;
	}
	public void setRole(int role) {
		this.role = role;
	}
}
