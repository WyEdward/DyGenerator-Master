package ${managerPackage};

import ${domainPackage}.${title};
import com.dayouzc.e2eplatform.core.common.ConditionRangeValue;
import com.dayouzc.e2eplatform.core.dto.equip.ConnectionInfoDTO;
import com.dayouzc.e2eplatform.core.mybatis.BaseManager;
import com.github.pagehelper.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author ${author}
 * @createTime ${time}
 */
public interface ${title}Manager extends BaseManager<${title}> {

    //分页查询
    Page<${title}> query${title}ByMore(${title} ${title?uncap_first}, ConditionRangeValue rangeValue, Pageable pageable);
    //新增
    ${title} create${title}(${title} ${title?uncap_first}, ConnectionInfoDTO tokenInfo);
    //修改
    ${title} update${title}(${title} ${title?uncap_first}, ConnectionInfoDTO tokenInfo);
    //删除
    ${title} delete${title}(${title} ${title?uncap_first},ConnectionInfoDTO tokenInfo);



}
