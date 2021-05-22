package ${package.Mapper};

import ${package.Entity}.${table.entityName};
import ${superMapperClassPackage};
import org.apache.ibatis.annotations.Mapper;

/**
* <p>
* ${table.comment!} Mapper 接口
* </p>
*
* @author ${author}
* @since ${date}
*/
@Mapper
public interface ${table.mapperName} extends ${superMapperClass}<${table.entityName}> {

}