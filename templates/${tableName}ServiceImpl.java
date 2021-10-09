package ${serviceImplPackage};

import ${domainPackage}.${title};
import ${managerPackage}.*;
import ${servicePackage}.${title}Service;
import com.dayouzc.e2eplatform.core.common.ConditionRangeValue;
import com.dayouzc.e2eplatform.core.dto.equip.ConnectionInfoDTO;
import com.dayouzc.e2eplatform.core.exception.E2EServiceException;
import com.github.pagehelper.Page;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @author ${author}
 * @createTime ${time}
 */
@Service
@Transactional
public class ${title}ServiceImpl implements ${title}Service {

    private static Logger logger = LoggerFactory.getLogger(${title}ServiceImpl.class);

   
    @Autowired
    private ${title}Manager ${title?uncap_first}Manager;


    @Override
    public Page<${title}> query${title}ByMore(${title} ${title?uncap_first}, ConditionRangeValue rangeValue, Pageable pageable) {
        return ${title?uncap_first}Manager.query${title}ByMore(${title?uncap_first},rangeValue,pageable);
    }

    @Override
    public ${title} create${title}(${title} ${title?uncap_first}, ConnectionInfoDTO tokenInfo) {
        return ${title?uncap_first}Manager.create${title}(${title?uncap_first},tokenInfo);
    }

    @Override
    public ${title} update${title}(${title} ${title?uncap_first}, ConnectionInfoDTO tokenInfo) {
        return ${title?uncap_first}Manager.update${title}(${title?uncap_first},tokenInfo);
    }

    @Override
    public ${title} delete${title}(${title} ${title?uncap_first}, ConnectionInfoDTO tokenInfo) {
        return ${title?uncap_first}Manager.delete${title}(${title?uncap_first},tokenInfo);
    }

 
}
