<#import 'common/base.ftl' as base>
<#import 'common/breadcrumb.ftl' as breadcrumb>
<#import 'common/language.ftl' as Language>
<@base.base_container
title="${training.title}"
headers=[
"/css/video-js.min.css",
"/css/breadcrumb.css",
"/css/quill.snow.css",
"/css/training-detail.css"
]
scripts=[
"/js/videojs-ie8.min.js",
"/js/video.min.js",
"/js/training-detail.js"
]>
    <div class="training-detail">
        <@breadcrumb.breadcrumb paths=[
        {
        "url": "${Language.lans[lan].url_path}index.html",
        "zh": "首页",
        "en": "Home"
        }, {
        "url": "#",
        "zh": "服务中心",
        "en": "Service Center"
        }, {
        "url": "${Language.lans[lan].url_path}training.html",
        "zh": "培训课堂",
        "en": "Training Class"
        }] />
        <div class="training-container">
            <div class="container">
                <div class="training-content">
                    <h1 class="title">${training.title}</h1>
                    <p class="training-times hidden-xs">
                        ${training.createdTime?string('yyyy年MM月dd日')}
                    </p>
                    <div class="training-tags hidden-xs">
                        <#if training.tagList?? && training.tagList?size gt 0>
                            <#list training.tagList as tag>
                                <#if lan == 'en'>
                                    <p class="training-tag">${(tag.enName)!''}</p>
                                <#else>
                                    <p class="training-tag">${(tag.name)!''}</p>
                                </#if>
                            </#list>
                        </#if>
                    </div>
                    <div class="banner">
                        <#assign hadVideo=false>
                        <#assign hadOther=false>
                        <#if training.resourceList?? && training.resourceList?size gt 0>
                            <#list training.resourceList as resource>
                                <#if ((resource.resourceType)!'') == 'VIDEO'>
                                    <#assign hadVideo=true>
                                    <video id="example_video_1"
                                           class="video-js vjs-default-skin vjs-big-play-centered vjs-fluid"
                                           controls preload="auto"
                                           width="400"
                                           height="300"
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
                        <#if hadVideo == false && training.bannerList?? && training.bannerList?size gt 0>
                            <img src="${(training.bannerList[0].resourceUrl)!''}${(training.bannerList[0].params)!''}"
                                 alt="">
                        </#if>
                    </div>
                    <div class="ql-container ql-snow">
                        <div id="content" class="ql-editor">
                            ${training.content}
                        </div>
                    </div>
                    <#if hadOther == true && training.resourceList?? && training.resourceList?size gt 0>
                        <div class="downloads">
                            <h2 class="downloads-title">
                                ${Language.lans[lan].training_download_title}
                            </h2>
                            <div class="download-items">
                                <#list training.resourceList as resource>
                                    <#if ((resource.resourceType)!'') == "OTHERS">
                                        <a href="${(resource.resourceUrl)!'#'}" download="${resource.resourceName}" title="${(resource.resourceName)!''}"
                                           class="down-resource" target="_blank">
                                            <img src="http://img.bnibt.com/static/dowload.png" alt="">
                                            <p>${(resource.resourceName)!''}</p>
                                        </a>
                                    </#if>
                                </#list>
                            </div>
                        </div>
                    </#if>
                </div>
                <div class="related-training hidden-xs">
                    <h3 class="related-title">
                        ${Language.lans[lan].training_related_news}
                    </h3>
                    <ul>
                        <#if related?? && related?size gt 0>
                            <#list related as related>
                                <li>
                                    <a href="${Language.lans[lan].url_path}training/${related.id}.html"
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
            <div class="related-training visible-xs-block">
                <h3 class="related-title">
                    相关推荐
                </h3>
                <ul>
                    <#list related as related>
                        <li>
                            <a href="${Language.lans[lan].url_path}training/${related.id}.html"
                               title="${related.title}">
                                ${related.title}
                            </a>
                        </li>
                    </#list>
                </ul>
            </div>
        </#if>
    </div>

</@base.base_container>