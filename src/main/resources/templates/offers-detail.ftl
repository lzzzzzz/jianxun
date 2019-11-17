<#import 'common/base.ftl' as base>
<#import 'common/breadcrumb.ftl' as breadcrumb>
<#import "common/language.ftl" as Language>
<@base.base_container
title="${article.title}"
headers=[
"/css/breadcrumb.css",
"/css/quill.snow.css",
"/css/offers-detail.css"
]
scripts=[
"/js/offers-detail.js"
]>
    <div class="offers">
        <img class="banner hidden-xs" src="http://img.bnibt.com/static/banner_sevice.png" alt="">
        <img class="banner visible-xs-block" src="http://img.bnibt.com/static/banner_sevice.png?x-oss-process=image/crop,w_750,h_300,g_center" alt="">
        <@breadcrumb.breadcrumb paths=[
        {
        "url": "${Language.lans[lan].url_path}index.html",
        "zh": "首页",
        "en": "Home"
        }, {
        "url": "#",
        "zh": "加入我们",
        "en": "Join Us"
        }, {
        "url": "${Language.lans[lan].url_path}offers.html",
        "zh": "社会招聘",
        "en": "Offers"
        }] />
        <div class="container">
            <#if lan?? && lan == "en">
                <div class="company-infor">
                    <div class="company-infor-img">
                        <img src="http://img.bnibt.com/static/conpany.png" alt="">
                    </div>
                    <div class="company-infor-detail">
                        <p>Tel：0755-23219857</p>
                        <p>Address：8th Floor, Block A, Konka R&D Building, Gaoxin South 12th Road, Nanshan District Science and Technology Park, Shenzhen</p>
                        <p>Email：abc@126.com</p>
                    </div>
                </div>
            <#else>
                <div class="company-infor">
                    <div class="company-infor-img">
                        <img src="http://img.bnibt.com/static/conpany.png" alt="">
                    </div>
                    <div class="company-infor-detail">
                        <p>电话：0755-23219857</p>
                        <p>地址：深圳南山区科技园高新南十二道康佳研发大厦A座8楼</p>
                        <p>投递邮箱：abc@126.com</p>
                    </div>
                </div>
            </#if>
            <div class="offers-content">
                <div class="offers-title hidden-xs">
                    <strong>${Language.lans[lan].offers_detail_title}</strong>
                </div>
                <div class="offers-content-title">
                    <h1 class="offers-name">${article.title}</h1>
                    <p class="offers-others">
                        ${(article.brief)!'无'}
                    </p>
                </div>
                <div class="ql-container ql-snow">
                    <div id="content" class="offers-container ql-editor">
                        ${(article.content)!'无'}
                    </div>
                </div>
            </div>
        </div>
    </div>
</@base.base_container>