package com.sweep.formduo.domain.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


@Getter
public enum MemberAuth {
    ROLE_USER("ROLE_USER"),
    ROLE_ADMIN("ROLE_ADMIN"),
    ROLE_GUEST("GUEST")
    ;

    private final String abbreviation;
    public static MemberAuth of(String abbreviation){
        return Arrays.stream(MemberAuth.values())
                .filter(r -> r.getAbbreviation().equals(abbreviation))
                .findAny()
                .orElse(ROLE_GUEST);
    }

    private static final Map<String,MemberAuth> lookup = new HashMap<>();

    static {
        for(MemberAuth auth : MemberAuth.values()) {
            lookup.put(auth.abbreviation,auth);
        }
    }

    // private
    MemberAuth(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public String getAbbreviation() {
        return this.abbreviation;
    }

    public static MemberAuth get(String abbreviation) {
        return lookup.get(abbreviation);
    }

    public static boolean containsKey(String abbreviation) {
        return lookup.containsKey(abbreviation);
    }



}