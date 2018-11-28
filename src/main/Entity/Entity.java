package main.Entity;

import main.Enum.EnumContentCode;
import main.Sockets.Linker;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

import static main.Utilities.BytesToHex.bytesToHex;

public class Entity {

    private Linker linker;
    private Integer type;
    private String footprint;

    public void setLinker(Linker linker) {
        this.linker = linker;
    }

    public Linker getLinker() { return this.linker; }

    public void setType(int type) { this.type = type; }

    public int getType() {
        EnumContentCode type = new EnumContentCode();
        return type.getType(this.type);
    }

    public void generateFootprint() {
        String digest = null;
        try {
            MessageDigest salt = MessageDigest.getInstance("SHA-256");
            salt.update(UUID.randomUUID().toString().getBytes("UTF-8"));
            digest = bytesToHex(salt.digest());
            this.footprint = String.valueOf(digest);
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        setFootprint(digest);

    }

    public void setFootprint(String footprint) {
        this.footprint = footprint;
    }

    public String getFootprint() {
        return footprint;
    }
}