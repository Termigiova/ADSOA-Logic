package main.Entity.BusinessLogic.Operations;

public class Division implements Operation {

    public Integer solve(Integer value1, Integer value2) {
        if (value2 != 0) {
            return value1 / value2;
        }
        return 0;
    }
}
