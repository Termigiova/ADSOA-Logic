package main.Entity;

import main.Enum.EnumType;
import main.Sockets.Linker;

public class Entity {

    private Linker linker;
    private Integer type;
    private String footprint;

    public Entity() {

    }

    public void setLinker(Linker linker) {
        this.linker = linker;
    }

    public Linker getLinker() { return this.linker; }

    public void setType(int type) { this.type = type; }

    public int getType() {
        EnumType type = new EnumType();
        return type.getType(this.type);
    }

    public void generateFootprint() {
        this.footprint = "Node" + Math.random();
    }

    public String getFootprint() {
        return footprint;
    }
}