package com.ejemplo.pokemon.soap;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.xml.ws.Endpoint;

import com.ejemplo.pokemon.service.PokemonService;
import org.json.JSONArray;
import org.json.JSONObject;

@WebService
public class PokemonSOAPService {
    private PokemonService pokemonService = PokemonService.getInstance();

    @WebMethod
    public String getPokemon(int limit, int offset) {
        try {
            String jsonResponse = pokemonService.getPaginatedPokemon(limit, offset);
            // Convertir el JSON en XML
            String xmlResponse = convertJsonToXml(jsonResponse);
            return xmlResponse;
        } catch (Exception e) {
            e.printStackTrace();
            return "<error>Error fetching Pokémon data</error>";
        }
    }

    private String convertJsonToXml(String jsonResponse) {
        JSONObject json = new JSONObject(jsonResponse);
        StringBuilder xml = new StringBuilder();

        xml.append("<pokemonData>");
        xml.append("<count>").append(json.getInt("count")).append("</count>");
        
        // Manejar el campo 'next'
        if (!json.isNull("next")) {
            xml.append("<next>").append(json.getString("next")).append("</next>");
        } else {
            xml.append("<next>").append("null").append("</next>");
        }

        // Manejar el campo 'previous'
        if (!json.isNull("previous")) {
            xml.append("<previous>").append(json.getString("previous")).append("</previous>");
        } else {
            xml.append("<previous>").append("null").append("</previous>");
        }

        // Manejar los resultados (Pokémon)
        xml.append("<results>");
        JSONArray results = json.getJSONArray("results");
        for (int i = 0; i < results.length(); i++) {
            JSONObject pokemon = results.getJSONObject(i);
            xml.append("<pokemon>");
            xml.append("<name>").append(pokemon.getString("name")).append("</name>");
            xml.append("<url>").append(pokemon.getString("url")).append("</url>");
            xml.append("</pokemon>");
        }
        xml.append("</results>");

        xml.append("</pokemonData>");
        return xml.toString();
    }

    public static void main(String[] args) {
        Endpoint.publish("http://localhost:8089/pokemon", new PokemonSOAPService());
        System.out.println("SOAP Service is running...");
    }
}
