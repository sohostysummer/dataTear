package zhao.io.dataTear.dataOp.dataTearRW.hdfs;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import zhao.io.dataTear.atzhaoPublic.Builder;
import zhao.io.dataTear.atzhaoPublic.Priority;

import java.io.IOException;

/**
 * HDFS数据输出组件建造者
 */
public class HDFSWriterZIPBuilder implements Builder<HDFSWriterZIP> {
    private final Configuration configuration = new Configuration();
    private String pathString;
    private Path pathObject;
    private FileSystem fileSystem;
    private String charset = "utf-8";

    /**
     * @param charset 需要使用的字符编码集类，可以通过StandardCharsets.xxxxx 枚举类获取
     * @return 链
     */
    public HDFSWriterZIPBuilder setCharset(String charset) {
        this.charset = charset;
        return this;
    }

    /**
     * 定位HDFS信息
     * 如果没有进行过过FileSystem的设置，本设置将会生效，也就是说 它是一种备用方法
     *
     * @param IP   HDFS集群通讯地址 一般是主NameNode信息
     * @param port 通讯端口
     * @return 链
     */
    @Priority("2")
    public HDFSWriterZIPBuilder setIP_port(String IP, String port) {
        configuration.set("fs.default.name", "hdfs://" + IP + ":" + port);
        return this;
    }

    /**
     * 定制更多配置信息
     * 如果没有进行过过FileSystem的设置，本设置将会生效，也就是说 它是一种备用方法
     *
     * @param key   HDFS配置名称
     * @param value 配置参数
     * @return 链
     */
    @Priority("2")
    public HDFSWriterZIPBuilder setKV(String key, String value) {
        configuration.set(key, value);
        return this;
    }

    /**
     * 定位输出路径 该方法不一定会被调用
     *
     * @param pathString 设置文件输出路径
     * @return 链
     */
    @Priority("3")
    public HDFSWriterZIPBuilder setPathString(String pathString) {
        this.pathString = pathString;
        return this;
    }

    /**
     * 定位输出路径 该方法不一定会被调用
     *
     * @param pathObject 设置文件输出路径对象
     * @return 链
     */
    @Priority("2")
    public HDFSWriterZIPBuilder setPathObject(Path pathObject) {
        this.pathObject = pathObject;
        return this;
    }

    /**
     * 直接通过FileSystem对象构建输出组件
     *
     * @param fileSystem HDFS文件系统对象
     * @return 链
     */
    @Priority("1")
    public HDFSWriterZIPBuilder setFileSystem(FileSystem fileSystem) {
        this.fileSystem = fileSystem;
        return this;
    }

    @Override
    public HDFSWriterZIP create() {
        try {
            if (pathObject == null) pathObject = new Path(pathString);
            return new HDFSWriterZIP(fileSystem, pathObject, charset);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
