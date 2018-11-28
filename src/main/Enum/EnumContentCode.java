package main.Enum;

public class EnumContentCode {
    static public final int NOTDECLARED = 0;
    static public final int NODE = 1;
    static public final int INTERFACE = 2;
    static public final int SUM = 3;
    static public final int SUBSTRACTION = 4;
    static public final int MULTIPLICATION = 5;
    static public final int DIVISION = 6;
    static public final int RESULT = 7;
    static public final int INITIAL_ENTITY_CONF = 8;

    public String toString(int type) {
        switch (type) {
            case NODE:
                return "NODE";
            case INTERFACE:
                return "INTERFACE";
            case SUM:
                return "SUM";
            case SUBSTRACTION:
                return "SUBSTRACTION";
            case MULTIPLICATION:
                return "MULTIPLICATION";
            case DIVISION:
                return "DIVISION";
            case RESULT:
                return "RESULT";
            case INITIAL_ENTITY_CONF:
                return "INITIAL_ENTITY_CONF";
            case NOTDECLARED:
            default:
                return "NOTDECLARED";
        }
    }

    public int getType(int type) {
        switch (type) {
            case NODE:
                return NODE;
            case INTERFACE:
                return INTERFACE;
            case SUM:
                return SUM;
            case SUBSTRACTION:
                return SUBSTRACTION;
            case MULTIPLICATION:
                return MULTIPLICATION;
            case DIVISION:
                return DIVISION;
            case RESULT:
                return RESULT;
            case INITIAL_ENTITY_CONF:
                return INITIAL_ENTITY_CONF;
            case NOTDECLARED:
            default:
                return NOTDECLARED;
        }
    }
}
