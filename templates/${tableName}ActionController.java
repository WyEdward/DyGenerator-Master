package ${actionPackage};

import ${domainPackage}.${title};
import ${DTOPackage}.${title}DTO;
import ${servicePackage}.${title}Service;
import ${servicePackage}.${title}Util;
import com.dayouzc.e2eplatform.core.base.MediaTypes;
import com.dayouzc.e2eplatform.core.common.ConditionRangeValue;
import com.dayouzc.e2eplatform.core.dto.common.RequestData;
import com.dayouzc.e2eplatform.core.dto.common.ResponseData;
import com.dayouzc.e2eplatform.core.dto.equip.ConnectionInfoDTO;
import com.dayouzc.e2eplatform.core.exception.E2EServiceException;
import com.dayouzc.e2eplatform.core.util.JsonUtil;
import com.dayouzc.e2eplatform.core.util.ResponseDataUtil;
import com.dayouzc.e2eplatform.core.util.SpringUtils;
import com.github.pagehelper.Page;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ${author}
 * @createTime ${time}
 */
@RestController
@RequestMapping("/${title?uncap_first}")
public class ${title}ActionController {

    private static final Logger logger = LoggerFactory.getLogger(${title}ActionController.class);

    @Autowired
    private ${title}Service ${title?uncap_first}Service;


    @RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaTypes.JSON_UTF_8)
    public ResponseData<${title}DTO> create(
            @RequestBody RequestData<${title}DTO> requestData,
            ConditionRangeValue rangeValue,
            @RequestAttribute(name = "tokenInfo") ConnectionInfoDTO tokenInfo) {
        ${title}DTO appData = requestData.getAppData();
        ${title} ${title?uncap_first} = ${title?uncap_first}Service.create${title}(${title}Util.dto2Domain(appData), tokenInfo, rangeValue);
        // 4.响应
        ResponseData responseData = new ResponseData();
        responseData.setResult(${title}Util.domain2Dto(${title?uncap_first}));
        return responseData;
    }

   
    @RequestMapping(value = "/update", method = RequestMethod.POST, consumes = MediaTypes.JSON_UTF_8)
    public ResponseData<${title}DTO> update(
            @RequestBody RequestData<${title}DTO> requestData,
            ConditionRangeValue rangeValue,
            @RequestAttribute("tokenInfo") ConnectionInfoDTO tokenInfo) {
        ${title}DTO appData = requestData.getAppData();
        ${title} ${title?uncap_first} = ${title?uncap_first}Service.update${title}(${title}Util.dto2Domain(appData), tokenInfo, rangeValue);
        // 4.响应
        ResponseData responseData = new ResponseData();
        responseData.setResult(${title}Util.domain2Dto(${title?uncap_first}));
        return responseData;
    }

    
    @RequestMapping(value = "/delete", method = RequestMethod.POST, consumes = MediaTypes.JSON_UTF_8)
    public ResponseData<${title}DTO> delete(
            @RequestBody RequestData<${title}DTO> requestData,
            ConditionRangeValue rangeValue,
            @RequestAttribute("tokenInfo") ConnectionInfoDTO tokenInfo) {
        ${title}DTO appData = requestData.getAppData();
        
        ${title} ${title?uncap_first} = ${title?uncap_first}Service.delete${title}(${title}Util.dto2Domain(appData), tokenInfo);
        // 4.响应
        ResponseData responseData = new ResponseData();
        responseData.setResult(${title}Util.domain2Dto(${title?uncap_first}));
        return responseData;
    }

}
