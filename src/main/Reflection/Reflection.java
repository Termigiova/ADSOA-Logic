package main.Reflection;

import main.Enum.EnumContentCode;
import org.apache.commons.io.FileUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import static main.Enum.EnumContentCode.SUM;

public class Reflection extends Thread{

    String createdNodeJar;
    String parameters;

    public Reflection(String jarName, String parameters) {
        String currentDirectory = System.getProperty("user.dir");
        String rootDirectory = currentDirectory.split("ADSOA/")[0];
        String nodeJarDirectory = rootDirectory + "/out/artifacts/" + jarName + "_jar/";
        String nodeFileName = jarName + ".jar";
        String timestamp = Long.toString(System.currentTimeMillis());
        String destination = nodeJarDirectory + jarName + "-" + timestamp;

        File sourceDirectory = new File(nodeJarDirectory);
        File destinationDirectory = new File(destination);
        createdNodeJar = destinationDirectory + "/" + nodeFileName;
        this.parameters = parameters;
        System.out.println(createdNodeJar);

        try {
            FileUtils.copyDirectory(sourceDirectory, destinationDirectory);
//            Runtime.getRuntime().exec("java -jar " + createdNodeJar + " " + parameters);
//            Process process = Runtime.getRuntime().exec("java -jar " + createdNodeJar + " " + parameters);
//            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
//
//            String line = "";
//            while((line = reader.readLine()) != null) {
//                System.out.print(line + "\n");
//            }
//
//            process.waitFor();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            Runtime.getRuntime().exec("java -jar " + createdNodeJar + " " + parameters);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String args[]) {

//        Reflection reflection = new Reflection("Node", "");
//        Reflection reflection = new Reflection("Interface", "5000");
//        Reflection reflection = new Reflection("BusinessLogic", "5000 " + SUM);


    }

}
