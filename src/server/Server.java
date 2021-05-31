package server;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Server {

    public static void main(String[] args) throws RemoteException {

        CenterServerC serverMTL=new CenterServerC();
        CenterServerC serverLVL=new CenterServerC();
        CenterServerC serverDDO=new CenterServerC();

        Registry registry= LocateRegistry.createRegistry(2964);


    }
}
