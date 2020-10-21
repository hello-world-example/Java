package xyz.kail.demo.javase.instrument.attach;

import com.sun.tools.attach.VirtualMachine;
import com.sun.tools.attach.VirtualMachineDescriptor;

import java.util.List;

public class AttachMain {

    public static void main(String[] args) {
        //
        List<VirtualMachineDescriptor> vmDescriptors = VirtualMachine.list();
        for (VirtualMachineDescriptor vm : vmDescriptors) {
            System.out.println(vm.id() + " : " + vm.displayName());
        }
    }

}
