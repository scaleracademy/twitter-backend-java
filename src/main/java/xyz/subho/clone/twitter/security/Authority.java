package xyz.subho.clone.twitter.security;

import org.springframework.security.core.GrantedAuthority;

public class Authority implements GrantedAuthority{
	
	private static final long serialVersionUID = -3711826889495714896L;
	private final String userAuthority; 

	public Authority(String userAuthority) {
		this.userAuthority = userAuthority;
	}

	@Override
	public String getAuthority() {
		return userAuthority;
	}

}
