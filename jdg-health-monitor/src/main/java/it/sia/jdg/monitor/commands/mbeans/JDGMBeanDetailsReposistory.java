package it.sia.jdg.monitor.commands.mbeans;

import java.util.List;
import java.util.Set;

import javax.management.ObjectInstance;

public interface JDGMBeanDetailsReposistory {

    void findCacheMBeansforMonitor(Set<ObjectInstance> instances);

    public void printCacheMBeansforMonitor();

    List<String> getCacheRpcManagermBeanNamesList();

    List<String> getCacheStateTransfermBeanNamesList();

    List<String> getClusterCacheStatsmBeanNamesList();

    List<String> getCacheMBeanNamesUniqueStartStrList();
}