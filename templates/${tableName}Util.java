package ${domainUtilPackage};

import ${domainPackage}.${title};
import ${DTOPackage}.${title}DTO;
import com.dayouzc.e2eplatform.core.dto.equip.ConnectionInfoDTO;
import com.dayouzc.e2eplatform.core.dto.ucl.ContentObjDTO;
import com.dayouzc.e2eplatform.core.dto.ucl.UclDTO;
import com.dayouzc.e2eplatform.core.util.DateUtils;
import com.dayouzc.e2eplatform.core.util.JsonUtil;
import com.github.pagehelper.Page;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.ClassUtils;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ${author}
 * @createTime ${time}
 */
public class ${title}Util {


    public static ${title} dto2Domain(${title}DTO dto){
        if(dto==null) return null;

        ${title} domain = new ${title}();
        BeanUtils.copyProperties(dto,domain);
		<#list table as abc>
			<#if abc.columnType=="Date">
		domain.setExpiryTime(DateUtils.parseDate(dto.get${abc.columnName2?cap_first}(), "yyyy-MM-dd HH:mm:ss"));
			</#if>
		</#list>
        return domain;
    }

    public static List<${title}> dto2Domain(List<${title}DTO> dtoList){
        if(dtoList==null || dtoList.isEmpty()) return null;
        List<${title}> domainList = new ArrayList<>();
        for(${title}DTO dto : dtoList){
            domainList.add(dto2Domain(dto));
        }
        return domainList;
    }

    public static ${title}DTO domain2Dto(${title} domain){
        if(domain==null) return null;

        ${title}DTO dto = new ${title}DTO();
        BeanUtils.copyProperties(domain,dto);

        //类型转换
		<#list table as abc>
			<#if abc.columnType=="Date">
		domain.setExpiryTime(DateUtils.parseDate(dto.get${abc.columnName2?cap_first}(), "yyyy-MM-dd HH:mm:ss"));
			</#if>
		</#list>
        return dto;
    }

    public static List<${title}DTO> domain2Dto(List<${title}> domainList){
        if(domainList==null || domainList.isEmpty()) return null;
        List<${title}DTO> dtoList = new ArrayList<>();
        for(${title} domain : domainList){
            dtoList.add(domain2Dto(domain));
        }
        return dtoList;
    }

    public static Page<${title}DTO> domain2Dto(Page<${title}> domainPage){
        if(domainPage==null || domainPage.isEmpty()) return null;
        Page<${title}DTO> dtoPage = new Page<>();
        BeanUtils.copyProperties(domainPage, dtoPage);
        for(${title} domain : domainPage){
            dtoPage.add(domain2Dto(domain));
        }
        return dtoPage;
    }

}
