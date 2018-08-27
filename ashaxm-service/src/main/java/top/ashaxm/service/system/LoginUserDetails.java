package top.ashaxm.service.system;

import java.io.Serializable;

import org.springframework.security.core.userdetails.UserDetails;

public interface LoginUserDetails extends Serializable, UserDetails {
	long getId();
	int getRole();
}
