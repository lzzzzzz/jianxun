<#import "language.ftl" as Language>
<#macro breadcrumb paths>
    <div class="cus-breadcrumb hidden-xs">
        <div class="container">
            <p>
                ${Language.lans[lan].breadcrumb_position}:
                <#list paths as path>
                    <#if path_index == paths?size - 1>
                        <a href="${path.url}" class="btn current" title="${path[lan]}">${path[lan]}</a>
                    <#else>
                        <a href="${path.url}" class="btn" title="${path[lan]}">${path[lan]}</a> -
                    </#if>
                </#list>
            </p>
        </div>
    </div>
</#macro>