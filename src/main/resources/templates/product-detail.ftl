<#import 'common/base.ftl' as base>
<#import 'common/breadcrumb.ftl' as breadcrumb>
<#import 'common/language.ftl' as Language>
<@base.base_container
title="${(product.title)!'产品详情'}"
headers=[
"/css/video-js.min.css",
"/css/swiper.css",
"/css/breadcrumb.css",
"/css/quill.snow.css",
"/css/product-detail.css"
]
scripts=[
"/js/videojs-ie8.min.js",
"/js/video.min.js",
"/js/swiper.min.js",
"/js/product-detail.js"
]>
    <@breadcrumb.breadcrumb paths=[
    {
    "url": "${Language.lans[lan].url_path}index.html",
    "zh": "首页",
    "en": "Home"
    }, {
    "url": "${Language.lans[lan].url_path}products.html",
    "zh": "产品中心",
    "en": "Products"
    }, {
    "url": "${Language.lans[lan].url_path}products/${product.id}.html",
    "zh": "产品详情",
    "en": "product-detail"
    }] />
    <div class="product">
        <div class="title">
            <div class="container">
                <div class="title-swiper">
                    <div class="view">
                        <div class="swiper-container">
                            <div class="swiper-wrapper">
                                <#if product.resourceList?? && product.resourceList?size gt 0>
                                    <#list product.resourceList as resource>
                                        <#if resource.resourceType == "VIDEO">
                                            <div class="swiper-slide">
                                                <video id="example_video_1"
                                                       class="video-js vjs-default-skin vjs-big-play-centered vjs-fluid"
                                                       controls preload="auto"
                                                       data-setup='{"example_option":true}'>
                                                    <source src="${resource.resourceUrl}" type="video/mp4"/>
                                                    <p class="vjs-no-js">To view this video please enable JavaScript,
                                                        and consider
                                                        upgrading to a web browser that <a
                                                                href="http://videojs.com/html5-video-support/"
                                                                target="_blank">supports
                                                            HTML5 video</a></p>
                                                </video>
                                            </div>
                                        </#if>
                                    </#list>
                                </#if>
                                <#if product.bannerList?? && product.bannerList?size gt 0>
                                    <#list product.bannerList as banner>
                                        <div class="swiper-slide">
                                            <img src="${banner.resourceUrl}" alt="${product.title}">
                                        </div>
                                    </#list>
                                </#if>
                            </div>
                        </div>
                    </div>
                    <div class="mobile-pagination visible-xs-block">
                        1/5
                    </div>
                    <div class="preview hidden-xs">
                        <a class="arrow-left" href="#"></a>
                        <a class="arrow-right" href="#"></a>
                        <div class="swiper-container">
                            <div class="swiper-wrapper">
                                <#if product.resourceList?? && product.resourceList?size gt 0>
                                    <#list product.resourceList as resource>
                                        <#if resource.resourceType == "VIDEO">
                                            <#assign hadVideo=true>
                                            <div class="swiper-slide active-nav">
                                                <div class="swiper-content">
                                                    <img src="${resource.resourceUrl}?x-oss-process=video/snapshot,t_0,f_jpg"
                                                         alt="${product.title}">
                                                </div>
                                            </div>
                                        <#elseif resource.resourceType == "OTHERS">
                                            <#assign hadOther=true>
                                        </#if>
                                    </#list>
                                </#if>
                                <#if product.bannerList?? && product.bannerList?size gt 0>
                                    <#list product.bannerList as banner>
                                        <div class="swiper-slide ${(banner_index == 0 && !(hadVideo?? && hadVideo == true))?string("active-nav", "")}">
                                            <div class="swiper-content">
                                                <img src="${banner.resourceUrl}" alt="${product.title}">
                                            </div>
                                        </div>
                                    </#list>
                                </#if>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="product-intro">
                    <div class="product-intro-title">
                        <h1 class="product-name">
                            ${(product.title)!'时间分辨碳纤维时间分辨碳纤维时间分辨碳纤维'}
                        </h1>
                    </div>
                    <div class="product-intro-field">
                        <p>${Language.lans[lan].product_detail_model}：${product.modelName}</p>
                        <p>${Language.lans[lan].product_detail_standard}：${product.specifications}</p>
                        <p>${Language.lans[lan].product_detail_code}：${product.goodsNum}</p>
                        <p>${Language.lans[lan].product_detail_place}：${product.originPlace}</p>
                        <p>${Language.lans[lan].product_detail_function}：${product.methodology}</p>
                    </div>
                    <div class="product-intro-control">
                        <#if userProductId??>
                            <button disabled="disabled"
                                    class="btn addwish">${Language.lans[lan].product_detail_wish_added}</button>
                        <#else>
                            <button data-content="${product.id}" class="btn addwish"><img
                                        src="http://img.bnibt.com/static/jiahao_white.png"
                                        alt="">${Language.lans[lan].product_detail_wish}</button>
                        </#if>

                        <a href="#" data-content="${product.id}" class="btn advisory" role="button" data-toggle="modal"
                           data-target="#typeModal">${Language.lans[lan].product_detail_advisory}</a>
                    </div>
                </div>
            </div>
        </div>
        <div class="product-detail-mask">
        </div>
        <div class="product-detail">
            <div class="container">
                <div class="product-detail-title">
                    <p><strong>${Language.lans[lan].product_detail_description}</strong></p>
                </div>
                <div class="product-detail-content ql-container ql-snow">
                    <div class="ql-editor">
                        ${product.content}
                    </div>
                    <#if hadOther?? && hadOther == true && product.resourceList?? && product.resourceList?size gt 0>
                        <div class="downloads">
                            <h2 class="downloads-title">
                                ${Language.lans[lan].training_download_title}
                            </h2>
                            <div class="download-items">
                                <#list product.resourceList as resource>
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

                <div class="product-recommend">
                    <p class="product-recommend-title">
                        <strong>${Language.lans[lan].product_detail_recommend}</strong>
                    </p>
                    <div class="product-items">
                        <#list products as product>
                            <a hidefocus="true" title="${product.title?html}"
                               href="${Language.lans[lan].url_path}products/${product.id}.html" class="product-item">
                                <img src="${(product.bannerList[0].resourceUrl)!''}${(product.bannerList[0].params)!''}"
                                     alt="${product.title?html}">
                                <p class="product-name">
                                    ${product.title?html}
                                </p>
                            </a>
                        </#list>
                    </div>
                </div>
            </div>
        </div>
        <#if related?? && related?size gt 0>
            <div class="other-products hidden-xs">
                <div class="container">
                    <p class="other-products-title">
                        <strong>${Language.lans[lan].product_detail_Related}</strong>
                    </p>
                    <div class="oproduct-items">
                        <#list related as product>
                            <a hidefocus="true" title="${product.title?html}"
                               href="${Language.lans[lan].url_path}products/${product.id}.html" class="oproduct-item">
                                <img src="${(product.bannerList[0].resourceUrl)!''}${(product.bannerList[0].params)!''}"
                                     alt="${product.title?html}">
                                <p class="oproduct-name">
                                    ${product.title?html}
                                </p>
                            </a>
                        </#list>
                    </div>
                </div>
            </div>
        </#if>
        <div class="other-products-mask"></div>
    </div>
    <!-- 选择咨询的客户类型 -->
    <div class="modal fade" id="typeModal" tabindex="-1" role="dialog" aria-labelledby="typeModalLabel">
        <div class="modal-dialog modal-sm" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                                aria-hidden="true">${Language.lans[lan].modal_advisory_btn_close}</span></button>
                    <h4 class="modal-title" id="typeModalLabel">${Language.lans[lan].modal_type_title}</h4>
                </div>
                <div class="modal-footer">
                    <button class="btn" data-dismiss="modal" data-content="OTHERS"
                            aria-label="Close">${Language.lans[lan].modal_type_customer}</button>
                    <button class="btn" data-dismiss="modal" data-content="HOSPITAL"
                            aria-label="Close">${Language.lans[lan].modal_type_hospital}</button>
                </div>
            </div>
        </div>
    </div>
    <!-- 咨询弹窗 -->
    <div class="modal fade" id="advisoryModal" tabindex="-1" role="dialog" aria-labelledby="advisoryModalLabel">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                                aria-hidden="true">${Language.lans[lan].modal_advisory_btn_close}</span></button>
                    <h4 class="modal-title" id="advisoryModalLabel">${Language.lans[lan].modal_advisory_title}</h4>
                </div>
                <div class="modal-body">
                    <div class="advisory-msg">
                        <div class="message-load">
                            <img src="http://img.bnibt.com/static/loading2.gif" alt="">
                        </div>
                        <div class="msg-list">
                        </div>
                    </div>
                    <div class="advisory-msg-control">
                        <div class="send-input">
                            <input type="text" class="form-control" id="messageText"
                                   value="${Language.lans[lan].modal_advisory_input_placeholder}"
                                   placeholder="${Language.lans[lan].modal_advisory_input_placeholder}">
                        </div>
                        <div class="send-btn">
                            <button class="btn send"
                                    role="button">${Language.lans[lan].modal_advisory_btn_send}</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</@base.base_container>