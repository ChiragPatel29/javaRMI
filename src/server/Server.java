package server;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.HashMap;

public class Server {
    static HashMap<String, CenterServer> servers;
    static CenterServer serverMTL;
    static CenterServer serverLVL;
    static CenterServer serverDDO;

    public static void initializeServers() throws RemoteException {
        servers = new HashMap<>();
        serverMTL = new CenterServer();
        serverLVL = new CenterServer();
        serverDDO = new CenterServer();
        servers.put("MTL", serverMTL);
        servers.put("LVL", serverLVL);
        servers.put("DDO", serverLVL);

    }


    public static void main(String[] args) throws RemoteException, AlreadyBoundException {
        initializeServers();

        Registry registry = LocateRegistry.createRegistry(2964);
        registry.bind("MTL",serverMTL);
        registry.bind("LVL",serverLVL);
        registry.bind("DDO",serverLVL);


    }
}
