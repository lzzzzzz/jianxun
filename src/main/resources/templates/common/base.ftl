<#macro base_container title="title" headers=[] scripts=[] needHeader=true needFooter=true>
<!DOCTYPE html>
<#import "app.ftl" as app>
<html lang="${lan}">
    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="format-detection" content="telephone=no">
        <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
        <meta content="产品,试剂,医疗,北方所" name="Keywords">
        <meta name="description" content="北方所创立于1985年，隶属于中国核工业集团有限公司旗下的中国同辐股份有限公司（中国同辐，1763．HK），专注于体外诊断试剂产品的研发、生产、经营及服务，产品涵盖放射免疫、化学发光、酶联免疫、时间分辨、胶体金和生物原材料等技术平台，拥有150多项产品注册证书，形成了覆盖全国的营销网络，产品出口至亚非欧等10余个国家或地区，能够为体外诊断领域提供全面的产品解决方案和整体服务。">
        <title>${title}</title>

        <link rel="shortcut icon" href="/favicon.ico" type="image/x-icon">
        <link rel="stylesheet" type="text/css" href="/css/bootstrap.min.css">
        <link rel="stylesheet" href="/css/common.css"/>
        <link rel="stylesheet" href="/css/login.css"/>
        <#list headers as header>
            <link rel="stylesheet" type="text/css" href="${header}"/>
        </#list>
        <!--[if lt IE 9]>
            <script src="https://cdn.bootcss.com/html5shiv/3.7.3/html5shiv-printshiv.min.js"></script>
            <script src="/js/respond.min.js"></script>
        <![endif]-->
        <link href="/css/toastr.min.css" rel="stylesheet">
    </head>
    <body>
        <#if needHeader == true>
            <#include "header.ftl">
        </#if>
        <#nested>
        <#if needFooter == true>
            <#include "footer.ftl">
        </#if>
        <script src="/js/ie10-viewport-bug-workaround.js"></script>
        <script src="/js/jquery.min.js"></script>
        <script src="/js/jquery.cookie.js"></script>
        <script src="/js/bootstrap.min.js"></script>
        <script src="/js/wechat/wxLogin.js"></script>
        <script src="/js/base.js"></script>
        <script src="/js/header.js"></script>
        <#list scripts as script>
            <script src="${script}"></script>
        </#list>
        <script src="/js/toastr.min.js"></script>
        <#--<script src="js/flexible.min.js"></script>-->
    </body>
</html>
</#macro>