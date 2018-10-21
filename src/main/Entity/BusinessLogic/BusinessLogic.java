package main.Entity.BusinessLogic;

import main.Entity.AbstractEntity;
import main.Enum.EnumContentCode;
import main.Enum.EnumType;

import java.io.IOException;
import java.util.Arrays;

public class BusinessLogic extends AbstractEntity {

    private Integer value1;
    private Integer value2;
    private Integer result;
    private Integer operand;

    private BusinessLogic() throws IOException {
        super();
        setType(EnumType.BUSINESSLOGIC);
    }

    @Override
    public void readFromIncomingInput() throws IOException, ClassNotFoundException {
        Object operation;

        while (true) {
            operation = in.readObject();
            System.out.println("BL reading: " + Arrays.toString((Object[]) operation));
            result = getResultFrom((Object[]) operation);
            sendOutput();
        }
    }

    private Integer getResultFrom(Object[] operation) {
        EnumContentCode enumContentCode = new EnumContentCode();

        value1 = (Integer) operation[0];
        operand = enumContentCode.getType((Integer) operation[1]);
        value2 = (Integer) operation[2];

        switch (operand) {
            case EnumContentCode.SUM:
                return sum(value1, value2);
            case EnumContentCode.SUBSTRACTION:
                return subtract(value1, value2);
            case EnumContentCode.MULTIPLICATION:
                return multiply(value1, value2);
            case EnumContentCode.DIVISION:
                return divide(value1,value2);
            default:
                return null;
        }

    }

    private Integer sum(Integer value1, Integer value2) { return (value1 + value2); }

    private Integer subtract(Integer value1, Integer value2) { return (value1 - value2); }

    private Integer multiply(Integer value1, Integer value2) { return (value1 * value2); }

    private Integer divide(Integer value1, Integer value2) {
        if (value2 != 0)
            return (value1 / value2);
        return null;
    }

    @Override
    public void sendOutput() throws IOException {
        System.out.println("BL sending: " + result);
        Object[] obj = new Object[] {result};
        out.writeObject(obj);
    }

    public static void main(String[] args) throws IOException {

        BusinessLogic businessLogic = new BusinessLogic();
        businessLogic.connectToPort(5001);
        businessLogic.getInfo();
        businessLogic.start();

    }

}
