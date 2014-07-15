package com.github.tachesimazzoca.java.examples.jackson;

import static org.junit.Assert.*;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonMappingException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ProfileFormTest {
    @Test
    public void testSerialization() throws
            JsonGenerationException,
            JsonMappingException,
            IOException {
        ProfileForm.Address address = new ProfileForm.Address(
                "123-0001", "Address1", "Address2", "Address3");

        ProfileForm form = new ProfileForm(
                "user1@example.net", "0000", "0000", "User 1", address, true);

        ObjectMapper mapper = new ObjectMapper();
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        mapper.writeValue(os, form);

        String json = os.toString("UTF-8");
        assertEquals("{\"email\":\"user1@example.net\""
                + ",\"password\":\"0000\",\"name\":\"User 1\""
                + ",\"address\":{\"zipcode\":\"123-0001\""
                + ",\"address1\":\"Address1\",\"address2\":\"Address2\""
                + ",\"address3\":\"Address3\"}}", json);

        ProfileForm form2 = mapper.readValue(json, ProfileForm.class);
        assertEquals(form, form2);
    }
}
