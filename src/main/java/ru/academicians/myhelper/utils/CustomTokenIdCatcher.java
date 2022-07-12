package ru.academicians.myhelper.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.BadCredentialsException;

import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ru.academicians.myhelper.defaults.DefaultKeys.BAD_TOKEN;

public class CustomTokenIdCatcher {
    private final Base64.Decoder decoder = Base64.getUrlDecoder();
    public CustomTokenIdCatcher() {

    }

    public long getIdFromToken(String token){
        ObjectMapper mapper = new ObjectMapper();

        String[] chunks = token.split("\\.");

        String payload = new String(decoder.decode(chunks[1]));
        long id = -1;

        try {

            TypeReference<HashMap<String, Object>> typeRef
                    = new TypeReference<HashMap<String, Object>>() {
            };
            Map<String, Object> map = mapper.readValue(payload, typeRef);

            List<String> authorities = (List<String>) map.get("authorities");
            for (String authority : authorities) {
                if (!authority.contains("id:")) {
                    continue;
                } else {
                    id = Long.parseLong(authority.replace("id:", ""));
                    break;
                }
            }

            if (id == -1) {
                throw new RuntimeException("Bad token!");
            }
        }catch (JsonProcessingException | RuntimeException e) {
            throw new BadCredentialsException(BAD_TOKEN, e);
        }

        return id;
    }

    public boolean isIdInTokenEquals(String token, long id){
        return id == getIdFromToken(token);
    }
}
