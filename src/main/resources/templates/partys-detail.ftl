<#import 'common/base.ftl' as base>
<#import 'common/breadcrumb.ftl' as breadcrumb>
<#import 'common/language.ftl' as Language>
<@base.base_container
title="${partys.title}"
headers=[
"/css/video-js.min.css",
"/css/breadcrumb.css",
"/css/quill.snow.css",
"/css/partys-detail.css"
]
scripts=[
"/js/videojs-ie8.min.js",
"/js/video.min.js",
"/js/partys-detail.js"
]>
    <div class="partys-detail">
        <@breadcrumb.breadcrumb paths=[
        {
        "url": "${Language.lans[lan].url_path}index.html",
        "zh": "首页",
        "en": "Home"
        }, {
        "url": "#",
        "zh": "关于我们",
        "en": "About Us"
        }, {
        "url": "${Language.lans[lan].url_path}partys.html",
        "zh": "党建工作",
        "en": "Partys"
        }] />
        <div class="partys-container">
            <div class="container">
                <div class="partys-content">
                    <h1 class="title">${partys.title}</h1>
                    <p class="partys-times hidden-xs">
                        ${partys.createdTime?string('yyyy-MM-dd')}
                    </p>
                    <div class="partys-tags hidden-xs">
                        <#if partys.tagList?? && partys.tagList?size gt 0>
                            <#list partys.tagList as tag>
                                <#if lan == 'en'>
                                    <p class="partys-tag">${(tag.enName)!''}</p>
                                <#else>
                                    <p class="partys-tag">${(tag.name)!''}</p>
                                </#if>
                            </#list>
                        </#if>
                    </div>
                    <div class="banner">
                        <#assign hadVideo=false>
                        <#assign hadOther=false>
                        <#if partys.resourceList?? && partys.resourceList?size gt 0>
                            <#list partys.resourceList as resource>
                                <#if ((resource.resourceType)!'') == 'VIDEO'>
                                    <#assign hadVideo=true>
                                    <video id="example_video_1"
                                           class="video-js vjs-default-skin vjs-big-play-centered vjs-fluid"
                                           controls preload="auto"
                                           data-setup='{"example_option":true}'>
                                        <source src="${resource.resourceUrl}" type="video/mp4"/>
                                        <p class="vjs-no-js">To view this video please enable JavaScript, and consider
                                            upgrading to a web browser that <a
                                                    href="http://videojs.com/html5-video-support/" target="_blank">supports
                                                HTML5 video</a></p>
                                    </video>
                                </#if>
                                <#if ((resource.resourceType)!'') == 'OTHERS'>
                                    <#assign hadOther=true>
                                </#if>
                            </#list>
                        </#if>
                        <#if hadVideo == false && partys.bannerList?? && partys.bannerList?size gt 0>
                            <img src="${(partys.bannerList[0].resourceUrl)!''}${(partys.bannerList[0].params)!''}"
                                 alt="">
                        </#if>
                    </div>
                    <div class="ql-container ql-snow">
                        <div id="content" class="ql-editor">
                            ${partys.content}
                        </div>
                    </div>
                    <#if hadOther == true && partys.resourceList?? && partys.resourceList?size gt 0>
                        <div class="downloads">
                            <h2 class="downloads-title">
                                ${Language.lans[lan].training_download_title}
                            </h2>
                            <div class="download-items">
                                <#list partys.resourceList as resource>
                                    <#if ((resource.resourceType)!'') == "OTHERS">
                                        <a href="${(resource.resourceUrl)!'#'}" download="${resource.resourceName}" title="${(resource.resourceName)!''}"
                                           class="down-resource" target="_blank">
                                            <img src="http://img.bnibt.com/static/dowload.png"
                                                 alt="">
                                            <p>${(resource.resourceName)!''}</p>
                                        </a>
                                    </#if>
                                </#list>
                            </div>
                        </div>
                    </#if>
                </div>
                <div class="related-partys hidden-xs">
                    <h3 class="related-title">
                        ${Language.lans[lan].training_related_news}
                    </h3>
                    <ul>
                        <#if related?? && related?size gt 0>
                            <#list related as related>
                                <li>
                                    <a href="${Language.lans[lan].url_path}partys/${related.id}.html"
                                       title="${related.title}">
                                        ${related.title}
                                    </a>
                                </li>
                            </#list>
                        <#else>
                            <div class="related-none">
                                <p>${Language.lans[lan].training_related_none}</p>
                            </div>
                        </#if>
                    </ul>
                </div>
            </div>
        </div>
        <#if related?? && related?size gt 0>
            <div class="related-partys visible-xs-block">
                <h3 class="related-title">
                    ${Language.lans[lan].training_related_news}
                </h3>
                <ul>
                    <#list related as related>
                        <li>
                            <a href="${Language.lans[lan].url_path}partys/${related.id}.html" title="${related.title}">
                                ${related.title}
                            </a>
                        </li>
                    </#list>
                </ul>
            </div>
        </#if>
    </div>

</@base.base_container>