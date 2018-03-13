package com.shuga.heroes.client.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.stereotype.Component;

import com.shuga.heroes.client.entity.Hero;
import com.shuga.heroes.client.entity.Sidekick;

@Component
public class ApplicationService {

	private static final String URL_API = "http://localhost:8080";

	private Client client;

	@PostConstruct
	protected void init() {
		client = ClientBuilder.newClient();
	}

	public Response createHero(Hero hero) {
		return client.target(URL_API + "/createHero").request(MediaType.APPLICATION_JSON)
				.post(Entity.entity(hero, MediaType.APPLICATION_JSON));
	}

	public Response createSidekic(Sidekick sidekick) {
		return client.target(URL_API + "/createSidekick").request(MediaType.APPLICATION_JSON)
				.post(Entity.entity(sidekick, MediaType.APPLICATION_JSON));
	}

	public ArrayList<Sidekick> getAllSidekicks() {
		return (ArrayList<Sidekick>) client.target(URL_API + "/sidekicks").request(MediaType.APPLICATION_JSON)
				.get(new GenericType<List<Sidekick>>() {
				});
	}

	public ArrayList<Hero> getAllHeroes() {
		return (ArrayList<Hero>) client.target(URL_API + "/heroes").request(MediaType.APPLICATION_JSON)
				.get(new GenericType<List<Hero>>() {
				});
	}

	public void releaseConnection() {
		this.client.close();
	}

}
