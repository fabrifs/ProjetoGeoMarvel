package rest.cliente;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;

public class DadosXml {

	public static void main(String[] args) {

		try {
			FileReader in = new FileReader("C:\\Users\\Fabrizzio\\Desktop\\ProjetoLP3\\Apps\\ModuloWeb\\web\\dados\\takase.xml");
			
			BufferedReader lerArq = new BufferedReader(in);
			
			List<String> lista = new ArrayList<String>();	

			String linha = (String) lerArq.readLine();
			if(linha.startsWith("<?xml version=")){
				linha = lerArq.readLine();
			}
			if(linha.equals("<collection>")){
				linha = lerArq.readLine();
			}
			
	        String lat = "", lon = "", timestamp="";

			while (linha != null) {
				String pdata = "<posicao><login>entrega@entrega.com</login>";
				if(linha.contains("<posicao>")){					
					linha = lerArq.readLine();
				}
				if(linha.contains("<id>")){
					linha = lerArq.readLine();
				}
				if(linha.contains("<lat>")){
					lat = linha;
					linha = lerArq.readLine();
					lat.replace(" ", "");
					//System.out.println(linha);
				}
				if(linha.contains("<login>")){
					linha = lerArq.readLine();
				}
				if(linha.contains("<lon>")){
					lon = linha;
					linha = lerArq.readLine();
					lon.replace(" ", "");
				}
				if(linha.contains("<timestamp>")){
					timestamp = linha;
					linha = lerArq.readLine();
					timestamp.replace(" ", "");
				}
				linha = lerArq.readLine();
				pdata += timestamp + lat + lon + "</posicao>";
				if(!pdata.equals("<posicao><login>entrega@entrega.com</login></posicao>")){
					lista.add(pdata);
				}
			}
			
			for(String s : lista){
				ws(s);
			}
	
			in.close();

			System.out.println("ok");
			
		} catch (Exception e) {
			System.err.printf("Erro na abertura do arquivo: %s.\n", e.getMessage());
		}
		System.out.println();
	}
	
	public static void ws(String chamada)throws Exception{
        HttpClient cliente = HttpClients.createDefault();
        HttpPut httpput = new HttpPut("http://localhost:8080/ModuloWeb/LP3Rest/lp3/novaposicao");
        StringEntity se = new StringEntity(chamada, ContentType.TEXT_XML);
        httpput.setEntity(se);
        cliente.execute(httpput);	
		
	}

}
