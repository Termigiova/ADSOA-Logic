package main.Entity.BusinessLogic.Operations;

import main.Entity.BusinessLogic.Operation;

public class Multiplication implements Operation {

    public Integer solve(Integer value1, Integer value2) {
        return value1 * value2;
    }

}
