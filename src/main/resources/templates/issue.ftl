<#import 'common/base.ftl' as base>
<#import 'common/breadcrumb.ftl' as breadcrumb>
<#import "common/language.ftl" as Language>
<@base.base_container
title="${Language.lans[lan].issue_title}"
headers=[
"/css/breadcrumb.css",
"/css/issue.css"
]
scripts=[
"/js/issue.js"
]>
    <div class="issue">
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
        "url": "${Language.lans[lan].url_path}issue.html",
        "zh": "常见问题",
        "en": "Issue"
        }] />
        <img class="banner hidden-xs" src="http://img.bnibt.com/static/Group44.png" alt="">
        <div class="visible-xs-block">
            <div class="issue-title-search mobile">
                <div class="search-input-container">
                    <input type="text" value="${Language.lans[lan].issue_search_placeholder}" placeholder="${Language.lans[lan].issue_search_placeholder}">
                </div>
                <button type="submit" class="btn search-mobile-btn visible-xs-block">
                    ${Language.lans[lan].issue_search_submit}
                </button>
            </div>
        </div>
        <div class="issue-list">
            <div class="container">
                <div class="list-container ${(articleList?? && articleList?size gt 0)?string("hadList", "")}">
                    <div class="panel-group" id="accordion" role="tablist" aria-multiselectable="true">
                        <#if articleList?? && articleList?size gt 0>
                            <#list articleList as article>
                                <div class="panel issue-item">
                                    <div class="panel-heading" role="tab" id="issue-head-${article.id}">
                                        <h2 class="panel-title">
                                            <a hidefocus="true" class="collapsed issue-item-title" role="button" data-toggle="collapse" data-parent="#accordion" href="#issue-${article.id}" aria-expanded="false" aria-controls="issue-${article.id}">
                                                <p>${article.title}</p>
                                                <div class="collapse-img">
                                                    <img class="open-img" src="http://img.bnibt.com/static/plus-circle.png" alt="">
                                                    <img class="close-img" src="http://img.bnibt.com/static/minus-circle.png" alt="">
                                                </div>
                                            </a>

                                        </h2>
                                    </div>
                                    <div id="issue-${article.id}" class="panel-collapse collapse" role="tabpanel" aria-labelledby="issue-head-${article.id}">
                                        <div class="panel-body">
                                            ${(article.content)!'无'}
                                        </div>
                                    </div>
                                </div>
                            </#list>
                        </#if>
                    </div>
                    <div class="issue-item-none">
                        <img src="http://img.bnibt.com/static/img_none.png" alt="">
                        <p>${Language.lans[lan].products_no_data}</p>
                    </div>
                </div>

                <div class="issue-message-desktop hidden-xs">
                    <p class="issue-message-title">
                        ${Language.lans[lan].issue_search_title}
                    </p>
                    <div class="issue-title-search desktop">
                        <div class="search-input-container">
                            <input type="text" value="${Language.lans[lan].issue_search_placeholder}" placeholder="${Language.lans[lan].issue_search_placeholder}">
                            <button type="submit" class="btn">
                                <img src="http://img.bnibt.com/static/search_black.png" alt="">
                            </button>
                        </div>
                    </div>
                    <div class="issue-message">
                        <p>${Language.lans[lan].issue_message_title}</p>
                        <a href="#" class="btn" data-toggle="modal">${Language.lans[lan].issue_message_btn_start}</a>
                    </div>
                </div>

                <div class="issue-pages hidden-xs">
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
            </div>
        </div>
        <div class="mobile-more issue-more visible-xs-block">
            <a href="#" class="btn previous">上一页</a>
            <a href="#" class="btn next">下一页</a>
        </div>
        <div class="issue-message visible-xs-block">
            <p>${Language.lans[lan].issue_message_title}</p>
            <a href="#" class="btn" data-toggle="modal">${Language.lans[lan].issue_message_btn_start}</a>
        </div>
    </div>


    <!-- 留言弹窗 -->
    <div class="modal fade" id="messageModal" tabindex="-1" role="dialog" aria-labelledby="messageModalLabel">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">${Language.lans[lan].issue_modal_btn_close}</span></button>
                    <h4 class="modal-title" id="messageModalLabel">${Language.lans[lan].issue_modal_title}</h4>
                </div>
                <div class="modal-body">
                    <input type="text" class="form-control" id="messageName" value="${Language.lans[lan].issue_modal_name_placeholder}" placeholder="${Language.lans[lan].issue_modal_name_placeholder}">
                    <input type="text" class="form-control" id="messagePhone" value="${Language.lans[lan].issue_modal_phone_placeholder}" placeholder="${Language.lans[lan].issue_modal_phone_placeholder}">
                    <input type="text" class="form-control" id="messageMail" value="${Language.lans[lan].issue_modal_email_placeholder}" placeholder="${Language.lans[lan].issue_modal_email_placeholder}">
                    <select class="form-control" id="messageType">
                        <option value='' disabled selected style='display:none;'>${Language.lans[lan].issue_modal_type_placeholder}</option>
                        <#if typeList??>
                            <#list typeList as type>
                                <#if (lan?lower_case) == "en">
                                    <option value='${type.dicKey}'>${(type.enName)!type.dicKey}</option>
                                <#else >
                                    <option value='${type.dicKey}'>${(type.name)!type.dicKey}</option>
                                </#if>

                            </#list>
                        </#if>
                    </select>
                    <textarea id="messageContent" class="form-control" rows="5" placeholder="${Language.lans[lan].issue_modal_content_placeholder}">${Language.lans[lan].issue_modal_content_placeholder}</textarea>
                    <div class="error-infor"><p></p></div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn">${Language.lans[lan].issue_modal_btn_confirm}</button>
                </div>
            </div>
        </div>
    </div>
</@base.base_container>