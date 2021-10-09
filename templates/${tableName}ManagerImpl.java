package ${managerImplPackage};

import com.dayouzc.e2eapp.ebusiness.constant.TemplateConstant;
import ${domainPackage}.${title};
import ${managerPackage}.${title}Manager;
import ${mapperPackage}.${title}Mapper;
import com.dayouzc.e2eapp.ebusiness.util.EbIDGenerator;
import com.dayouzc.e2eplatform.core.common.ConditionRangeValue;
import com.dayouzc.e2eplatform.core.dto.equip.ConnectionInfoDTO;
import com.dayouzc.e2eplatform.core.mybatis.AbstractManager;
import com.dayouzc.e2eplatform.core.util.DateUtil;
import com.dayouzc.e2eplatform.core.util.QueryUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Date;

/**
 * @author ${author}
 * @createTime ${time}
 */
@Repository
public class ${title}ManagerImpl extends AbstractManager<${title}> implements ${title}Manager {

    @Autowired
    private ${title}Mapper ${title?uncap_first}Mapper;

    @Override
    public Page<${title}> query${title}ByMore(${title} ${title?uncap_first}, ConditionRangeValue rangeValue, Pageable pageable) {
        if (pageable == null) {
            pageable = new PageRequest(0, 100);
        }
        PageHelper.startPage(pageable.getPageNumber() + 1, pageable.getPageSize());
        String orderByStr = QueryUtil.getOrderByStr(pageable);
        return (Page<${title}>) ${title?uncap_first}Mapper.queryAll(${title?uncap_first},rangeValue,orderByStr);
    }

    @Override
    public ${title} create${title}(${title} paramObj, ConnectionInfoDTO tokenInfo) {
        Date currentTime = DateUtil.getCurrentTime(tokenInfo);
        paramObj.setCmservId(EbIDGenerator.generateID(paramObj, tokenInfo));

        // 设置系统字段
        paramObj.setAcctStatus(TemplateConstant.Status.NORMAL);
        paramObj.setCreateTime(currentTime);
        paramObj.setUpdateTime(currentTime);

        ${title?uncap_first}Mapper.insertSelective(paramObj);
        ${title} ${title?uncap_first} = ${title?uncap_first}Mapper.selectByPrimaryKey(paramObj);
        return ${title?uncap_first};
    }

    @Override
    public ${title} update${title}(${title} paramObj, ConnectionInfoDTO tokenInfo) {
        Date currentTime = DateUtil.getCurrentTime(tokenInfo);
        // 设置系统字段
        paramObj.setUpdateTime(currentTime);

        ${title?uncap_first}Mapper.updateByPrimaryKeySelective(paramObj);
        return ${title?uncap_first}Mapper.selectByPrimaryKey(paramObj);
    }

    @Override
    public ${title} delete${title}(${title} ${title?uncap_first}, ConnectionInfoDTO tokenInfo) {
        Date currentTime = DateUtil.getCurrentTime(tokenInfo);
        // 设置系统字段
        ${title?uncap_first}.setUpdateTime(currentTime);
        ${title?uncap_first}.setAcctStatus(TemplateConstant.Status.DELETED);
        ${title?uncap_first}Mapper.updateByPrimaryKeySelective(${title?uncap_first});

        return ${title?uncap_first}Mapper.selectByPrimaryKey(${title?uncap_first});
    }

  
}
