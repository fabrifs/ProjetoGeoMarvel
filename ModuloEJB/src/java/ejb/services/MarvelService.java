/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.services;

import ejb.beans.UsuarioBean;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import shared.entities.Usuario;
import utils.Hash;


@Stateless
@LocalBean
@Path("/marvel/")
public class MarvelService {

    @EJB
    UsuarioBean usuarioBean;

    Hash hash = new Hash();

    @GET
    @Path("/listaPopup/{login}")
    @Produces({"application/text"})
    public String listaPopup(@PathParam("login") final String login) {
        List<Usuario> usuarios = new ArrayList<Usuario>();
        usuarios = usuarioBean.listLogin(login);
        Usuario u = usuarios.get(0);

        String apikey = "62cc7f7bd41e3346a1af737e0449428b";
        String privatekey = "a9e5cccf7acc7f9ce2555471b5b6fc51d5725df6";
        String urlbase = "http://gateway.marvel.com/v1/public/characters";
        //Criação de um timestamp
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("MMddyyyyhhmmss");
        String ts = sdf.format(date);

        //Criação do HASH
        String hashStr = hash.MD5(ts + privatekey + apikey);
        String uri;
        String nomeAvatar = u.getAvatar();
        //Conversão necessária para aplicar o %20 no nome
        
        //String[] name = nomeAvatar.split(" ");
        //String nomeOk = name[0] + "%20" + name[1];
        nomeAvatar.replace(" ", "%20");
        //url de consulta
        uri = urlbase + "?nameStartsWith=" + nomeAvatar + "&ts=" + ts + "&apikey=" + apikey + "&hash=" + hashStr;
        //System.out.println(uri);
        try {
            HttpClient cliente = HttpClients.createDefault();

            //HttpHost proxy = new HttpHost("172.16.0.10", 3128, "http");
            //RequestConfig config = RequestConfig.custom().setProxy(proxy).build();
            HttpGet httpget = new HttpGet(uri);
           // httpget.setConfig(config);
            HttpResponse response = cliente.execute(httpget);
            System.out.println("----------------------------------------");
            System.out.println(response.getStatusLine());
            Header[] h = response.getAllHeaders();

            for (Header head : h) {
                System.out.println(head.getValue());
            }

            HttpEntity entity = response.getEntity();
            
            StringReader readerLine = null;
            if (entity != null) {
                InputStream instream = entity.getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(instream));
                StringBuilder out = new StringBuilder();
                String line;
                
                line = reader.readLine();
                readerLine = new StringReader(line);
                
                reader.close();
                instream.close();
                
                JSONObject jsonObject = (JSONObject) new JSONParser().parse(line);
                JSONObject jsonObjectData = (JSONObject) jsonObject.get("data");
                JSONArray lang = (JSONArray) jsonObjectData.get("results");
                JSONObject jsonObjectDados = (JSONObject) lang.get(0);

                JSONObject jsonPath = (JSONObject) jsonObjectDados.get("thumbnail");

                String nomeMarvel = (String) jsonObjectDados.get("name");
                String descricao = (String) jsonObjectDados.get("description");
                String urlImg = (String) jsonPath.get("path");
                
                String result = "<p>Personagem: " + nomeMarvel + "</p> <br />";
                result += "<p>Descrição: " + descricao + "</p> <br />";
                result += "<img src='" + urlImg + ".jpg' alt='foto'/ height='200' width='200'>";
                return result;

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
       return null; 
    }
    
}