package BasicQueue;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.web.servlet.server.Encoding;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class ObjectSerialize {
    public static byte[] Serialize (Object obj){
        if(obj == null){
            return null;
        }
        try {
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(obj);
            return json.getBytes(StandardCharsets.UTF_8);
        }catch(IOException e){
            e.printStackTrace();
            return null;
        }

    }
}
