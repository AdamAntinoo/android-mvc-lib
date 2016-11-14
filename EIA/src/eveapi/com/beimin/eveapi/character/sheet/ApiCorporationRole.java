package com.beimin.eveapi.character.sheet;

import java.io.Serializable;

public class ApiCorporationRole implements Serializable {
	private long		roleID;
	private String	roleName;

	public long getRoleID() {
		return this.roleID;
	}

	public String getRoleName() {
		return this.roleName;
	}

	public void setRoleID(final long roleID) {
		this.roleID = roleID;
	}

	public void setRoleName(final String roleName) {
		this.roleName = roleName;
	}
}