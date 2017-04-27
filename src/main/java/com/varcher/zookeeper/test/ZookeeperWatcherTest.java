package com.varcher.zookeeper.test;

import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.zookeeper.*;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.data.Stat;

public class ZookeeperWatcherTest {

	static public class AppClient {  
	    private String groupNode = "sgroup";  
	    private ZooKeeper zk;  
	    private Stat stat = new Stat();  
	    private volatile List<String> serverList;  
	  

	    public void connectZookeeper() throws Exception {  
	        final int TIME_OUT = 3000;
	        final String HOST = "192.168.43.216:2181";
	        zk = new ZooKeeper(HOST, TIME_OUT, new Watcher() {  
	            public void process(WatchedEvent event) {  
	                // 如果发生了"/sgroup"节点下的子节点变化事件, 更新server列表, 并重新注册监听  
	                if (event.getType() == EventType.NodeChildrenChanged   
	                    && ("/" + groupNode).equals(event.getPath())) {  
	                    try {  
	                        updateServerList();  
	                    } catch (Exception e) {  
	                        e.printStackTrace();  
	                    }  
	                }
	                String name = ManagementFactory.getRuntimeMXBean().getName();    
	                System.out.println(name);    
	            }  
	        });  
	        
	        if(zk.exists("/" + groupNode, false) == null)
	        {
	            zk.create("/" + groupNode, "test".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
	        }
	  
	        updateServerList();  
	    }  
	  
	    /** 
	     * 更新server列表 
	     */  
	    private void updateServerList() throws Exception {  
	        List<String> newServerList = new ArrayList<String>();  
	  
	        // 获取并监听groupNode的子节点变化  
	        // watch参数为true, 表示监听子节点变化事件.   
	        // 每次都需要重新注册监听, 因为一次注册, 只能监听一次事件, 如果还想继续保持监听, 必须重新注册  
	        List<String> subList = zk.getChildren("/" + groupNode, true);  
	        for (String subNode : subList) {  
	            // 获取每个子节点下关联的server地址  
	            byte[] data = zk.getData("/" + groupNode + "/" + subNode, false, stat);  
	            newServerList.add(new String(data, "utf-8"));  
	        }  
	  
	        // 替换server列表  
	        serverList = newServerList;  
	  
	        System.out.println("server list updated: " + serverList);  
	    }  
	  
	    /** 
	     * client的工作逻辑写在这个方法中 
	     * 此处不做任何处理, 只让client sleep 
	     */  
	    public void handle() throws InterruptedException {  
	        Thread.sleep(Long.MAX_VALUE);  
	    }  

	}  
	  
    public static void main(String[] args) throws Exception {  
        AppClient ac = new AppClient();
//        Map<String, String> map = System.getenv(); 
//        for(Map.Entry<String, String> entry: map.entrySet())
//        {
//        	System.out.println("key = " + entry.getKey() + " : " + entry.getValue());
//        }
        ac.connectZookeeper();  
        
//        String name = ManagementFactory.getRuntimeMXBean().getName();    
//        System.out.println(name);    
  
        ac.handle();  
    }  
    
}
