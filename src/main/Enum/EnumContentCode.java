package main.Enum;

public class EnumContentCode {
    static public final int NOTDECLARED = 0;
    static public final int SUM = 1;
    static public final int SUBSTRACTION = 2;
    static public final int MULTIPLICATION = 3;
    static public final int DIVISION = 4;

    public String toString(int type) {
        switch (type) {
            case NOTDECLARED:
                return "NOTDECLARED";
            case SUM:
                return "SUM";
            case SUBSTRACTION:
                return "SUBSTRACTION";
            case MULTIPLICATION:
                return "MULTIPLICATION";
            case DIVISION:
                return "DIVISION";
        }
        return null;
    }

    public int getType(int type) {
        switch (type) {
            case NOTDECLARED:
                return NOTDECLARED;
            case SUM:
                return SUM;
            case SUBSTRACTION:
                return SUBSTRACTION;
            case MULTIPLICATION:
                return MULTIPLICATION;
            case DIVISION:
                return DIVISION;
        }
        return 0;
    }
}
