#set($EntityNameUp="${entityClass.className}")
#set($fileName="${entityClass.className}Dao")
#set($src="src/")
#set($extension=".py")
#set($path=${entityClass.getClazz().getPackage().getName().replace(".entity.", ".dao.").replace(".", "/")})
#set($outputFile="${src}${path}/${fileName}${extension}")
#set($params="${EntityNameUp}:")
#foreach($name in $entityClass.columnInfos.keySet())
#set($params="${params}${name},")
#end
#${params}