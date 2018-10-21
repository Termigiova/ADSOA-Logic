package main.Reflection;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.lang.reflect.Method;

public class ReflectClass {

    private Class reflectedClass;
    private Method[] reflectedMethods;

    ReflectClass(Class reflectedClass) {
        this.reflectedClass = reflectedClass;
        this.reflectedMethods = this.reflectedClass.getDeclaredMethods();
    }

    private void printReflectedMethods() {
        for (int i = 0; i < reflectedMethods.length; i++) {
            System.out.println(reflectedMethods[i].toString());
        }
    }

    private Method[] getReflectedMethods() {
        return reflectedMethods;
    }

    private void getMethodsInfo() {
        for (int i = 0; i < reflectedMethods.length; i++) {
            Method m = reflectedMethods[i];
            System.out.println("name = " + m.getName());
            System.out.println("decl class = " + m.getDeclaringClass());
            Class pvec[] = m.getParameterTypes();
            for (int j = 0; j < pvec.length; j++)
                System.out.println("param #" + j + " " + pvec[j]);
                Class evec[] = m.getExceptionTypes();
            for (int j = 0; j < evec.length; j++)
                System.out.println("exc #" + j + " " + evec[j]);
            System.out.println("return type = " + m.getReturnType());
            System.out.println("-----");
        }
    }

    String Encoder(Object object) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            String json = objectMapper.writeValueAsString(object);
            System.out.println(json);
            return json;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    void Decoder(Object object, String json) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            Object car = objectMapper.readValue(json, object.getClass());

            System.out.println("Car brand: " + car);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ReflectClass reflectClass = new ReflectClass(Car.class);
        Method[] reflectedMethods = reflectClass.getReflectedMethods();

        reflectClass.getMethodsInfo();

//        Car car = new Car();
//        car.setBrand("BMW");
//        car.setDoors(4);
//
//        String json = reflectClass.Encoder(car);
//        reflectClass.Decoder(car,json);
//
//        Class c = Car.class;
//        Method m[] = c.getDeclaredMethods();
//        for (int i = 0; i < m.length; i++) {
//            System.out.println(m[i].toString());
//        }

    }

}
