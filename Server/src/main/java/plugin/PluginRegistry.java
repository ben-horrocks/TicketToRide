package plugin;

import java.io.File;
import java.lang.reflect.Constructor;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class PluginRegistry
{
    Map<String, PluginDescriptor> plugins;

    public PluginRegistry()
    {
        plugins = new HashMap<>();
    }

    public void scanForPlugins(String directoryPath)
    {
        File pluginDirectory = new File(directoryPath);
        for(File plugin : pluginDirectory.listFiles())
        {
            String filePath = plugin.getPath();
            String fullName = plugin.getName();
            String pluginName = fullName.substring(0, fullName.length() - ".jar".length());
            PluginDescriptor descriptor = new PluginDescriptor(filePath, pluginName);
            plugins.put(pluginName, descriptor);
        }
    }

    public IDatabasePlugin loadPlugin(String pluginName, String pluginClassName, int numberOfCommands, boolean clear) throws Exception
    {
        PluginDescriptor descriptor = plugins.get(pluginName);
        if(descriptor == null)
            throw new ClassNotFoundException("Did not find a " + pluginName + " plugin!");
        File jarFile = new File(descriptor.getFilePath());
        URLClassLoader pluginLoader = URLClassLoader.newInstance(new URL[] {jarFile.toURL()});

        Class<?> pluginClass = Class.forName(pluginClassName, true, pluginLoader);
        Constructor<?> constructor = pluginClass.getConstructor(int.class, boolean.class);
        return (IDatabasePlugin) constructor.newInstance(numberOfCommands, clear);
    }
}
