package ${basepackage!""}.${subpackage!""};


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

<#function dashedToCamel(s)>
<#return s
        ?replace('(^_+)|(_+$)', '', 'r')
        ?replace('\\_+(\\w)?', ' $1', 'r')
        ?replace('([A-Z])', ' $1', 'r')
        ?capitalize
        ?replace(' ' , '')
        ?uncap_first
        >
</#function>

@ApiModel("${className!""}显示模型")
public class ${className!""}Dto {
<#if attrs??>
    <#list attrs!"" as attr>
    @ApiModelProperty(value = "${attr.desc!""}")
    <#if attr.value == "Date">
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    </#if>
    public ${attr.value} ${dashedToCamel(attr.name)};
    </#list>

    <#list attrs!"" as attr>
    public void set${dashedToCamel(attr.name)?cap_first}(${attr.value} ${dashedToCamel(attr.name)}){
        this.${dashedToCamel(attr.name)} = ${dashedToCamel(attr.name)};
    }

    public ${attr.value} get${dashedToCamel(attr.name)?cap_first}(){
        return this.${dashedToCamel(attr.name)};
    }
    </#list>
</#if>
}