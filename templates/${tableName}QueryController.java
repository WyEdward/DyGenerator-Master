package ${queryPackage};

import ${domainPackage}.${title};
import ${DTOPackage}.${title}DTO;
import ${servicePackage}.${title}Service;
import ${servicePackage}.${title}Util;
import com.dayouzc.e2eplatform.core.base.MediaTypes;
import com.dayouzc.e2eplatform.core.common.ConditionRangeValue;
import com.dayouzc.e2eplatform.core.dto.common.RequestData;
import com.dayouzc.e2eplatform.core.dto.common.ResponseData;
import com.dayouzc.e2eplatform.core.dto.equip.ConnectionInfoDTO;
import com.dayouzc.e2eplatform.core.util.ResponseDataUtil;
import com.github.pagehelper.Page;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author ${author}
 * @createTime ${time}
 */
@RestController
@RequestMapping("/${title?uncap_first}")
public class ${title}QueryController {

    private static final Logger logger = LoggerFactory.getLogger(${title}ActionController.class);

    @Autowired
    private ${title}Service ${title?uncap_first}Service;

    
    

    /**
     * 分页查询商品服务
     * @param requestData
     * @param rangeValue
     * @param pageable
     * @return
     */
    @RequestMapping(value = "/findAllByPage", method = RequestMethod.POST, consumes = MediaTypes.JSON_UTF_8)
    public ResponseData<${title}DTO> findAllByPage(
            @RequestBody RequestData<${title}DTO> requestData,
            ConditionRangeValue rangeValue,
            @PageableDefault(value = 10) Pageable pageable) {
        ${title}DTO appData = requestData.getAppData();
        Page<${title}> ${title?uncap_first}s = ${title?uncap_first}Service.query${title}ByMore(${title}Util.dto2Domain(appData), rangeValue, pageable);
        // 4.响应
        ResponseData responseData = new ResponseData();
        ResponseDataUtil.setResponseDataPageInfo(responseData,${title?uncap_first}s);
        responseData.setResult(${title}Util.domain2Dto(${title?uncap_first}s));
        return responseData;
    }
}
