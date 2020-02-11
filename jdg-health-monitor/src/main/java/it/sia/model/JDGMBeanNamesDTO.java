package it.sia.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import it.sia.jdg.monitor.commands.mbeans.JDGMBeanDetailsReposistory;

public class JDGMBeanNamesDTO implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -5850298738090838049L;

    /* org.infinispan.remoting.rpc.RpcManagerImpl */
    private List<String> cacheRpcManagermBeanNamesList = new ArrayList<String>();

    /* org.infinispan.statetransfer.StateTransferManagerImpl */
    private List<String> cacheStateTransfermBeanNamesList = new ArrayList<String>();

    /* org.infinispan.stats.impl.ClusterCacheStatsImpl */
    private List<String> clusterCacheStatsmBeanNamesList = new ArrayList<String>();

    public JDGMBeanNamesDTO(List<String> cacheRpcManagermBeanNamesList, List<String> cacheStateTransfermBeanNamesList,
            List<String> clusterCacheStatsmBeanNamesList) {
        this.cacheRpcManagermBeanNamesList = cacheRpcManagermBeanNamesList;
        this.cacheStateTransfermBeanNamesList = cacheStateTransfermBeanNamesList;
        this.clusterCacheStatsmBeanNamesList = clusterCacheStatsmBeanNamesList;
    }

    public JDGMBeanNamesDTO(JDGMBeanDetailsReposistory mbeanNames) {
        this.cacheRpcManagermBeanNamesList = mbeanNames.getCacheRpcManagermBeanNamesList();
        this.cacheStateTransfermBeanNamesList = mbeanNames.getCacheStateTransfermBeanNamesList();
        this.clusterCacheStatsmBeanNamesList = mbeanNames.getClusterCacheStatsmBeanNamesList();
    }

    public List<String> getCacheRpcManagermBeanNamesList() {
        return cacheRpcManagermBeanNamesList;
    }

    public List<String> getCacheStateTransfermBeanNamesList() {
        return cacheStateTransfermBeanNamesList;
    }

    public List<String> getClusterCacheStatsmBeanNamesList() {
        return clusterCacheStatsmBeanNamesList;
    }

}