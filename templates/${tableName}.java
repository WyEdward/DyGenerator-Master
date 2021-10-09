package ${domainPackage};

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author ${author}
 * @createTime ${time}
 */
@Table(name = "${tableName}")
public class ${title} {
	
<#list table as abc>
	<#if abc.columnKey==true>
	/**
	* ${abc.columnComment}
	*/
	@Id
	@Column(name = "${abc.columnName}")
	private ${abc.columnType} ${abc.columnName2};
	</#if>
	<#if abc.columnKey==false>
	/**
	* ${abc.columnComment}
	*/
	@Column(name = "${abc.columnName}")
	private ${abc.columnType} ${abc.columnName2};
	</#if>
	
	
	
</#list>
<#list table as abc>
	public ${abc.columnType} get${abc.columnName2?cap_first}() {
        return this.${abc.columnName2};
    }

	public void set${abc.columnName2?cap_first}(${abc.columnType} ${abc.columnName2}) {
        this.${abc.columnName2} = ${abc.columnName2};
    }
</#list>

   

}
