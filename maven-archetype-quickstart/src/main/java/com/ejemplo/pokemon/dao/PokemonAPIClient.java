package com.ejemplo.pokemon.dao;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class PokemonAPIClient {
    private static final String BASE_URL = "https://pokeapi.co/api/v2/pokemon";
    
    public String getPokemonPage(int limit, int offset) throws Exception {
        String url = BASE_URL + "?limit=" + limit + "&offset=" + offset;
        
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet request = new HttpGet(url);
        HttpResponse response = httpClient.execute(request);
        return EntityUtils.toString(response.getEntity());
    }
}