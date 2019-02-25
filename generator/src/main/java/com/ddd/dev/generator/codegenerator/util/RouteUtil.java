package com.ddd.dev.generator.codegenerator.util;

import com.cisdi.info.simple.DDDException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.wiring.ClassNameBeanWiringInfoResolver;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
import java.util.jar.JarEntry;

public class RouteUtil {
    private static org.apache.logging.log4j.Logger logger = LogManager.getLogger();
    public static void main(String[] args) throws IOException {
        String rootPaht = "F:\\IDEA\\workspace\\xc-pc-ui\\src\\view";
        mergeRoutes(rootPaht);
        logger.info("成功合并");
    }
    public static void mergeRoutes(String rootPaht)  {
        Map<File,List<File>> routes =listRoutes(rootPaht);

        try {
            executeMergeRoutes(routes);
        } catch (IOException e) {
            throw new DDDException("合并路由出错，原因是："+e.getMessage(),e);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }
    public static Map<File,List<File>> listRoutes(String rootPath)
    {
        File root = new File(rootPath);
        Map<File,List<File>> routes = new HashMap<File, List<File>>();
        File[] subSystemDirs = root.listFiles(new FileFilter() {
            @Override
            public boolean accept(File file) {
                if(file.isDirectory())
                {
                    File routeFile = makeRouteFile(file);
                    //System.out.println(file.toString());
                    return routeFile.exists();
                }
                return false;
            }
        });
        for(File file : subSystemDirs)
        {
            AndFileFilter andFileFilter = new AndFileFilter();
            andFileFilter.addFileFilter(new SuffixFileFilter("Route.js"));
            andFileFilter.addFileFilter(new NotFileFilter(new NameFileFilter(file.getName()+"Route.js")));
            Collection<File>  moduleRoutes = FileUtils.listFiles(file,andFileFilter ,TrueFileFilter.INSTANCE);

             routes.put(file, new ArrayList<File>(moduleRoutes));
        }
//        System.out.println(routes);
        return routes;
    }
    private static void executeMergeRoutes(Map<File,List<File>> routes) throws IOException, URISyntaxException {
        for (File subSystemFile : routes.keySet())
        {
            String route = FileUtils.readFileToString(makeRouteFile(subSystemFile), "UTF-8");
            int childrenPos = StringUtils.indexOf(route, "children");
            String temp1 = StringUtils.substring(route,0, childrenPos);
            String temp2 = StringUtils.substring(route,childrenPos);
            StringBuilder childrenRoutes = new StringBuilder(StringUtils.substringBetween(temp2,"[" ,"]" ));

            for(File file : routes.get(subSystemFile))
            {
                String routeName = StringUtils.removeEnd(file.getName(),".js");
                //相对化路径
                String importLine= "./"+new URI(subSystemFile.getAbsolutePath().replaceAll("\\\\","/")).relativize(new URI(file.getAbsolutePath().replaceAll("\\\\","/"))).getPath();
                if(! StringUtils.contains(route, importLine))
                {
                    importLine = "import "+routeName+" from '"+importLine+"'\n";
                    temp1 = importLine+temp1;
                    childrenRoutes.append("     ...").append(routeName).append(",\n");
                }
            }
            temp2 = StringUtils.substringBefore(temp2, "[")+"["+childrenRoutes.toString()+"    ]"+StringUtils.substringAfter(temp2, "]");
            route = temp1 + temp2;
            FileUtils.writeStringToFile(makeRouteFile(subSystemFile), route, "UTF-8");
            logger.info(String.format("合并Route%s", makeRouteFile(subSystemFile)));
        }
    }
    private static File makeRouteFile(File file)
    {
        return new File(file.getPath()+File.separator+file.getName()+"Route.js");
    }

}
