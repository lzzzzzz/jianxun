<#import 'common/base.ftl' as base>
<#import 'common/breadcrumb.ftl' as breadcrumb>
<#import "common/language.ftl" as Language>
<@base.base_container
    title="${Language.lans[lan].training_title}"
    headers=[
        "/css/breadcrumb.css",
        "/css/training.css"
    ]
    scripts=[
        "/js/training.js"
    ]>
    <div class="training">
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
        <div class="training-list">
            <div class="container">
                <div class="training-page-title">
                    <h1 class="training-title-name hidden-xs">${Language.lans[lan].training_content_title}</h1>
                    <div class="training-title-search">
                        <div class="search-input-container">
                            <input type="text" value="${Language.lans[lan].training_search_placeholder}" placeholder="${Language.lans[lan].training_search_placeholder}">
                            <button type="submit" class="btn hidden-xs">
                                <img src="http://img.bnibt.com/static/search_black.png" alt="">
                            </button>
                        </div>
                        <button type="submit" class="btn search-mobile-btn visible-xs-block">
                            ${Language.lans[lan].training_search_submit}
                        </button>
                    </div>
                </div>
                <div class="training-items">
                    <#if training?? && training?size gt 0>
                        <#list training as new>
                            <a hidefocus="true" href="${Language.lans[lan].url_path}training/${new.id}.html" title="${new.title}" class="training-item">
                                <img src="${(new.bannerList[0].resourceUrl)!''}${(new.bannerList[0].params)!''}" alt="">
                                <h2 class="training-title">
                                    <strong>${new.title?html}</strong>
                                </h2>
                                <p class="training-content">
                                    ${(new.brief)!''}
                                </p>
                            </a>
                        </#list>
                    <#else>
                        <div class="no-training">
                            <img src="http://img.bnibt.com/static/img_none.png" alt="">
                            <p>${Language.lans[lan].products_no_data}</p>
                        </div>
                    </#if>
                </div>
            </div>
        </div>
    <#if training?? && training?size gt 0>
        <div class="mobile-more training-more visible-xs-block">
            <a href="#" class="btn previous">上一页</a>
            <a href="#" class="btn next">下一页</a>
        </div>
    </#if>
        <div class="training-pages hidden-xs">
            <div class="container">
                <nav aria-label="Page navigation">
                    <ul class="pagination" content="${page.page?c}/${page.totalPage?c}/${page.limit?c}/${page.totalItem?c}">
                        <div class="page-total">
                            <p>${page.totalItem?c}条数据，共${page.totalPage?c}页</p>
                        </div>
                        <li class="previous-page">
                            <span aria-label="Previous">
                                上一页
                            </span>
                        </li>
                        <#if page.totalItem != 0>
                            <#list 1..page.totalPage as item>
                                <li class="${(page.page == item)?string("active", "")} pageIndex">
                                    <a role="button" href="#">${item?c}</a>
                                </li>
                            </#list>
                        </#if>
                        <li class="next-page ${(page.page == page.totalPage)?string("disabled", "")}">
                            <a href="#" aria-label="Next">
                                下一页
                            </a>
                        </li>
                    </ul>
                </nav>
            </div>
            <div class="product-pages-mark"></div>
        </div>
    </div>

</@base.base_container>