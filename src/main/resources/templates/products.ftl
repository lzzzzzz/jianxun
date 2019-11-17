<#import 'common/base.ftl' as base>
<#import 'common/breadcrumb.ftl' as breadcrumb>
<#import 'common/language.ftl' as Language>
<@base.base_container
    title="${Language.lans[lan].products_title}"
    headers=[
        "/css/breadcrumb.css",
        "/css/products.css"
    ]
    scripts=[
        "/js/products.js"
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
    }] />
    <div class="product-types">
        <div class="container">
            <#if menus??>
                <div class="desktop-types hidden-xs">
                    <#list menus as submenu>
                        <#if submenu??>
                            <div class="type-container">
                                <p class="type-title">
                                    ${submenu[lan]}
                                </p>
                                <div class="type-list">
                                    <#if submenu.menus?exists>
                                        <#list submenu.menus as item>
                                            <a href="${item.url}" title="${item[lan]}" class="btn ${(productType?? && item.id == productType)?string("current", "")}" role="button">${item[lan]}</a>
                                        </#list>
                                    <#else>
                                        <a href="${submenu.url}" title="${submenu[lan]}" class="btn" role="button">${submenu[lan]}</a>
                                    </#if>
                                </div>
                            </div>
                        </#if>
                    </#list>
                </div>
            </#if>
            <#if hots?? && hots?size gt 0>
                <div class="type-container hot-product">
                    <div class="type-title">
                        <img class="icon_hot hidden-xs" src="http://img.bnibt.com/static/icon_hot.png" alt="">
                        ${Language.lans[lan].products_hot_title}
                    </div>
                    <div class="type-list">
                        <#list hots as hot>
                            <a href="${Language.lans[lan].url_path}products/${hot.id}.html" class="btn" role="button" title="${hot.title}">${hot.title}</a>
                        </#list>
                    </div>

                </div>
            </#if>
            <div class="type-mark hidden-xs"></div>
            <div class="type-container tags">
                <div class="type-title">
                    ${Language.lans[lan].products_tag_title}
                </div>
                <div class="tag-list">
                    <#list tags as tag>
                        <#if (lan?lower_case) == 'en'>
                            <a href="#" content="${tag.dicKey}" class="btn" role="button">${(tag.enName)!''}</a>
                        <#else>
                            <a href="#" content="${tag.dicKey}" class="btn" role="button">${(tag.name)!''}</a>
                        </#if>
                    </#list>
                </div>
            </div>
            <#if menus??>
                <div class="visible-xs-block">
                    <div class="collapse" id="types-collapse">
                        <#list menus as submenu>
                            <#if submenu??>
                            <div class="type-container">
                                <p class="type-title">
                                    ${submenu[lan]}
                                </p>
                                <div class="type-list">
                                    <#if submenu.menus?exists>
                                        <#list submenu.menus as item>
                                            <a href="${item.url}" title="${item[lan]}" class="btn" role="button">${item[lan]}</a>
                                        </#list>
                                    <#else>
                                        <a href="${submenu.url}" title="${submenu[lan]}" class="btn" role="button">${submenu[lan]}</a>
                                    </#if>
                                </div>
                            </div>
                            </#if>
                        </#list>
                    </div>
                </div>
            </#if>
            <div class="type-control visible-xs-block">
                <a data-toggle="collapse" href="#types-collapse" aria-expanded="false" class="btn" role="button">展开更多</a>
            </div>
        </div>
    </div>
    <div class="product-list">
        <div class="container">
            <div class="product-title"></div>
            <div class="product-items">
                <#list products as product>
                    <a href="${Language.lans[lan].url_path}products/${product.id}.html" class="product-item">
                        <img src="${(product.bannerList[0].resourceUrl)!''}${(product.bannerList[0].params)!''}" alt="${product.title?html}">
                        <p class="product-name">
                            ${product.title?html}
                        </p>
                    </a>
                </#list>
                <#if products?size == 0>
                    <div class="search-result-none">
                        <img src="http://img.bnibt.com/static/img_none.png" alt="">
                        <p>${Language.lans[lan].products_no_data}</p>
                    </div>
                </#if>
            </div>
        </div>
    </div>
    <#if products?size gt 0>
        <div class="mobile-more product-more visible-xs-block">
            <a href="#" class="btn previous">${Language.lans[lan].pagination_previous_btn}</a>
            <a href="#" class="btn next">${Language.lans[lan].pagination_next_btn}</a>
        </div>
        <div class="product-pages hidden-xs">
            <div class="container">
                <nav aria-label="Page navigation">
                    <ul class="pagination" content="${page.page?c}/${page.totalPage?c}/${page.limit?c}/${page.totalItem?c}">
                        <div class="page-total">
                            <#if lan?? && lan == "en">
                                <p>${page.totalItem?c} data，total ${page.totalPage?c} page</p>
                            <#else>
                                <p>${page.totalItem?c}条数据，共${page.totalPage?c}页</p>
                            </#if>
                        </div>
                        <li class="previous-page">
                            <span aria-label="Previous">
                                ${Language.lans[lan].pagination_previous_btn}
                            </span>
                        </li>
                        <#list 1..page.totalPage as item>
                            <li class="${(page.page == item)?string("active", "")} pageIndex">
                                <a role="button" href="#">${item?c}</a>
                            </li>
                        </#list>
                        <li class="next-page ${(page.page == page.totalPage)?string("disabled", "")}">
                            <a href="#" aria-label="Next">
                                ${Language.lans[lan].pagination_next_btn}
                            </a>
                        </li>
                    </ul>
                </nav>
            </div>
            <div class="product-pages-mark"></div>
        </div>
    </#if>
</@base.base_container>