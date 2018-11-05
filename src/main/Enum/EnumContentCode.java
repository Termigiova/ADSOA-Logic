package main.Enum;

public class EnumContentCode {
    static public final int NOTDECLARED = 0;
    static public final int SUM = 1;
    static public final int SUBSTRACTION = 2;
    static public final int MULTIPLICATION = 3;
    static public final int DIVISION = 4;
    static public final int RESULT = 5;
    static public final int INITIAL_NODE_CONF = 6;
    static public final int INITIAL_INTERFACE_CONF = 7;
    static public final int INITIAL_OPERATION_CONF = 8;

    public String toString(int type) {
        switch (type) {
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
            case INITIAL_NODE_CONF:
                return "INITIAL_NODE_CONF";
            case INITIAL_INTERFACE_CONF:
                return "INITIAL_INTERFACE_CONF";
            case INITIAL_OPERATION_CONF:
                return "INITIAL_OPERATION_CONF";
            case NOTDECLARED:
            default:
                return "NOTDECLARED";
        }
    }

    public int getType(int type) {
        switch (type) {
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
            case INITIAL_NODE_CONF:
                return INITIAL_NODE_CONF;
            case INITIAL_INTERFACE_CONF:
                return INITIAL_INTERFACE_CONF;
            case INITIAL_OPERATION_CONF:
                return INITIAL_OPERATION_CONF;
            case NOTDECLARED:
            default:
                return NOTDECLARED;
        }
    }
}
