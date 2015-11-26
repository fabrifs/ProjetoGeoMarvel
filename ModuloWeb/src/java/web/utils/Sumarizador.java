package web.utils;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;

@Stateless
@LocalBean
public class Sumarizador {

    public void sumariza(String login) throws Exception {

        try {
            
            //Busca no WS de posições as posições do usuário passado
            HttpClient cliente = HttpClients.createDefault();
            HttpGet httpget = new HttpGet("http://localhost:8080/ModuloWeb/LP3Rest/lp3/posicoes/" + login);
            HttpResponse response = cliente.execute(httpget);
            System.out.println(response.getStatusLine());
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                InputStream instream = entity.getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(instream));
                StringBuilder out = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    out.append(line);
                }
                reader.close();
                instream.close();
                   
                //Aplica a folha xsl no xml retornado
                StringReader readerLine = new StringReader(out.toString());
                Source xmlSource = new StreamSource(readerLine);
                File xslFile = new File("C:\\Users\\Fabrizzio\\Desktop\\ProjetoLP3\\Apps\\ModuloWeb\\web\\dados\\xsl\\cenarioGpx.xsl");
                TransformerFactory transFact = TransformerFactory.newInstance();
                Transformer trans = transFact.newTransformer(new StreamSource(xslFile));
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                trans.transform(xmlSource, new StreamResult(bos));
                
                //Grava o arquivo com o xsl retornado
                PrintWriter writer = new PrintWriter("C:\\Users\\Fabrizzio\\Desktop\\ProjetoLP3\\Apps\\ModuloWeb\\web\\dados\\pontos.gpx", "UTF-8");
                writer.println(bos);
                writer.close();
            }
        } catch (Exception ex) {
            Logger.getLogger(Sumarizador.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}



