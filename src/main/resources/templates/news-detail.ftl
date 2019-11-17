<#import 'common/base.ftl' as base>
<#import 'common/breadcrumb.ftl' as breadcrumb>
<#import 'common/language.ftl' as Language>
<@base.base_container
title="${news.title}"
headers=[
"/css/video-js.min.css",
"/css/breadcrumb.css",
"/css/quill.snow.css",
"/css/news-detail.css"
]
scripts=[
"/js/videojs-ie8.min.js",
"/js/video.min.js",
"/js/news-detail.js"
]>
    <div class="news-detail">
        <@breadcrumb.breadcrumb paths=[
        {
        "url": "${Language.lans[lan].url_path}index.html",
        "zh": "首页",
        "en": "Home"
        }, {
        "url": "${Language.lans[lan].url_path}news.html",
        "zh": "新闻中心",
        "en": "News"
        }] />
        <div class="news-container">
            <div class="container">
                <div class="news-content">
                    <h1 class="title">${news.title}</h1>
                    <p class="news-times hidden-xs">
                        ${news.createdTime?string('yyyy-MM-dd')}
                    </p>
                    <div class="news-tags hidden-xs">
                        <#if news.tagList?? && news.tagList?size gt 0>
                            <#list news.tagList as tag>
                                <#if lan == 'en'>
                                    <p class="news-tag">${(tag.enName)!''}</p>
                                <#else>
                                    <p class="news-tag">${(tag.name)!''}</p>
                                </#if>
                            </#list>
                        </#if>
                    </div>
                    <div class="banner">
                        <#assign hadVideo=false>
                        <#assign hadOther=false>
                        <#if news.resourceList?? && news.resourceList?size gt 0>
                            <#list news.resourceList as resource>
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
                        <#if hadVideo == false>
                            <img src="${(news.bannerList[0].resourceUrl)!''}${(news.bannerList[0].params)!''}" alt="">
                        </#if>
                    </div>
                    <div class="ql-container ql-snow">
                        <div id="content" class="ql-editor">
                            ${news.content}
                        </div>
                    </div>
                    <#if news.type == "CAMPAIGN">
                        <#setting url_escaping_charset="UTF-8">
                        <div class="qrcode" data-content="${news.id}">
                            <img src="" alt="">
                            <p>${Language.lans[lan].news_wechat_qrcode}</p>
                        </div>
                    </#if>
                    <#if hadOther == true && news.resourceList?? && news.resourceList?size gt 0>
                        <div class="downloads">
                            <h2 class="downloads-title">
                                ${Language.lans[lan].training_download_title}
                            </h2>
                            <div class="download-items">
                                <#list news.resourceList as resource>
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
                <#if relatedNews?? && relatedNews?size gt 0>
                    <div class="related-news hidden-xs">
                        <h3 class="related-title">
                            ${Language.lans[lan].training_related_news}
                        </h3>
                        <ul>
                            <#if relatedNews??>
                                <#list relatedNews as related>
                                    <li>
                                        <a href="${Language.lans[lan].url_path}news/${related.id}.html"
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
                </#if>
            </div>
        </div>
        <#if relatedNews?? && relatedNews?size gt 0>
            <div class="related-news visible-xs-block">
                <h3 class="related-title">
                    ${Language.lans[lan].training_related_news}
                </h3>
                <ul>
                    <#list relatedNews as related>
                        <li>
                            <a href="${Language.lans[lan].url_path}news/${related.id}.html" title="${related.title}">
                                ${related.title}
                            </a>
                        </li>
                    </#list>
                </ul>
            </div>
        </#if>
    </div>

</@base.base_container>