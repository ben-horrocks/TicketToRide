package plugin;

import java.io.File;

public class PluginDescriptor
{
    private String filePath;
    private String pluginName;

    public PluginDescriptor(String filePath, String pluginName)
    {
        this.filePath = filePath;
        this.pluginName = pluginName;
    }

    public String getFilePath()
    {
        return filePath;
    }

    public String getPluginName()
    {
        return pluginName;
    }
}
