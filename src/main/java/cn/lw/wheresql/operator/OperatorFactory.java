package cn.lw.wheresql.operator;

import cn.lw.wheresql.pojo.SearchColumn;

import java.io.File;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * @desc:
 * @author: longwei
 * @date: 2021/7/22
 */
public class OperatorFactory {

    public static ConcurrentHashMap<String, Operator> operators = new ConcurrentHashMap<>();

    /**
     * operator初始化
     */
    static {
        List<Class<?>> classes = getclasses("cn.lw.wheresql.operator");
        for (Class<?> c : classes) {
            if (c.getInterfaces().length > 0 && c.getInterfaces()[0].getName().equalsIgnoreCase("cn.lw.wheresql.operator.Operator")) {
                try {
                    Operator operator = (Operator) c.newInstance();
                    operators.put(operator.getName(), operator);
                } catch (InstantiationException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 构建操作链
     * @param searchColumns
     * @return
     */
    public static OperatorChain buildOperatorChain(List<SearchColumn> searchColumns) {
        List<Operator> list = new ArrayList<>();
        for (SearchColumn searchColumn : searchColumns) {
            if (null == operators.get(searchColumn.getCompareOperation())) {
                throw new RuntimeException(String.format("查询条件设置错误SearchColumn[%s]", searchColumn.toString()));
            }
            list.add(operators.get(searchColumn.getCompareOperation()));
        }
        return new OperatorChain(list, searchColumns);
    }

    /**
     * 从包package中获取所有的class
     *
     * @param packageName
     * @return
     */
    private static List<Class<?>> getclasses(String packageName) {
        // 第一个class类的集合
        List<Class<?>> classes = new ArrayList<>();

        // 是否循环迭代
        boolean recursive = true;

        // 获取包的名字 并进行替换
        String packageDirName = packageName.replace('.', '/');

        // 定义一个枚举的集合 并进行循环处理
        Enumeration<URL> dirs;

        try {

            dirs = Thread.currentThread().getContextClassLoader().getResources(packageDirName);

            while (dirs.hasMoreElements()) {
                URL url = dirs.nextElement();

                String protocol = url.getProtocol();

                // 如果是以文件的形式保存在服务器上
                if ("file".equals(protocol)) {
                    // 获取包的物理路径
                    String filePath = URLDecoder.decode(url.getFile(), "UTF-8");

                    // 以文件的方式扫描整个包下的文件 并添加到集合中
                    findAndAddClassesInPackageByFile(packageName, filePath, recursive, classes);
                } else if ("jar".equals(protocol)) {
                    // 如果是jar包文件 定义一个jarFile
                    JarFile jarFile;

                    // 获取jar
                    jarFile = ((JarURLConnection) url.openConnection()).getJarFile();

                    Enumeration<JarEntry> entries = jarFile.entries();

                    while (entries.hasMoreElements()) {

                        // 获取jar里的一个实体
                        JarEntry jarEntry = entries.nextElement();

                        String name = jarEntry.getName();

                        // 如果是以 / 开头 获取后面字符串
                        if (name.charAt(0) == '/') {
                            name = name.substring(1);
                        }

                        // 如果前半部分和定义的包相同
                        if (name.startsWith(packageDirName)) {
                            int idx = name.lastIndexOf('/');

                            if (idx != -1) {
                                // 获取包名 把"/" 替换成 "."
                                packageName = name.substring(0, idx).replace('/', '.');
                            }

                            if (idx != -1 && recursive) {

                                // 如果是一个.class文件 并且不是目录
                                if (name.endsWith(".class") && !jarEntry.isDirectory()) {

                                    // 获取类名
                                    String className = name.substring(packageName.length() + 1, name.length() - 6);

                                    try {
                                        classes.add(Class.forName(packageName + '.' + className));
                                    } catch (ClassNotFoundException e) {
                                        e.printStackTrace();
                                    }
                                }

                            }
                        }
                    }
                }
            }


        } catch (IOException e) {
            e.printStackTrace();
        }


        return classes;
    }

    /**
     * 以文件的形式获取包下的所有class
     *
     * @param packageName
     * @param filePath
     * @param recursive
     * @param classes
     */
    private static void findAndAddClassesInPackageByFile(String packageName, String filePath, boolean recursive, List<Class<?>> classes) {
        // 获取此包的目录 建立一个File
        File dir = new File(filePath);

        if (!dir.exists() || !dir.isDirectory()) {
            return;
        }

        // 自定义过滤规则 过滤子目录 以及.class结尾的文件
        File[] files = dir.listFiles(file -> (recursive && file.isDirectory()) || file.getName().endsWith(".class"));

        for (File file : files) {
            // 如果是目录 则继续扫描
            if (file.isDirectory()) {

                findAndAddClassesInPackageByFile(packageName + '.' + file.getName(),
                        file.getAbsolutePath(), recursive, classes);

            } else {
                // 如果是class文件 只留下类名
                String className = file.getName().substring(0, file.getName().length() - 6);

                try {
                    classes.add(Class.forName(packageName + '.' + className));
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
