package com.ddd.dev.generator.codegenerator.generator.impl.java.entity;

import com.cisdi.info.simple.DDDException;
import com.cisdi.info.simple.util.Config;
import com.cisdi.info.simple.util.D4Util;
import com.cisdi.info.simple.util.EntityManager;
import com.ddd.dev.generator.codegenerator.entity.FieldAttribute;
import com.ddd.dev.generator.codegenerator.entity.GenerationAttribute;
import com.ddd.dev.generator.codegenerator.generator.impl.java.JavaGenerator;
import com.ddd.dev.generator.codegenerator.util.GeneratorUtil;
import com.cisdi.info.simple.entity.base.EntityClass;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class EntityGenerator extends JavaGenerator {
    private static Logger logger = LogManager.getLogger();

    @Autowired
    private Config config;

    private GenerationAttribute generationAttribute;

    private Boolean coverWrite = false;

    private String filePath;

    private String templateLoc = "frameResource/java/Entity.vm";

    private String daoTemplateAbsPath;

//	private Map<String, String> replacement = new HashMap<String, String>();

    private Set<String> importArea = new HashSet<String>();

    private static String gsfunc = "\tpublic {fieldType} get{fieldNameUp}() {\n\t\treturn this.{fieldName};\n"
            + "\t}\n\n" + "\tpublic void set{fieldNameUp}({fieldType} {fieldName}) {\n\t\tthis.{fieldName} = {fieldName};\n" + "\t}\n\n";

    private static String gsfuncForForeign = "\tpublic {fieldType} get{fieldNameUp}() {\n\t\treturn this.{fieldName};\n"
            + "\t}\n\n" + "\tpublic void set{fieldNameUp}({fieldType} {fieldName}) {\n\t\tif({fieldName} == null){\n\t\t\treturn;\n\t\t}\n\t\tthis.{foreignId} = {fieldName}.getEId();\n\t\tthis.{fieldName} = {fieldName};\n" + "\t}\n\n";

    public EntityGenerator() {
    }

    public EntityGenerator initialize(GenerationAttribute generationAttribute) {
        this.generationAttribute = generationAttribute;

//		this.initGenerationParameter();
//		this.initParameters();

        return this;
    }
//	private void initParameters()
//	{
//		filePath = generationParameter.getFullPackagePath() + generationParameter.getEntityNameUp() + ".java";
//		daoTemplateAbsPath = generationParameter.getSrcBasePath() + daoTemplateLoc;
//
//		replacement = new HashMap<String, String>();
//		replacement.put("{packageName}", generationParameter.getPackageName());
//		replacement.put("{className}", generationParameter.getEntityNameUp());
//
//		String tablePrefix = sql.getShortCode()+"_";
//		replacement.put("{EntityAnnotation}", "label=\""+generationParameter.getGa().getEntityClass().getLabel()+"\",name=\""+tablePrefix+generationParameter.getEntityNameLow()+"\"");

//	}

    private void generateEntityCode() {
        StringBuffer field = new StringBuffer();
        StringBuffer funcs = new StringBuffer();
        List<FieldAttribute> fattris = this.generationAttribute.getFieldAttributeList();
        int index = 2;
        List<FieldAttribute> fattris1 = new ArrayList<FieldAttribute>();
        fattris1.addAll(fattris);
        for (FieldAttribute fa : fattris1) {
            field.append(generateFieldCode(fa, ++index, fattris));
        }
        for (FieldAttribute fa : fattris) {
            funcs.append(generateFuncCode(fa));
        }
        this.velocityContext.put("fieldArea", field.toString());
        this.velocityContext.put("funcArea", funcs.toString());
    }

    private String generateFieldCode(FieldAttribute fa, int index, List<FieldAttribute> fattris) {
        StringBuffer dsb = new StringBuffer("\t@DColumn(");
        StringBuffer sb = new StringBuffer("\t@Column(");
        StringBuffer asb = new StringBuffer();

        dsb.append("index=" + index);
        dsb.append(",label=\"" + fa.getColumnInfo().getLabel() + "\"");


        sb.append("name=\"#{columnName}\"");
        if (fa.getColumnInfo().getLength() > 0) {
            sb.append(",length=" + fa.getColumnInfo().getLength());
        } else {
            sb.append(",length=250");
        }
        String type = GeneratorUtil.isBaseDataType(fa.getType());
        if (type != null) {
            fa.setType(type);
            if (fa.getType().equals("Date")) {
                importArea.add("\nimport java.util.Date;");
            } else if (fa.getType().equals("Timestamp")) {
                importArea.add("\nimport java.util.Date;");
            } else if (fa.getType().equals("BigDecimal")) {
                importArea.add("\nimport java.math.BigDecimal;");
            }

//			sb.append("name=\"" + GeneratorUtil.subUptoLow(fa.getFieldName(), 0, 1) + "\"");

        } else if (fa.getType().startsWith("List<")) {
            importArea.add("\nimport java.util.List;");
            String foreignEntity = StringUtils.substringBetween(fa.getType(), "<", ">");
            importForeignEntity(foreignEntity);
        }
// 		else if (fa.getType().startsWith("Set<"))
//		{
//			importArea.add("\nimport java.util.Set;");
//			sb
//			type = fa.getType().substring(fa.getType().indexOf("<") + 1, fa.getType().indexOf(">"));
//			importArea.add("\n" + GeneratorUtil.getImport(new File(generationParameter.getBasePackagePath() + "/entity"), type));
//
//			type = fa.getType().substring(fa.getType().indexOf("<") + 1, fa.getType().indexOf(">"));
//			dsb.append(",codeTable=\"" + type + "\"");
//			fa.setType("String");
//
//		}
        else if (fa.getType().startsWith("CodeTable<")) {
//			sb.append("name=\"" + GeneratorUtil.subUptoLow(fa.getFieldName(), 0, 1) + "\"");
            type = fa.getType().substring(fa.getType().indexOf("<") + 1, fa.getType().indexOf(">"));
            dsb.append(",codeTable=\"" + type + "\"");
            fa.setType("String");
        }
//		else
//		{
//			importArea.add("\n" + GeneratorUtil.getImport(new File(generationParameter.getBasePackagePath() + "/entity"), fa.getType()));
//			sb.append(",name=\"" + GeneratorUtil.subUptoLow(fa.getType(), 0, 1) + "Id\"");
//		}
        else {
//			sb.append("name=\"" + GeneratorUtil.subUptoLow(fa.getType(), 0, 1) + "Id\"");
            dsb.append(",foreignEntity=\"" + fa.getType() + "\"");
//			dsb.append(",foreignKey=\"" + fa.getType() + "Id\"");

            String foreignEntity = fa.getType();
            importForeignEntity(foreignEntity);
            asb.append("\t@Transient\n");
            asb.append(String.format("\tprivate %s %s;\n", foreignEntity, fa.getFieldName()));
            FieldAttribute fieldAttribute = null;
            fieldAttribute = new FieldAttribute();
            fieldAttribute.setType(foreignEntity);
            fieldAttribute.setFieldName(fa.getFieldName());
            fattris.add(fieldAttribute);

            asb.append("\n\t@Transient\n");
            asb.append("\t@DColumn(index=" + index);
            asb.append(",label=\"" + fa.getColumnInfo().getLabel() + "\"");
            asb.append(",foreignEntity=\"" + fa.getType() + "\"");

            fieldAttribute = new FieldAttribute();
            fieldAttribute.setType("String");
            fieldAttribute.setFieldName(fa.getFieldName() + "Name");
            fattris.add(fieldAttribute);

        }

        if (!fa.getColumnInfo().isNullable()) {
            sb.append(",nullable=false");
        } else {
            sb.append(",nullable=true");
        }

        if (fa.getColumnInfo().isUnique()) {
            sb.append(",unique=true");
        } else {
            sb.append(",unique=false");
        }

        if (fa.getColumnInfo().getComment() == null) {
            fa.getColumnInfo().setComment(fa.getColumnInfo().getLabel());
        }
        dsb.append(",comment=\"" + fa.getColumnInfo().getComment() + "\"");
        dsb.append(")\n");

        if (asb.length() > 0) {

            asb.append(",comment=\"" + fa.getColumnInfo().getComment() + "\"");
            asb.append(")\n");
            asb.append("\t" + fa.getVisitModifier() + " String " + fa.getFieldName() + "Name;\n\n");
            fa.setFieldName(fa.getFieldName() + "Id");
            fa.setType("Long");
        }

        sb.append(")\n");

        if (fa.getType().startsWith("List<")) {
            dsb = new StringBuffer();
            sb = new StringBuffer();
            sb.append("\t@Transient\n");
        }
        sb.append("\t" + fa.getVisitModifier() + " " + fa.getType() + " " + fa.getFieldName() + ";\n\n");

        sb.insert(0, dsb.toString());
        sb.append(asb.toString());

        String columnName = D4Util.convertCamelCaseName2UnderScore(fa.getFieldName());
        String fieldCode = sb.toString();
        fieldCode = StringUtils.replace(fieldCode, "#{columnName}", columnName);

        return fieldCode;
    }

    private void importForeignEntity(String foreignEntity) {
        if (!this.generationAttribute.getEntityClass().getClassName().equals(foreignEntity)) {
            EntityClass foreignEntityClass = new EntityManager().getEntityClassByClassName(foreignEntity);
            if (foreignEntityClass == null) {
                String error = String.format("没找到名为 %s 的实体", foreignEntity);
                logger.error(error);
                throw new DDDException(error);
            }
            importArea.add("\nimport " + foreignEntityClass.getClazz().getName() + ";");
        }
    }

    private String generateFuncCode(FieldAttribute fa) {
        String baseType = GeneratorUtil.isBaseDataType(fa.getType());
        String fieldNameUp = GeneratorUtil.subLowToUp(fa.getFieldName(), 0, 1);
        String type = fa.getType();
        if (baseType != null || type.startsWith("CodeTable<") || type.startsWith("List<")) {
            String res = EntityGenerator.gsfunc;
            res = res.replace("{fieldType}", fa.getType());
            res = res.replace("{fieldNameUp}", fieldNameUp);
            res = res.replace("{fieldName}", fa.getFieldName());
            return res;
        } else {
            String res = EntityGenerator.gsfuncForForeign;
            res = res.replace("{fieldType}", fa.getType());
            res = res.replace("{fieldNameUp}", fieldNameUp);
            res = res.replace("{fieldName}", fa.getFieldName());
            res = res.replace("{foreignId}", fa.getFieldName() + "Id");
            return res;
        }
    }

    @Override
    public void generate() {
        this.generateEntityCode();
        StringBuffer sb = new StringBuffer();
        for (String string : importArea) {
            sb.append(string);
        }
        this.velocityContext.put("entityClass", this.generationAttribute.getEntityClass());
        this.velocityContext.put("fieldAttributes", this.generationAttribute.getFieldAttributeList());
        this.velocityContext.put("importArea", sb.toString());

        super.generate();
//		GeneratorUtil.generateFromTemplate(daoTemplateAbsPath, filePath, replacement, coverWrite);
    }

    @Override
    public String getTemplateFile() {
        return this.templateLoc;
    }

    public Boolean getCoverWrite() {
        return coverWrite;
    }

    public void setCoverWrite(Boolean coverWrite) {
        this.coverWrite = coverWrite;
    }

//	private void initGenerationParameter() {
//		this.generationParameter.ga = generationAttribute;
//
//		this.generationParameter.fieldAttributeList = generationAttribute.getFieldAttributeList();
//	}
}
