package main.Enum;

public class EnumType {
    static public final int NOTDECLARED = 0;
    static public final int INTERFACE = 1;
    static public final int BUSINESSLOGIC = 2;
    static public final int NODE = 3;

    public String toString(int type) {
        switch (type) {
            case NOTDECLARED:
                return "NOTDECLARED";
            case INTERFACE:
                return "INTERFACE";
            case BUSINESSLOGIC:
                return "BUSINESS LOGIC";
            case NODE:
                return "NODE";
        }
        return null;
    }

    public int getType(int type) {
        switch (type) {
            case NOTDECLARED:
                return NOTDECLARED;
            case INTERFACE:
                return INTERFACE;
            case BUSINESSLOGIC:
                return BUSINESSLOGIC;
            case NODE:
                return NODE;
        }
        return 0;
    }
}
