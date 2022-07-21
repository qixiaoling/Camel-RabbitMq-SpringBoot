package BasicQueue;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class DeSerialize {
    public static Object Deserialize(byte[] arrBytes){
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(arrBytes, Object.class);
        }catch(IOException e){
            e.printStackTrace();
            return null;
        }
    }
}
