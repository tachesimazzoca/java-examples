package com.github.tachesimazzoca.java.examples.jackson;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ProfileForm {
    public final String email;
    public final String password;
    @JsonIgnore
    public final String retypedPassword;
    public final String name;
    public final Address address;
    @JsonIgnore
    public final boolean agreed;

    public ProfileForm(
            String email,
            String password,
            String retypedPassword,
            String name,
            Address address,
            boolean agreed) {
        this.email = email;
        this.password = password;
        this.retypedPassword = retypedPassword;
        this.name = name;
        this.address = address;
        this.agreed = agreed;
    }

    @JsonCreator
    public ProfileForm(
            @JsonProperty("email") String email,
            @JsonProperty("password") String password,
            @JsonProperty("name") String name,
            @JsonProperty("address") Address address) {
        this.email = email;
        this.password = password;
        this.retypedPassword = password;
        this.name = name;
        this.address = address;
        this.agreed = true;
    }

    public static class Address {
        public final String zipcode;
        public final String address1;
        public final String address2;
        public final String address3;

        @JsonCreator
        public Address(
                @JsonProperty("zipcode") String zipcode,
                @JsonProperty("address1") String address1,
                @JsonProperty("address2") String address2,
                @JsonProperty("address3") String address3) {
            this.zipcode = zipcode;
            this.address1 = address1;
            this.address2 = address2;
            this.address3 = address3;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result
                    + ((address1 == null) ? 0 : address1.hashCode());
            result = prime * result
                    + ((address2 == null) ? 0 : address2.hashCode());
            result = prime * result
                    + ((address3 == null) ? 0 : address3.hashCode());
            result = prime * result
                    + ((zipcode == null) ? 0 : zipcode.hashCode());
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            Address other = (Address) obj;
            if (address1 == null) {
                if (other.address1 != null)
                    return false;
            } else if (!address1.equals(other.address1))
                return false;
            if (address2 == null) {
                if (other.address2 != null)
                    return false;
            } else if (!address2.equals(other.address2))
                return false;
            if (address3 == null) {
                if (other.address3 != null)
                    return false;
            } else if (!address3.equals(other.address3))
                return false;
            if (zipcode == null) {
                if (other.zipcode != null)
                    return false;
            } else if (!zipcode.equals(other.zipcode))
                return false;
            return true;
        }
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((address == null) ? 0 : address.hashCode());
        result = prime * result + (agreed ? 1231 : 1237);
        result = prime * result + ((email == null) ? 0 : email.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result
                + ((password == null) ? 0 : password.hashCode());
        result = prime * result
                + ((retypedPassword == null) ? 0 : retypedPassword.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ProfileForm other = (ProfileForm) obj;
        if (address == null) {
            if (other.address != null)
                return false;
        } else if (!address.equals(other.address))
            return false;
        if (agreed != other.agreed)
            return false;
        if (email == null) {
            if (other.email != null)
                return false;
        } else if (!email.equals(other.email))
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (password == null) {
            if (other.password != null)
                return false;
        } else if (!password.equals(other.password))
            return false;
        if (retypedPassword == null) {
            if (other.retypedPassword != null)
                return false;
        } else if (!retypedPassword.equals(other.retypedPassword))
            return false;
        return true;
    }
}
