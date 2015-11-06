/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.concurrent.ConcurrentHashMap;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import plugin.IPlugin;

/**
 *
 * @author Tommy
 */
public class JarLoader {     
    static public ConcurrentHashMap<String,Class> loadClasses() throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        ConcurrentHashMap<String,Class> classes = new ConcurrentHashMap();
        String pathToJar = "plugin/BasicPlugin.jar";
        
        JarFile jarFile = new JarFile(pathToJar);
        Enumeration e = jarFile.entries();

        URL[] urls = { new URL("jar:file:" + pathToJar+ "!/") };
        URLClassLoader cl = URLClassLoader.newInstance(urls);

        while (e.hasMoreElements()) {
            JarEntry je = (JarEntry) e.nextElement();
            if(je.isDirectory() || !je.getName().endsWith(".class")){
                continue;
            }
            String className = je.getName().substring(0,je.getName().length()-6);
            className = className.replace('/', '.');
            Class c = cl.loadClass(className);
            IPlugin plug = (IPlugin)c.newInstance();
            classes.put(plug.getName(), c);
        }
        jarFile.close();
        
        pathToJar = "plugin/BonusPlugin.jar";
        
        jarFile = new JarFile(pathToJar);
        e = jarFile.entries();

        URL[] urls2 = { new URL("jar:file:" + pathToJar+ "!/") };
        cl = URLClassLoader.newInstance(urls2);

        while (e.hasMoreElements()) {
            JarEntry je = (JarEntry) e.nextElement();
            if(je.isDirectory() || !je.getName().endsWith(".class")){
                continue;
            }
            String className = je.getName().substring(0,je.getName().length()-6);
            className = className.replace('/', '.');
            Class c = cl.loadClass(className);
            Object o = c.newInstance();
            if (o instanceof IPlugin) {
                IPlugin plug = (IPlugin)o;
                classes.put(plug.getName(), c);
            }
        }
        jarFile.close();
        
        return classes;
    }
}
