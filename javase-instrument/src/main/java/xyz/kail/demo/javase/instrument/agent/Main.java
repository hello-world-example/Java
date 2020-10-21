package xyz.kail.demo.javase.instrument.agent;

import com.sun.tools.attach.VirtualMachine;
import com.sun.tools.attach.VirtualMachineDescriptor;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

/**
 * @author kail
 */
public class Main {

    public static void main(String[] args) throws Exception {
        // /target/classes/../
        Path targetPath = Paths.get(Main.class.getResource("/").toURI()).getParent();
        System.out.println("targetPath:" + targetPath);

        //
        List<VirtualMachineDescriptor> vmDescriptors = VirtualMachine.list();
        for (VirtualMachineDescriptor vm : vmDescriptors) {
            System.out.println(vm.id() + " : " + vm.displayName());
        }

        //
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入 pid: ");
        String pid = scanner.nextLine();
        System.out.println("请输入 regex: ");
        String regex = scanner.nextLine();
        scanner.close();

        //
        VirtualMachine vm = VirtualMachine.attach(pid);
        try {
            vm.loadAgent(targetPath + "/javase-instrument-1.0-SNAPSHOT.jar",
                    "" +
                            "ClassToFileTransformer=true;" +
                            "debug=true;" +
                            "regex=" + regex + ";" +
                            "path=" + targetPath
            );
        } finally {
            vm.detach();
        }
    }
}