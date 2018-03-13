package com.shuga.heroes.client.entity;

public class Hero {

	private Long id;

	private String name;

	private Boolean status;

	private Sidekick sidekick;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public Sidekick getSidekick() {
		return sidekick;
	}

	public void setSidekick(Sidekick sidekick) {
		this.sidekick = sidekick;
	}

}
