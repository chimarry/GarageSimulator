package util;

import garage.structure.model.PlatformField;

import java.util.function.Predicate;

public final class PlatformFieldPredicate {
    private PlatformFieldPredicate(){}
    public static Predicate<PlatformField> isParkingSpace(){

        return PlatformField::isParkingField;
    }
}
