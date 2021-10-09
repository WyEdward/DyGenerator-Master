package ${mapperPackage};

import ${domainPackage}.${title};
import com.dayouzc.e2eplatform.core.common.ConditionRangeValue;
import org.apache.ibatis.annotations.Param;
import com.dayouzc.e2eplatform.core.mybatis.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author ${author}
 * @createTime ${time}
 */
@Mapper
public interface ${title}Mapper extends BaseMapper<${title}> {

    List<${title}> queryAll(@Param("${title?uncap_first}") ${title} ${title?uncap_first}, @Param("rangeValue") ConditionRangeValue rangeValue, @Param("orderByStr") String orderByStr);

}
