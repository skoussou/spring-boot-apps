package it.sia.jdg.monitor.commands.mbeans.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.util.logging.Logger;

import javax.management.ObjectInstance;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import it.sia.jdg.monitor.commands.mbeans.JDGMBeanDetailsReposistory;

/**
 * 
 * JMX Components & Attributes for monitoring JDG 6.x versions
 * 
 * Complete list can be fond here
 * https://docs.jboss.org/infinispan/6.0/apidocs/jmxComponents.html
 * 
 * @author Stelios Kousouris
 */

@Profile("jdg65")
@Component
public class JDGMBeanDetailsReposistoryJDG65 implements JDGMBeanDetailsReposistory {

    // JDG version 6 CacheManager MBean naem
    // Note: only for one cachecontainer "clustered"
    public static String cacheManagerClusteredMBeanJDG6 = "jboss.infinispan:type=CacheManager,name=\"clustered\",component=CacheManager";

    // JDG version 6 HotRod Transport MBean naem
    public static final String hotRodTransportMBeanJDG6 = "jboss.infinispan:type=Server,name=HotRod,component=Transport";

    // JDG version 6 Cache MBean
    public static String cacheCategoryMBeans = "jboss.infinispan:type=Cache,name=";

    public static String cacheCategoryRpcManagerMBeanClassName = "org.infinispan.remoting.rpc.RpcManagerImpl";
    public static String cacheCategoryStateTransferMBeanClassName = "org.infinispan.statetransfer.StateTransferManagerImpl";
    public static String cacheCategoryClusterCacheStatsMBeanClassName = "org.infinispan.stats.impl.ClusterCacheStatsImpl";

    public Set<String> jdgStandardCacheNamesSet = new HashSet<String>() {
        {
            add("___script_cache(repl_sync)");
            add("org.infinispan.CLIENT_SERVER_TX_TABLE(repl_sync)");
            add("___protobuf_metadata(repl_sync)");
            add("org.infinispan.CONFIG(repl_sync)");
            add("___hotRodTopologyCache(repl_sync)");
        }
    };

    /* org.infinispan.remoting.rpc.RpcManagerImpl */
    private List<String> cacheRpcManagermBeanNamesList = new ArrayList<String>();

    /* org.infinispan.statetransfer.StateTransferManagerImpl */
    private List<String> cacheStateTransfermBeanNamesList = new ArrayList<String>();

    /* org.infinispan.stats.impl.ClusterCacheStatsImpl */
    private List<String> clusterCacheStatsmBeanNamesList = new ArrayList<String>();

    /*
     * Taking advantage of common start of MBean names to capture unique cache MBean
     * names (won't work for multile cachecontainers)
     */
    private List<String> cacheMBeanNamesUniqueStartStrList = new ArrayList<String>();

    public List<String> addRPCCacheMbeanName(String mbeanName) {
        if (cacheRpcManagermBeanNamesList == null) {
            cacheRpcManagermBeanNamesList = new ArrayList<String>();
        }
        if (!cacheRpcManagermBeanNamesList.contains(mbeanName))
            cacheRpcManagermBeanNamesList.add(mbeanName);
        return cacheRpcManagermBeanNamesList;
    }

    public List<String> addStateTransferCacheMbeanName(String mbeanName) {
        if (cacheStateTransfermBeanNamesList == null) {
            cacheStateTransfermBeanNamesList = new ArrayList<String>();
        }
        if (!cacheStateTransfermBeanNamesList.contains(mbeanName))
            cacheStateTransfermBeanNamesList.add(mbeanName);
        return cacheStateTransfermBeanNamesList;
    }

    public List<String> addClusterCacheMbeanName(String mbeanName) {
        if (clusterCacheStatsmBeanNamesList == null) {
            clusterCacheStatsmBeanNamesList = new ArrayList<String>();
        }
        if (!clusterCacheStatsmBeanNamesList.contains(mbeanName))
            clusterCacheStatsmBeanNamesList.add(mbeanName);
        return clusterCacheStatsmBeanNamesList;
    }

    public List<String> addCacheMBeanNamesUniqueStart(String mbeanNameStart) {
        if (cacheMBeanNamesUniqueStartStrList == null) {
            cacheMBeanNamesUniqueStartStrList = new ArrayList<String>();
        }
        if (!cacheMBeanNamesUniqueStartStrList.contains(mbeanNameStart))
            cacheMBeanNamesUniqueStartStrList.add(mbeanNameStart);
        return cacheMBeanNamesUniqueStartStrList;
    }

    @Override
    public List<String> getCacheRpcManagermBeanNamesList() {
        return cacheRpcManagermBeanNamesList;
    }

    @Override
    public List<String> getCacheStateTransfermBeanNamesList() {
        return cacheStateTransfermBeanNamesList;
    }

    @Override
    public List<String> getClusterCacheStatsmBeanNamesList() {
        return clusterCacheStatsmBeanNamesList;
    }

    @Override
    public List<String> getCacheMBeanNamesUniqueStartStrList() {
        return cacheMBeanNamesUniqueStartStrList;
    }

    @Override
    public void findCacheMBeansforMonitor(Set<ObjectInstance> instances) {
        Iterator<ObjectInstance> iterator = instances.iterator();

        while (iterator.hasNext()) {
            ObjectInstance instance = iterator.next();
            String mbeanNameStr = instance.getObjectName().toString();
            if (mbeanNameStr.startsWith(JDGMBeanDetailsReposistoryJDG65.cacheCategoryMBeans)) {
                if (instance.getClassName().toString()
                        .equals(JDGMBeanDetailsReposistoryJDG65.cacheCategoryRpcManagerMBeanClassName)) {
                    this.addRPCCacheMbeanName(instance.getObjectName().toString());
                    this.addCacheMBeanNamesUniqueStart(
                            addToUniqueCacheMBeanNameList(instance.getObjectName().toString()));
                } else if (instance.getClassName().toString()
                        .equals(JDGMBeanDetailsReposistoryJDG65.cacheCategoryStateTransferMBeanClassName)) {
                    this.addStateTransferCacheMbeanName(instance.getObjectName().toString());
                } else if (instance.getClassName().toString()
                        .equals(JDGMBeanDetailsReposistoryJDG65.cacheCategoryClusterCacheStatsMBeanClassName)) {
                    this.addClusterCacheMbeanName(instance.getObjectName().toString());
                } else {

                    // Logger.getGlobal().log(java.util.logging.Level.SEVERE, "MBean Ignored: ");
                    // Logger.getGlobal().log(java.util.logging.Level.SEVERE, "Class Name: " +
                    // instance.getClassName());
                    // Logger.getGlobal().log(java.util.logging.Level.SEVERE, "Object Name: " +
                    // instance.getObjectName());
                    // Logger.getGlobal().log(java.util.logging.Level.SEVERE,
                    // "****************************************");
                }
            }
        }
        // printCacheMBeansforMonitor();
        Logger.getGlobal().info("<----------------------------------------------------------------------------->");

        Logger.getGlobal().info("Mbeans for Rpc           : " + this.getCacheRpcManagermBeanNamesList().size());
        Logger.getGlobal().info("Mbeans for StateTransfer : " + this.getCacheStateTransfermBeanNamesList().size());

        Logger.getGlobal().info("Mbeans for ClusterdCache : " + this.getClusterCacheStatsmBeanNamesList().size());
        Logger.getGlobal().info("Mbeans Uniques Str       : " + this.getCacheMBeanNamesUniqueStartStrList().size());

        Logger.getGlobal().info("<----------------------------------------------------------------------------->");
    }

    private String addToUniqueCacheMBeanNameList(String mbeanName) {
        int index2ndComma = mbeanName.indexOf(",", mbeanName.indexOf(",") + 1);
        String mbeanNameUniqueStart = mbeanName.substring(0, index2ndComma);
        // System.out.println(
        // "MBEAN Name [Idx: " + index2ndComma + "]: " + mbeanName + " subsrting[" +
        // mbeanNameUniqueStart + "]");
        return mbeanNameUniqueStart;
    }

    // private String printniqueCacheNameList(String mbeanName) {
    // int index2ndComma = mbeanName.indexOf(",", mbeanName.indexOf(",") + 1);
    // String mbeanNameUniqueStart = mbeanName.substring(0, index2ndComma);
    // System.out.println(
    // "MBEAN Name [Idx: " + index2ndComma + "]: " + mbeanName + " subsrting[" +
    // mbeanNameUniqueStart + "]");
    // return mbeanNameUniqueStart;
    // }

    @Override
    public void printCacheMBeansforMonitor() {
        Logger.getGlobal().info("<----------------------------------------------------------------------------->");
        Logger.getGlobal().info("Cache Rpc MBeans List           : [" + this.getCacheRpcManagermBeanNamesList() + "]");
        Logger.getGlobal()
                .info("Cache StateTransfer MBeans List : [" + this.getCacheStateTransfermBeanNamesList() + "]");
        Logger.getGlobal()
                .info("Cache ClusterCache MBeans List  : [" + this.getClusterCacheStatsmBeanNamesList() + "]");
        Logger.getGlobal()
                .info("Cache Unique MBeans Starts List  : [" + this.getCacheMBeanNamesUniqueStartStrList() + "]");
        Logger.getGlobal().info("<----------------------------------------------------------------------------->");

    }

}