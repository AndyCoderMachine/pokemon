package com.ejemplo.pokemon.service;

import com.ejemplo.pokemon.dao.PokemonAPIClient;


public class PokemonService {
    private static PokemonService instance;
    private PokemonAPIClient pokemonAPIClient;
    
    private PokemonService() {
        pokemonAPIClient = new PokemonAPIClient();
    }
    
    public static synchronized PokemonService getInstance() {
        if (instance == null) {
            instance = new PokemonService();
        }
        return instance;
    }

    public String getPaginatedPokemon(int limit, int offset) throws Exception {
        return pokemonAPIClient.getPokemonPage(limit, offset);
    }
}