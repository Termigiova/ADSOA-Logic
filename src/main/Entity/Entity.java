package main.Entity;

import main.Enum.EnumType;

public class Entity {

    private EntityThread entityThread;
    private int type;

    public Entity(EntityThread entityThread) {
        this.entityThread = entityThread;
    }

    public EntityThread getEntityThread() { return this.entityThread; }

    public void setType(int type) { this.type = type; }

    public int getType() {
        EnumType type = new EnumType();
//        System.out.println("Node type: " + type.toString(this.type));

        return type.getType(this.type);
    }

}