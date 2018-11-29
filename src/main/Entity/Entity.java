package main.Entity;

import main.Enum.EnumContentCode;
import main.Sockets.Linker;
import main.Utilities.Footprint;

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

    public String getFootprint() {
        return footprint;
    }

    public void setFootprint(String footprint) {
        this.footprint = footprint;
    }

    public void generateFootprint() {
        Footprint footprint = new Footprint();
        this.footprint = footprint.generateFootprint();
    }
}