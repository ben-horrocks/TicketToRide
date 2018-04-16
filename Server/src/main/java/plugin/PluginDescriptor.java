package plugin;

import java.io.File;

public class PluginDescriptor {
    private String filePath;
    private String pluginName;
    private String pluginClassPath;

    public PluginDescriptor(File jarFile)
    {
        filePath = jarFile.getPath();
        pluginName = jarFile.getName();
        pluginClassPath = "";
    }
}
