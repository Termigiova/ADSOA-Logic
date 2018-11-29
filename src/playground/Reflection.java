package playground;

import org.apache.commons.io.FileUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class Reflection {



    public Reflection(String jarName) {

    }

    public static void main(String args[]) {

        String currentDirectory = System.getProperty("user.dir");
        String rootDirectory = currentDirectory.split("ADSOA/")[0];
        String nodeJarDirectory = rootDirectory + "/out/artifacts/Node_jar/";
        String nodeFileName = "Node.jar";
        String timestamp = Long.toString(System.currentTimeMillis());
        String destination = nodeJarDirectory + "Node-" + timestamp;

        File sourceDirectory = new File(nodeJarDirectory);
        File destinationDirectory = new File(destination);
        String createdNodeJar = destinationDirectory + "/" + nodeFileName;
        System.out.println(createdNodeJar);

        try {
            FileUtils.copyDirectory(sourceDirectory, destinationDirectory);
            Process process = Runtime.getRuntime().exec("java -jar " + createdNodeJar);
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line = "";
            while((line = reader.readLine()) != null) {
                System.out.print(line + "\n");
            }

            process.waitFor();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

}
