/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RestDatabase.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.json.JSONObject;
import org.json.XML;

/**
 *
 * @author Mihai Serban Copil
 */
public class Jackson {
    
        private ObjectMapper mapper;
    
    private Jackson() {
         mapper = new ObjectMapper();
    }
    
    public Response asResource(Object object, String contentType) throws Exception {
        String body;
        String mediaType;
        if (contentType != null && contentType.equals(MediaType.APPLICATION_XML)) {
            body = XML.toString(new JSONObject(object));
            mediaType = MediaType.APPLICATION_XML;
        } else {
            body = mapper.writeValueAsString(object);
            mediaType = MediaType.APPLICATION_JSON;
        }
        return Response.ok(body, mediaType).build();
    }
    
    public HashMap<String, Object> getResource(String strObject) {
        try {
        return mapper.readValue(strObject, new TypeReference<HashMap<String, Object>>(){
            
        });
        } catch (Exception ex) {
            return null;
        }
    }
    
    private static Jackson instance;
    public static Jackson getInstance() {
        if (instance == null) instance = new Jackson();
        return instance;
    }

    
}
