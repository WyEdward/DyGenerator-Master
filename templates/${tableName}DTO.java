package ${DTOPackage};

import com.fasterxml.jackson.annotation.JsonInclude;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author ${author}
 * @createTime ${time}
 */
@JsonInclude(value = JsonInclude.Include.NON_EMPTY)
public class ${title}DTO {
<#list table as abc>
	<#if abc.columnType=="Date">
		/**
		* ${abc.columnComment}
		*/
		private String ${abc.columnName2};
	</#if>
	<#if abc.columnType!="Date">
	    /**
		* ${abc.columnComment}
		*/
		private ${abc.columnType} ${abc.columnName2};
	</#if>
</#list>
<#list table as abc>
	<#if abc.columnType=="Date">

	public String get${abc.columnName2?cap_first}() {
        return this.${abc.columnName2};
    }

	public void set${abc.columnName2?cap_first}(String ${abc.columnName2}) {
        this.${abc.columnName2} = ${abc.columnName2};
    }
	</#if>
	<#if abc.columnType!="Date">
	public ${abc.columnType} get${abc.columnName2?cap_first}() {
        return this.${abc.columnName2};
    }

	public void set${abc.columnName2?cap_first}(${abc.columnType} ${abc.columnName2}) {
        this.${abc.columnName2} = ${abc.columnName2};
    }
	</#if>
</#list>
}
